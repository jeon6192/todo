package com.example.todo.config.security;

import com.example.todo.model.entity.User;
import com.example.todo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserDetailsByAuthentication(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUser();
    }

    public Optional<User> findByLoginId(String username) {
        return userRepository.findByLoginId(username);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
