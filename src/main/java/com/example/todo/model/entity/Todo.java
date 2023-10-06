package com.example.todo.model.entity;

import com.example.todo.model.dto.TodoDTO;
import com.example.todo.model.dto.TodoStatusDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Todo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx", referencedColumnName = "idx")
    private User user;

    private String contents;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_idx", referencedColumnName = "idx")
    private TodoStatus todoStatus;

    @Builder
    public Todo(Long idx, User user, String contents, TodoStatus todoStatus) {
        this.idx = idx;
        this.user = user;
        this.contents = contents;
        this.todoStatus = todoStatus;
    }

    public void updateData(String contents, TodoStatus todoStatus) {
        this.contents = contents;
        this.todoStatus = todoStatus;
    }

    public void updateData(TodoDTO todoDto, TodoStatusDTO todoStatusDto) {
        this.contents = todoDto.getContents();
        this.todoStatus.updateStatus(todoStatusDto);
    }
}
