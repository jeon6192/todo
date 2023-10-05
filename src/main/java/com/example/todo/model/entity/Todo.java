package com.example.todo.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
}
