package com.example.todo.model.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TodoStatus {
    TODO(1L, "1", "할 일"),
    IN_PROCESS(2L, "2", "진행중"),
    DONE(3L, "3", "완료됨");

    private final Long idx;

    private final String status;

    private final String description;

    TodoStatus(Long idx, String status, String description) {
        this.idx = idx;
        this.status = status;
        this.description = description;
    }

    public static TodoStatus getIdxByStatus(String status) {
        TodoStatus todoStatus = resolve(status);
        if (todoStatus == null) {
            throw new IllegalArgumentException("No matching constant for [" + status + "]");
        }
        return todoStatus;
    }

    public static TodoStatus resolve(String status) {
        for (TodoStatus todoStatus : values()) {
            if (todoStatus.status.equals(status)) {
                return todoStatus;
            }
        }
        return null;
    }
}
