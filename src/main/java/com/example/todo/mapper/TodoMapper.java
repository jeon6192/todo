package com.example.todo.mapper;

import com.example.todo.model.dto.TodoDTO;
import com.example.todo.model.entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    TodoMapper INSTANCE = Mappers.getMapper(TodoMapper.class);

    Todo toEntity(TodoDTO todoDto);

    TodoDTO toDto(Todo todo);

    List<Todo> toEntities(List<TodoDTO> todoDtoList);

    List<TodoDTO> toDtoList(List<Todo> entities);
}
