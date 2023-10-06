package com.example.todo.service;

import com.example.todo.exception.UserException;
import com.example.todo.mapper.TodoMapper;
import com.example.todo.mapper.TodoStatusMapper;
import com.example.todo.mapper.UserMapper;
import com.example.todo.model.dto.TodoDTO;
import com.example.todo.model.dto.TodoStatusDTO;
import com.example.todo.model.dto.UserDTO;
import com.example.todo.model.entity.Todo;
import com.example.todo.model.entity.TodoStatus;
import com.example.todo.model.enums.UserError;
import com.example.todo.repository.TodoRepository;
import com.example.todo.repository.TodoStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoStatusRepository todoStatusRepository;
    private final UserMapper userMapper;
    private final TodoMapper todoMapper;
    private final TodoStatusMapper todoStatusMapper;

    public TodoService(TodoRepository todoRepository, TodoStatusRepository todoStatusRepository) {
        this.todoRepository = todoRepository;
        this.todoStatusRepository = todoStatusRepository;
        this.userMapper = UserMapper.INSTANCE;
        this.todoMapper = TodoMapper.INSTANCE;
        this.todoStatusMapper = TodoStatusMapper.INSTANCE;
    }

    public TodoDTO createTodo(TodoDTO todoDTO) {
        Todo todo = todoMapper.toEntity(todoDTO);

        return todoMapper.toDto(todoRepository.save(todo));
    }

    public TodoStatusDTO getTodoStatus(String status) throws UserException {
        Long idx = com.example.todo.model.enums.TodoStatus.getIdxByStatus(status).getIdx();
        TodoStatus todoStatus = todoStatusRepository.findById(idx)
                .orElseThrow(() -> new UserException(UserError.BAD_REQUEST));

        return todoStatusMapper.toDto(todoStatus);
    }

    public List<TodoDTO> getList(UserDTO userDto) {
        List<Todo> todoList = todoRepository.findByUser(userMapper.toEntity(userDto));

        return todoMapper.toDtoList(todoList);
    }

    public TodoDTO getLastOne(UserDTO userDto) throws UserException {
        Todo todo = todoRepository.findTop1ByUserOrderByCreatedAtDesc(userMapper.toEntity(userDto))
                .orElseThrow(() -> new UserException(UserError.HAVE_NO_DATA));

        return todoMapper.toDto(todo);
    }
}
