package com.example.todo.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoDTO {
    private Long idx;

    private UserDTO user;

    @NotBlank(message = "내용은 필수값 입니다.")
    private String contents;

    private TodoStatusDTO todoStatus;

    @Builder
    public TodoDTO(Long idx, UserDTO user, String contents, TodoStatusDTO todoStatus) {
        this.idx = idx;
        this.user = user;
        this.contents = contents;
        this.todoStatus = todoStatus;
    }
}
