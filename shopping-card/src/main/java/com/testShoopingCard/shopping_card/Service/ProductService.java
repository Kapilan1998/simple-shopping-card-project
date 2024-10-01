package com.testShoopingCard.shopping_card.Service;

import com.testShoopingCard.shopping_card.ApplicationConstants.Applicationconstants;
import com.testShoopingCard.shopping_card.Converter.ProductConverter;
import com.testShoopingCard.shopping_card.Dto.ProductDto;
import com.testShoopingCard.shopping_card.Dto.ResponseDto;
import com.testShoopingCard.shopping_card.Entity.Category;
import com.testShoopingCard.shopping_card.Entity.Product;
import com.testShoopingCard.shopping_card.Exception.ServiceException;
import com.testShoopingCard.shopping_card.Repository.CategoryRepository;
import com.testShoopingCard.shopping_card.Repository.ProductRepository;
import com.testShoopingCard.shopping_card.Service.Interfaces.ProductInterface;
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
public class ProductService implements ProductInterface {
    private final ProductConverter productConverter;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseDto addProduct(ProductDto productDto) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Category> existingCategoryOpt = categoryRepository.findByName(productDto.getCategory().getName());
        if (existingCategoryOpt.isEmpty()) {
            throw new ServiceException("This category is not found", Applicationconstants.NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        boolean isProductExist = productRepository.existsByName(productDto.getName());
        if (isProductExist) {
            throw new ServiceException("This product is already available", Applicationconstants.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
        Product product = productConverter.convertToEntity(productDto);
        // Set the existing category on the product
        product.setCategory(existingCategoryOpt.get());
        productRepository.save(product);

        responseDto.setMessage("Product added successfully");
        return responseDto;
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new ServiceException("Product id not found", Applicationconstants.BAD_REQUEST, HttpStatus.BAD_REQUEST));

    }

    public ProductDto getProductDetailById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ServiceException("Product id not found", Applicationconstants.BAD_REQUEST, HttpStatus.BAD_REQUEST));
        return productConverter.convertToDto(product);
    }

    public ResponseDto deleteProduct(Integer id) {
        productRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Sorry, unable to find given product", Applicationconstants.BAD_REQUEST, HttpStatus.BAD_REQUEST));
        productRepository.deleteById(id);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Product deleted successfully");
        return responseDto;
    }


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    public List<ProductDto> getAllProductsByCategoryId(Integer categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(() -> new ServiceException("Category id not found", Applicationconstants.NOT_FOUND, HttpStatus.BAD_REQUEST));
        List<Product> products = productRepository.findAllByCategoryId(categoryId);
        return products.stream()
                .map(productConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public ResponseDto updateProduct(ProductDto productDto) {
        ResponseDto responseDto = new ResponseDto();
        if (productDto.getId() == null) {
            throw new ServiceException("Product id is required", Applicationconstants.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
        Optional<Category> existingCategoryOpt = categoryRepository.findByName(productDto.getCategory().getName());
        if (existingCategoryOpt.isEmpty()) {
            throw new ServiceException("This category is not found", Applicationconstants.NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        Product productUpdate = productRepository.findById(productDto.getId())
                .orElseThrow(() -> new ServiceException("Given product id is not found", Applicationconstants.NOT_FOUND, HttpStatus.BAD_REQUEST));
        // Update product details (without modifying images or creating a new product)
        productConverter.updateEntity(productDto, productUpdate);
        productUpdate.setCategory(existingCategoryOpt.get());
        productRepository.save(productUpdate);
        responseDto.setMessage("Product updated successfully");
        return responseDto;
    }

}
