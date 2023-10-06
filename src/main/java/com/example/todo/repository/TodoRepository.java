package com.example.todo.repository;

import com.example.todo.model.entity.Todo;
import com.example.todo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUser(User user);

    Optional<Todo> findTop1ByUserOrderByCreatedAtDesc(User user);
}
