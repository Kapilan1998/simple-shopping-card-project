package com.testShoopingCard.shopping_card.Service;

import com.testShoopingCard.shopping_card.ApplicationConstants.Applicationconstants;
import com.testShoopingCard.shopping_card.Converter.CategoryConverter;
import com.testShoopingCard.shopping_card.Dto.CategoryDto;
import com.testShoopingCard.shopping_card.Dto.ResponseDto;
import com.testShoopingCard.shopping_card.Entity.Category;
import com.testShoopingCard.shopping_card.Exception.ServiceException;
import com.testShoopingCard.shopping_card.Repository.CategoryRepository;
import com.testShoopingCard.shopping_card.Service.Interfaces.CategoryInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService implements CategoryInterface {
    private final CategoryConverter categoryConverter;
    private final CategoryRepository categoryRepository;

    public ResponseDto saveCategory(CategoryDto categoryDto) {
        ResponseDto responseDto = new ResponseDto();
        boolean isNameAvailable = categoryRepository.existsByName(categoryDto.getName());
        log.info("Name availability status {}", isNameAvailable);
        if (isNameAvailable) {
            throw new ServiceException("This category already exist.", Applicationconstants.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
        Category category = categoryConverter.convertToEntity(categoryDto);
        categoryRepository.save(category);
        responseDto.setMessage("Category saved successfully");
        return responseDto;
    }

    public CategoryDto getCategoryById(Integer id) {
        categoryRepository.findById(id).orElseThrow(() -> new ServiceException("Category id not found", Applicationconstants.NOT_FOUND, HttpStatus.BAD_REQUEST));
        Category category = categoryRepository.findCategoryData(id);
        return categoryConverter.convertToDto(category);
    }

    public List<CategoryDto> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .map(categoryConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public ResponseDto updateCategory(CategoryDto categoryDto) {
        ResponseDto responseDto = new ResponseDto();
        if(categoryDto.getId() == null){
            throw new ServiceException("Category id is required", Applicationconstants.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
        Category updateCategory = categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new ServiceException("Category id not found", Applicationconstants.NOT_FOUND, HttpStatus.BAD_REQUEST));
        updateCategory.setName(categoryDto.getName());
        categoryRepository.save(updateCategory);
        responseDto.setMessage("Product updated successfully");
        return responseDto;
    }

    @Override
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public void deleteCategoryById(Integer id) {
categoryRepository.findById(id)
        .ifPresentOrElse(categoryRepository::delete,()->{
            throw new ServiceException("Category not found",Applicationconstants.NOT_FOUND,HttpStatus.BAD_REQUEST);
        });
    }
}
