package com.challenge.challengebackend.service;

import com.challenge.challengebackend.model.Category;
import com.challenge.challengebackend.model.Todo;
import com.challenge.challengebackend.model.dto.TodoDTO;
import com.challenge.challengebackend.repository.CategoryRepository;
import com.challenge.challengebackend.repository.TodoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTests {

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    public void TodoService_addNewTodo_ReturnsNotNull() {

        long categoryId = 1L;
        String categoryName = "Cat1";
        Category category = Category.builder().id(categoryId).name(categoryName).build();

        String todoTitle = "Title";
        String todoContent = "Content";
        boolean todoCompleted = false;

        Todo todo = Todo.builder() .title(todoTitle).content(todoContent).completed(todoCompleted).category(category).build();
        TodoDTO todoDTO = TodoDTO.builder().title(todoTitle).content(todoContent).completed(todoCompleted).categoryId(categoryId).build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));  // The JPA method findById typically returns an Optional. Optional.of(category) creates an Optional instance containing the category object. This means the Optional object is not empty, and it holds a non-null reference to category.
        when(todoRepository.save(Mockito.any(Todo.class))).thenReturn(todo);

        Todo savedTodo = todoService.addNewTodo(todoDTO);

        Assertions.assertThat(savedTodo).isNotNull();
        Assertions.assertThat(savedTodo.getTitle()).isEqualTo(todoTitle);
        Assertions.assertThat(savedTodo.getContent()).isEqualTo(todoContent);
        Assertions.assertThat(savedTodo.isCompleted()).isEqualTo(todoCompleted);
        Assertions.assertThat(savedTodo.getCategory()).isEqualTo(category);

        // Verify that the CategoryRepository's findById method was called with the correct argument
        verify(categoryRepository).findById(1L);

    }

    @Test
    public void TodoService_getTodosByCategory_IsNotNull() {

        long categoryId = 1L;
        String categoryName = "Cat1";
        Category category = Category.builder().id(categoryId).name(categoryName).build();

        Todo todo1 = Todo.builder() .title("Title1").content("Content1").completed(false).category(category).build();
        Todo todo2 = Todo.builder() .title("Title2").content("Content2").completed(false).category(category).build();
        List<Todo> listTodos = List.of(todo1, todo2);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));  // The JPA method findById typically returns an Optional. Optional.of(category) creates an Optional instance containing the category object. This means the Optional object is not empty, and it holds a non-null reference to category.
        when(todoRepository.findByCategory(category)).thenReturn(listTodos);

        List<Todo> listByCat = todoService.getTodosByCategory(categoryId);

        Assertions.assertThat(listByCat).isNotNull();
        Assertions.assertThat(listByCat).isEqualTo(listTodos);
    }


}
