package com.example.todo.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@DynamicInsert
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String loginId;

    private String pwd;

    private String name;

    private Boolean isEnabled;

    private LocalDateTime withdrawalDate;

    @Builder
    public User(Long idx, String loginId, String pwd, String name, Boolean isEnabled, LocalDateTime withdrawalDate) {
        this.idx = idx;
        this.loginId = loginId;
        this.pwd = pwd;
        this.name = name;
        this.isEnabled = isEnabled;
        this.withdrawalDate = withdrawalDate;
    }
}
