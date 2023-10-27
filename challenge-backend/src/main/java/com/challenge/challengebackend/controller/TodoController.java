package com.challenge.challengebackend.controller;

import com.challenge.challengebackend.model.Todo;
import com.challenge.challengebackend.model.dto.TodoDTO;
import com.challenge.challengebackend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/todo")
    Todo addNewTodo(@RequestBody TodoDTO newTodoDTO) {
        return todoService.addNewTodo(newTodoDTO);
    }

    @GetMapping("/todos")
    List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @GetMapping("/todos-by-cat")
    List<Todo> getTodosByCategory(@RequestParam Long categoryId) {
        return todoService.getTodosByCategory(categoryId);
    }

    @PutMapping("/todo/{id}")
    Todo updateTodo(@RequestBody TodoDTO newTodoDTO, @PathVariable Long id) {
        return todoService.updateTodo(newTodoDTO, id);
    }

    @DeleteMapping("/todo/{id}")
    ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        return todoService.deleteTodo(id);
    }

    @GetMapping("/todo/{id}")
    Todo getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id);
    }
}
