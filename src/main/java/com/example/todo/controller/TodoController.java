package com.example.todo.controller;

import com.example.todo.exception.UserException;
import com.example.todo.model.dto.TodoDTO;
import com.example.todo.model.dto.TodoStatusDTO;
import com.example.todo.model.dto.UserDTO;
import com.example.todo.model.enums.UserError;
import com.example.todo.service.TodoService;
import com.example.todo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
public class TodoController {

    private final UserService userService;
    private final TodoService todoService;

    public TodoController(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
    }

    @PostMapping("/todo/{userIdx}")
    public ResponseEntity<TodoDTO> createTodo(@Validated @RequestBody TodoDTO todoDTO, @PathVariable Long userIdx,
                                              BindingResult bindingResult) throws UserException {
        if (bindingResult.hasErrors()) {
            String message = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new UserException(UserError.BAD_REQUEST, message);
        }

        UserDTO userDTO = userService.getUser(userIdx);

        TodoStatusDTO todoStatusDTO = todoService.getTodoStatus(todoDTO.getTodoStatus().getStatus());
        TodoDTO dtoToSave = TodoDTO.builder()
                .user(userDTO)
                .contents(todoDTO.getContents())
                .todoStatus(todoStatusDTO)
                .build();

        return ResponseEntity.ok(todoService.createTodo(dtoToSave));
    }

    @GetMapping("/todo/{userIdx}")
    public ResponseEntity<List<TodoDTO>> getTodoList(@PathVariable Long userIdx) throws UserException {
        UserDTO userDto = userService.getUser(userIdx);

        return ResponseEntity.ok(todoService.getList(userDto));
    }

    @GetMapping("/todo/last/{userIdx}")
    public ResponseEntity<TodoDTO> getLastTodo(@PathVariable Long userIdx) throws UserException {
        UserDTO userDto = userService.getUser(userIdx);

        return ResponseEntity.ok(todoService.getLastOne(userDto));
    }
}
