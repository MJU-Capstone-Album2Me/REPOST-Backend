package com.album2me.repost.domain.post.service;

import com.album2me.repost.domain.image.dto.UploadImageRequest;
import com.album2me.repost.domain.image.dto.UploadImageResponse;
import com.album2me.repost.domain.image.dto.UploadImageUrlRequest;
import com.album2me.repost.domain.image.service.ImageService;
import com.album2me.repost.domain.post.dto.request.PostCreateRequest;
import com.album2me.repost.domain.post.dto.request.PostUpdateRequest;
import com.album2me.repost.domain.post.model.Post;
import com.album2me.repost.domain.post.dto.response.PostResponse;
import com.album2me.repost.domain.post.repository.PostRepository;
import com.album2me.repost.domain.room.model.Room;
import com.album2me.repost.domain.room.service.RoomService;
import com.album2me.repost.domain.user.model.User;

import lombok.RequiredArgsConstructor;

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
    private final PostRepository postRepository;
    private final ImageService imageService;

    public PostResponse findById(final Long id) {

        final Post post = postRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return PostResponse.from(post);
    }

    public List<PostResponse> showAll() {
        final List<Post> posts = postRepository.findAll();

        return posts.stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long create(final Long roomId, final User user, final PostCreateRequest postCreateRequest) {
        final Room room = roomService.findRoomById(roomId);

        final Long postId = createPost(room, user, postCreateRequest);

        return postId;
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
        UploadImageRequest uploadImageRequest = UploadImageRequest.of(image);

        final UploadImageResponse uploadImageResponse = imageService.uploadImageToS3(uploadImageRequest);

        return uploadImageResponse.imageUrl();
    }


    private void uploadImageToDB(final Long postId, final String postImageUrl) {
        UploadImageUrlRequest uploadImageUrlRequest = UploadImageUrlRequest.of(postId, postImageUrl);
        imageService.uploadImageToDB(uploadImageUrlRequest);
    }

    @Transactional
    public void update(final Long id, final User user, final PostUpdateRequest postUpdateRequest) {
        final Post post = findPostById(id);

        validateWriter(post, user);

        post.update(postUpdateRequest.toEntity());
    }

    @Transactional
    public void delete(final Long id, final User user) {
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
