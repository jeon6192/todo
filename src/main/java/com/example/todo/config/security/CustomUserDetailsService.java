package com.example.todo.config.security;

import com.example.todo.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final LoginService loginService;

    public CustomUserDetailsService(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // AuthenticationProvider 에서 받은 파라미터 (loginId) 를 통하여 DB 조회
        User user = loginService.findByLoginId(username).orElseThrow(()
                -> new UsernameNotFoundException("USER NOT FOUND"));

        // UserDetails 를 상속하여 만든 CustomUserDetails 를 User 객체의 정보를 추가하여 생성 후 리턴
        return CustomUserDetails.builder().user(user).build();
    }
}