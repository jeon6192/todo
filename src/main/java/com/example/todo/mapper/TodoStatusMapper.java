package com.example.todo.mapper;

import com.example.todo.model.dto.TodoStatusDTO;
import com.example.todo.model.entity.TodoStatus;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TodoStatusMapper {

    TodoStatusMapper INSTANCE = Mappers.getMapper(TodoStatusMapper.class);

    TodoStatus toEntity(TodoStatusDTO todoStatusDto);

    TodoStatusDTO toDto(TodoStatus todoStatus);
}
