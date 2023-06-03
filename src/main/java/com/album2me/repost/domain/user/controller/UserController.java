package com.album2me.repost.domain.user.controller;

import com.album2me.repost.domain.image.dto.UploadImageRequest;
import com.album2me.repost.domain.user.dto.request.UserCreateRequest;
import com.album2me.repost.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody @Valid UserCreateRequest userCreateRequest){
        userService.signUp(userCreateRequest.toEntity(passwordEncoder));
    }

    @PatchMapping("/{id}/profile-image")
    public ResponseEntity<Void> updateProfile (
            @PathVariable final Long id,
            @Valid @RequestBody UploadImageRequest uploadImageRequest
    ) {
        userService.updateProfileImage(id, uploadImageRequest);

        return ResponseEntity.ok()
                .build();
    }
}
