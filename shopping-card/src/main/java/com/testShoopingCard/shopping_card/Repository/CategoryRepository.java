package com.testShoopingCard.shopping_card.Repository;

import com.testShoopingCard.shopping_card.Dto.CategoryDto;
import com.testShoopingCard.shopping_card.Entity.Category;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    boolean existsByName(String name);

    @Query("SELECT c FROM Category c WHERE c.id = :id")
    Category findCategoryData(Integer id);

    Optional<Category> findByName( String name);
}
