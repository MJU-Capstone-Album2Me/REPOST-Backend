package com.album2me.repost.domain.user.controller;

import com.album2me.repost.domain.user.dto.request.UserCheckIdRequest;
import com.album2me.repost.domain.user.dto.request.UserCheckNicknameRequest;
import com.album2me.repost.domain.user.dto.request.UserCreateRequest;
import com.album2me.repost.domain.user.dto.request.UserProfileChangeRequest;
import com.album2me.repost.domain.user.dto.response.UserCheckResponse;
import com.album2me.repost.domain.user.dto.response.UserProfileChangeResponse;
import com.album2me.repost.domain.user.service.UserService;
import com.album2me.repost.global.config.security.jwt.JwtAuthentication;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    @PatchMapping("/profile-image")
    public ResponseEntity<UserProfileChangeResponse> changeProfileImage(@ModelAttribute @Valid UserProfileChangeRequest userProfileChangeRequest,
                                                                        @AuthenticationPrincipal JwtAuthentication jwtAuthentication) {
        return ResponseEntity.ok(userService.changeProfileImage(userProfileChangeRequest, jwtAuthentication.getId()));
    }

    @PostMapping("/check-id")
    public ResponseEntity<UserCheckResponse> checkIdDuplicated(@RequestBody @Valid UserCheckIdRequest userCheckIdRequest) {
        return ResponseEntity.ok(userService.checkIdDuplicated(userCheckIdRequest));
    }

    @PostMapping("/check-nickname")
    public ResponseEntity<UserCheckResponse> checkNicknameDuplicated(@RequestBody @Valid UserCheckNicknameRequest userCheckNicknameRequest) {
        return ResponseEntity.ok(userService.checkNicknameDuplicated(userCheckNicknameRequest));
    }
}
