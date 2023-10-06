package com.example.todo.service;

import com.example.todo.exception.UserException;
import com.example.todo.mapper.UserMapper;
import com.example.todo.model.dto.UserDTO;
import com.example.todo.model.entity.User;
import com.example.todo.model.enums.UserError;
import com.example.todo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = UserMapper.INSTANCE;
    }

    public UserDTO signUp(UserDTO userDTO) {
        User user = User.builder()
                .loginId(userDTO.getLoginId())
                .pwd(passwordEncoder.encode(userDTO.getPwd()))
                .name(userDTO.getName())
                .build();

        return userMapper.toDto(userRepository.save(user));
    }

    public UserDTO getUser(Long idx) throws UserException {
        User user = userRepository.findById(idx).orElseThrow(() -> new UserException(UserError.HAVE_NO_DATA));

        return userMapper.toDto(user);
    }

    public void withdrawalUser(Long userIdx) throws UserException {
        User user = userRepository.findById(userIdx).orElseThrow(() -> new UserException(UserError.HAVE_NO_DATA));

        user.updateIsEnabled();
        user.updateWithdrawalDate(LocalDateTime.now());

        userRepository.save(user);
    }
}
