package com.challenge.challengebackend.service;

import com.challenge.challengebackend.exception.CategoryNotFoundException;
import com.challenge.challengebackend.exception.TodoNotFoundException;
import com.challenge.challengebackend.model.Category;
import com.challenge.challengebackend.model.Todo;
import com.challenge.challengebackend.model.dto.TodoDTO;
import com.challenge.challengebackend.repository.CategoryRepository;
import com.challenge.challengebackend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository, CategoryRepository categoryRepository) {
        this.todoRepository = todoRepository;
        this.categoryRepository = categoryRepository;
    }

    public Todo addNewTodo(@RequestBody TodoDTO todoDTO) {

        Category category = categoryRepository.findById(todoDTO.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(todoDTO.getCategoryId()));

        Todo newTodo = new Todo();
        newTodo.setTitle(todoDTO.getTitle());
        newTodo.setContent(todoDTO.getContent());
        newTodo.setCompleted(todoDTO.isCompleted());
        newTodo.setCategory(category);

        return todoRepository.save(newTodo);
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public List<Todo> getTodosByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        return todoRepository.findByCategory(category);
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(()->new TodoNotFoundException(id));
    }

    public Todo updateTodo(TodoDTO todoDTO, Long id) {

        Category category = categoryRepository.findById(todoDTO.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(todoDTO.getCategoryId()));

        return todoRepository.findById(id)
                .map(user-> {
                    user.setTitle(todoDTO.getTitle());
                    user.setContent(todoDTO.getContent());
                    user.setCompleted(todoDTO.isCompleted());
                    user.setCategory(category);
                    return todoRepository.save(user);
                }).orElseThrow(()->new TodoNotFoundException(id));
    }

    public ResponseEntity<String> deleteTodo(Long id) {
        try {
            this.getTodoById(id);
            todoRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (TodoNotFoundException ex) {
            throw new TodoNotFoundException(id);
        }
    }
}
