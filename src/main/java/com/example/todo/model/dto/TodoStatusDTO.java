package com.example.todo.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoStatusDTO {
    private Long idx;

    @NotBlank(message = "상태값은 필수값 입니다.")
    private String status;

    @Builder
    public TodoStatusDTO(Long idx, String status) {
        this.idx = idx;
        this.status = status;
    }
}
