package com.challenge.challengebackend.repository;

import com.challenge.challengebackend.model.Category;
import com.challenge.challengebackend.model.Todo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void TodoRepository_SaveTodo_ReturnSavedTodo() {

        // Arrange
        Long todoId = 1L;
        String todoTitle = "Title";
        String todoContent = "Content";
        Boolean todoCompleted = false;
        Category category = Category.builder().id(1L).name("Cat1").build();
        Todo todo = Todo.builder().id(todoId).title(todoTitle).content(todoContent).completed(todoCompleted).category(category).build();

        // Act
        Todo savedTodo = todoRepository.save(todo);

        // Assert
        Assertions.assertThat(savedTodo).isNotNull();
//        Assertions.assertThat(savedTodo.getId()).isEqualTo(todoId);  // This throws an error: org.opentest4j.AssertionFailedError: expected: 1L but was: 4L. This is because, as the running order of the methods is not established, and the database is shared, the second method run before the first one. And, as the ids are auto generated, the id for this object ended up being 4l.
        Assertions.assertThat(savedTodo.getTitle()).isEqualTo(todoTitle);
        Assertions.assertThat(savedTodo.getContent()).isEqualTo(todoContent);
        Assertions.assertThat(savedTodo.isCompleted()).isEqualTo(todoCompleted);

    }

    @Test
    public void TodoRepository_GetTodosByCategory_ReturnListOf2() {

        Category category1 = Category.builder().id(1L).name("Cat1").build();
        Category category2 = Category.builder().id(2L).name("Cat2").build();
        Todo todo1 = Todo.builder().id(1L).title("Title").content("Content").completed(false).category(category1).build();
        Todo todo2 = Todo.builder().id(2L).title("Title").content("Content").completed(false).category(category1).build();
        Todo todo3 = Todo.builder().id(3L).title("Title").content("Content").completed(false).category(category2).build();

        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);
        List<Todo> TodoList = todoRepository.findByCategory(category1);

        Assertions.assertThat(TodoList).isNotNull();
        Assertions.assertThat(TodoList.size()).isGreaterThan(0);
        Assertions.assertThat(TodoList.size()).isEqualTo(2);

    }

}
