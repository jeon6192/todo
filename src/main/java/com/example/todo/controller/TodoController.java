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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/todo")
public class TodoController {

    private final UserService userService;
    private final TodoService todoService;

    public TodoController(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
    }

    @PostMapping("/{userIdx}")
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

        return ResponseEntity.ok(todoService.saveTodo(dtoToSave));
    }

    @GetMapping("/{userIdx}")
    public ResponseEntity<List<TodoDTO>> getTodoList(@PathVariable Long userIdx) throws UserException {
        UserDTO userDto = userService.getUser(userIdx);

        return ResponseEntity.ok(todoService.getList(userDto));
    }

    @GetMapping("/last/{userIdx}")
    public ResponseEntity<TodoDTO> getLastTodo(@PathVariable Long userIdx) throws UserException {
        UserDTO userDto = userService.getUser(userIdx);

        return ResponseEntity.ok(todoService.getLastOne(userDto));
    }

    @DeleteMapping("/{todoIdx}")
    @PreAuthorize("@apiGuard.checkAuthority(#todoIdx)")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long todoIdx) throws UserException {
        if (todoIdx == null) {
            throw new UserException(UserError.BAD_REQUEST);
        }

        todoService.deleteTodo(todoIdx);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{todoIdx}")
    @PreAuthorize("@apiGuard.checkAuthority(#todoIdx)")
    public ResponseEntity<TodoDTO> updateTodo(@Validated @RequestBody TodoDTO todoDto, @PathVariable Long todoIdx,
                                              BindingResult bindingResult) throws UserException {
        if (bindingResult.hasErrors()) {
            String message = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new UserException(UserError.BAD_REQUEST, message);
        }

        return ResponseEntity.ok(todoService.updateTodo(todoIdx, todoDto));
    }
}
