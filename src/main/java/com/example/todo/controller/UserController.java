package com.example.todo.controller;

import com.example.todo.config.security.CustomUserDetails;
import com.example.todo.exception.UserException;
import com.example.todo.model.dto.UserDTO;
import com.example.todo.model.enums.UserError;
import com.example.todo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<?> test() {

        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDTO> signUp(@Validated @RequestBody UserDTO userDTO, BindingResult bindingResult) throws UserException {
        if (bindingResult.hasErrors()) {
            String message = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new UserException(UserError.BAD_REQUEST, message);
        }

        return ResponseEntity.ok(userService.signUp(userDTO));
    }

    @GetMapping("/about-me")
    public ResponseEntity<UserDTO> me(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws UserException {
        Long idx = customUserDetails.getUser().getIdx();

        if(idx == null) {
            throw new UserException(UserError.NOT_DEFINED);
        }

        return ResponseEntity.ok(userService.me(idx));
    }
}
