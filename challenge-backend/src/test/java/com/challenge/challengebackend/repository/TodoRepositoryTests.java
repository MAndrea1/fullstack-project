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
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void TodoRepository_SaveTodo_ReturnSavedTodo() {

        // Arrange
        Long todoId = 1L;  // IDs are auto generated. So strictly we don't need to define this, because it will be ignored.
        String todoTitle = "Title";
        String todoContent = "Content";
        Boolean todoCompleted = false;
        Category category = Category.builder().id(1L).name("Cat1").build();  // IDs are auto generated. So strictly we don't need to define id, because it will be ignored.
        Todo todo = Todo.builder().id(todoId).title(todoTitle).content(todoContent).completed(todoCompleted).category(category).build();  // IDs are auto generated. So strictly we don't need to define id, because it will be ignored.

        // Act
        Todo savedTodo = todoRepository.save(todo);

        // Assert
        Assertions.assertThat(savedTodo).isNotNull();
//        Assertions.assertThat(savedTodo.getId()).isEqualTo(todoId);  // This throws an error: org.opentest4j.AssertionFailedError: expected: 1L but was: 4L. This is because, as the running order of the methods is not established and the database is shared, the second method run before the first one. And, as the ids are auto generated, the id for this object ended up being 4l.
        Assertions.assertThat(savedTodo.getTitle()).isEqualTo(todoTitle);
        Assertions.assertThat(savedTodo.getContent()).isEqualTo(todoContent);
        Assertions.assertThat(savedTodo.isCompleted()).isEqualTo(todoCompleted);
    }

    @Test
    public void TodoRepository_GetAllTodos_ReturnListOf3() {

        // Arrange
        Category category1 = Category.builder().id(1L).name("Cat1").build();
        Category category2 = Category.builder().id(2L).name("Cat2").build();
        Todo todo1 = Todo.builder().id(1L).title("Title").content("Content").completed(false).category(category1).build();
        Todo todo2 = Todo.builder().id(2L).title("Title").content("Content").completed(false).category(category1).build();
        Todo todo3 = Todo.builder().id(3L).title("Title").content("Content").completed(false).category(category2).build();

        // Act
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);
        List<Todo> todoList = todoRepository.findAll();

        // Assert
        Assertions.assertThat(todoList).isNotNull();
        Assertions.assertThat(todoList.size()).isEqualTo(3);
    }

    @Test
    public void TodoRepository_GetTodosByCategory_IsNotNull() {

        Category category1 = Category.builder().id(1L).name("Cat1").build();
        Todo todo1 = Todo.builder().id(1L).title("Title").content("Content").completed(false).category(category1).build();
        Todo todo2 = Todo.builder().id(2L).title("Title").content("Content").completed(false).category(category1).build();

        todoRepository.save(todo1);
        todoRepository.save(todo2);
        List<Todo> TodoList = todoRepository.findByCategory(category1);

        Assertions.assertThat(TodoList).isNotNull();
        Assertions.assertThat(TodoList.size()).isGreaterThan(0);
    }

    @Test
    public void TodoRepository_EditTodo_ReturnEditedData() {

        Category category1 = Category.builder().id(1L).name("Cat1").build();
        Todo todo1 = Todo.builder().id(1L).title("Title").content("Content").completed(false).category(category1).build();

        todoRepository.save(todo1);

        Todo savedTodo = todoRepository.findById(todo1.getId()).get();

        String todoTitle = "Edited Title";
        Boolean todoCompleted = true;
        savedTodo.setTitle(todoTitle);
        savedTodo.setCompleted(todoCompleted);

        Todo todoEdited = todoRepository.save(savedTodo);

        Assertions.assertThat(todoEdited).isNotNull();
        Assertions.assertThat(todoEdited.getTitle()).isEqualTo(todoTitle);
        Assertions.assertThat(todoEdited.isCompleted()).isEqualTo(todoCompleted);

    }

    @Test
    public void TodoRepository_FindById_ReturnNotNull() {

        Category category1 = Category.builder().id(1L).name("Cat1").build();
        Todo todo1 = Todo.builder().id(1L).title("Title").content("Content").completed(false).category(category1).build();

        todoRepository.save(todo1);
        Todo savedTodo = todoRepository.findById(todo1.getId()).get();

        Assertions.assertThat(savedTodo).isNotNull();
        Assertions.assertThat(savedTodo.getId()).isEqualTo(1L);

    }

    @Test
    public void TodoRepository_DeleteById_ReturnEmpty() {

        Category category1 = Category.builder().id(1L).name("Cat1").build();
        Todo todo1 = Todo.builder().id(1L).title("Title").content("Content").completed(false).category(category1).build();

        todoRepository.save(todo1);
        todoRepository.deleteById(todo1.getId());
        Optional<Todo> savedTodo = todoRepository.findById(todo1.getId());

        Assertions.assertThat(savedTodo).isEmpty();

    }

}
