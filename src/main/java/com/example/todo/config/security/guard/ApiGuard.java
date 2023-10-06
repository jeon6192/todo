package com.example.todo.config.security.guard;

import com.example.todo.exception.UserException;
import com.example.todo.model.entity.Todo;
import com.example.todo.model.enums.UserError;
import com.example.todo.repository.TodoRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("apiGuard")
public class ApiGuard {

    private final TodoRepository todoRepository;

    public ApiGuard(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public final boolean checkAuthority(Long todoIdx) throws UserException {
        if (todoIdx == null) {
            throw new UserException(UserError.BAD_REQUEST);
        }

        // 1. 체크하려는 객체의 주인 확인
        Long ownerIdx = getOwnerIdx(todoIdx);

        // 2. 요청한 (로그인된) 유저가 주인인지 확인
        return isLoggedUser(ownerIdx);
    }

    // 파라미터로 받은 userIdx 와 현재 로그인된 유저의 userIdx 를 비교
    private boolean isLoggedUser(Long userIdx) {
        return Objects.equals(userIdx, GuardHelper.extractUserIdx());
    }

    // 파라미터로 받은 todoIdx 의 소유자 idx 반환
    private Long getOwnerIdx(Long todoIdx) throws UserException {
        Todo todo = todoRepository.findById(todoIdx).orElseThrow(() -> new UserException(UserError.HAVE_NO_DATA));

        return todo.getUser().getIdx();
    }
}
