package com.challenge.challengebackend.repository;

import com.challenge.challengebackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
