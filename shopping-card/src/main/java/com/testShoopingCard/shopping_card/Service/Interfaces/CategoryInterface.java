package com.testShoopingCard.shopping_card.Service.Interfaces;

import com.testShoopingCard.shopping_card.Entity.Category;

import java.util.Optional;


public interface CategoryInterface {
    Optional<Category> getCategoryByName(String name);

    void deleteCategoryById(Integer id);


}
