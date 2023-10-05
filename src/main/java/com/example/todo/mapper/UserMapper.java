package com.example.todo.mapper;

import com.example.todo.model.dto.UserDTO;
import com.example.todo.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserDTO userDTO);

    UserDTO toDto(User user);
}
