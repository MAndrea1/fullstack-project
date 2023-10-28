package com.challenge.challengebackend.repository;

import com.challenge.challengebackend.model.Category;
import com.challenge.challengebackend.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByCategory(Category category);
}
