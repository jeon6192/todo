package com.example.todo.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDTO {
    private Long idx;

    @NotBlank(message = "ID는 필수값 입니다.")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수값 입니다.")
//    @Pattern(regexp = "((?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,18})")
    private String pwd;

    @NotBlank(message = "닉네임은 필수값 입니다.")
    private String name;

    private Boolean isEnabled;

    private LocalDateTime withdrawalDate;

    @Builder
    public UserDTO(Long idx, String loginId, String pwd, String name, Boolean isEnabled, LocalDateTime withdrawalDate) {
        this.idx = idx;
        this.loginId = loginId;
        this.pwd = pwd;
        this.name = name;
        this.isEnabled = isEnabled;
        this.withdrawalDate = withdrawalDate;
    }
}
