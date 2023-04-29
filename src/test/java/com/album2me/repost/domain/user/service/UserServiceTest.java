package com.album2me.repost.domain.user.service;

import static org.mockito.BDDMockito.*;

import com.album2me.repost.domain.user.dto.UserCreateRequest;
import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.domain.user.repository.UserRepository;
import com.album2me.repost.testutils.UserObjectProvider;
import org.aspectj.util.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @DisplayName("사용자는 회원가입을 성공한다.")
    @Test
    void singUP_success(){
        //given
        User newUser = UserObjectProvider.createUser();
        ReflectionTestUtils.setField(newUser, "id", 1L);
        given(userRepository.save(any(User.class))).willReturn(newUser);

        //when
        userService.signUp(newUser);

        //then
        verify(userRepository).save(any());
    }

}
