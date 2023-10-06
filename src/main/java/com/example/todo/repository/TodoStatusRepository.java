package com.example.todo.repository;

import com.example.todo.model.entity.TodoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoStatusRepository extends JpaRepository<TodoStatus, Long> {
}
