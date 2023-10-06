package com.example.todo.config.security.guard;

import com.example.todo.config.security.CustomUserDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GuardHelper {

    // CustomUserDetails 객체 내의 필드인 User entity 의 idx 를 반환
    public static Long extractUserIdx() {
        return getUserDetails().getUser().getIdx();
    }

    // 시큐리티 인증정보를 CustomUserDetails 객체로 변환
    private static CustomUserDetails getUserDetails() {
        return (CustomUserDetails) getAuthentication().getPrincipal();
    }

    // ContextHolder 에서 현재 로그인 된 인증정보를 얻어 옴
    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
