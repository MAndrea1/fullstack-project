package com.challenge.challengebackend.repository;

import com.challenge.challengebackend.model.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void CategoryRepository_GetAllCategories_ReturnAllCategories() {

        // Arrange
        Category category1 = Category.builder().id(1L).name("Cat 1").build();
        Category category2 = Category.builder().id(2L).name("Cat 2").build();
        Category category3 = Category.builder().id(3L).name("Cat 3").build();
        Category category4 = Category.builder().id(4L).name("Cat 4").build();

        // Act
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        categoryRepository.save(category4);
        List<Category> categoryList = categoryRepository.findAll();

        // Assert
        Assertions.assertThat(categoryList).isNotNull();
        Assertions.assertThat(categoryList.size()).isGreaterThan(0);
        Assertions.assertThat(categoryList.size()).isEqualTo(4);
    }

    @Test
    public void CategoryRepository_FindById_ReturnOneCategory() {

        // Arrange
        Category category1 = Category.builder().id(1L).name("Cat 1").build();
        Category category2 = Category.builder().id(2L).name("Cat 2").build();
        Category category3 = Category.builder().id(3L).name("Cat 3").build();
        Category category4 = Category.builder().id(4L).name("Cat 4").build();

        // Act
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        categoryRepository.save(category4);
        Category category = categoryRepository.findById(category3.getId()).get(); // the findByID method returns an Optional instead of the entity directly, to handle the situation in which the entity may not exist. The .get() method in the Optional will either return the entity if exists or will throw a NoSuchElementException if the Optional is empty. Note: there are methods such as .orElse() to handle the Optionals more gracefully.

        // Assert
        Assertions.assertThat(category).isNotNull();
        Assertions.assertThat(category.getId()).isEqualTo(3L);

    }

}
