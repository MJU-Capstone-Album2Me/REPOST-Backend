package com.album2me.repost.domain.post.service;

import com.album2me.repost.domain.comment.dto.response.CommentDto;
import com.album2me.repost.domain.image.dto.ImageDto;
import com.album2me.repost.domain.image.dto.UploadImageRequest;
import com.album2me.repost.domain.image.dto.UploadImageResponse;
import com.album2me.repost.domain.image.dto.UploadImageUrlRequest;
import com.album2me.repost.domain.image.service.ImageService;
import com.album2me.repost.domain.post.dto.request.PostCreateRequest;
import com.album2me.repost.domain.post.dto.request.PostShowRequest;
import com.album2me.repost.domain.post.dto.request.PostUpdateRequest;
import com.album2me.repost.domain.post.dto.response.PostCreateResponse;
import com.album2me.repost.domain.post.dto.response.PostPageResponse;
import com.album2me.repost.domain.post.model.Post;
import com.album2me.repost.domain.post.dto.response.PostWithCommentsResponse;
import com.album2me.repost.domain.post.repository.PostRepository;
import com.album2me.repost.domain.reply.dto.response.ReplyDto;
import com.album2me.repost.domain.room.model.Room;
import com.album2me.repost.domain.room.service.RoomService;
import com.album2me.repost.domain.user.model.User;

import com.album2me.repost.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final RoomService roomService;
    private final UserService userService;
    private final PostRepository postRepository;
    private final ImageService imageService;

    public PostWithCommentsResponse findById(final Long id) {

        final Post post = postRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        List<CommentDto> commentDtos = post.getComments()
                .stream()
                .map(comment ->
                    {
                        List<ReplyDto> replyDtos = comment.getReplies()
                                .stream()
                                .map(ReplyDto::from)
                                .collect(Collectors.toList());

                        return CommentDto.from(comment, replyDtos);
                    }
                )
                .toList();

        List<ImageDto> imageDtos = post.getImages()
                .stream()
                .map(ImageDto::from)
                .toList();

        return PostWithCommentsResponse.from(post, imageDtos, commentDtos);
    }

    public PostPageResponse findAll(final PostShowRequest postShowRequest, final Pageable pageable) {
        final Slice<Post> posts = postRepository.findAllPostWithImage(postShowRequest.cursor(), pageable);

        return PostPageResponse.from(posts);
    }

    @Transactional
    public PostCreateResponse create(final Long roomId, final Long userId, final PostCreateRequest postCreateRequest) {
        final Room room = roomService.findRoomById(roomId);
        final User user = userService.findUserById(userId);

        final Long postId = createPost(room, user, postCreateRequest);

        return new PostCreateResponse(postId);
    }

    public Long createPost(final Room room, final User user, final PostCreateRequest postCreateRequest) {
        final Post post = postCreateRequest.toEntity(user, room);

        final Long postId = postRepository.save(post).getId();

        uploadImages(postCreateRequest.getImages(), postId);

        return postRepository.save(post)
                .getId();
    }

    private List<String> uploadImages(final List<MultipartFile> images, final Long postId) {
        List<String> postImageUrls = new ArrayList<>();

        for (MultipartFile image : images) {

            String postImageUrl = uploadImageToAwsS3(image);
            postImageUrls.add(postImageUrl);

            uploadImageToDB(postId, postImageUrl);
        }

        return postImageUrls;
    }

    private String uploadImageToAwsS3(final MultipartFile image) {

        return imageService.uploadImageToS3(image);
    }


    private void uploadImageToDB(final Long postId, final String postImageUrl) {
        final Post post = findPostById(postId);
        UploadImageUrlRequest uploadImageUrlRequest = UploadImageUrlRequest.of(post, postImageUrl);
        imageService.uploadImageToDB(uploadImageUrlRequest);
    }

    @Transactional
    public void update(final Long id, final Long userId, final PostUpdateRequest postUpdateRequest) {
        final User user = userService.findUserById(userId);
        final Post post = findPostById(id);

        validateWriter(post, user);

        post.update(postUpdateRequest.toEntity());
    }

    @Transactional
    public void delete(final Long id, final Long userId) {
        final User user = userService.findUserById(userId);
        final Post post = findPostById(id);

        validateWriter(post, user);

        postRepository.delete(post);
    }

    private void validateWriter(final Post post, final User user) {
        if (!post.isWrittenBy(user)) {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }
    }

    public Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("해당 id로 Post을 찾을 수 없습니다."));
    }
}
