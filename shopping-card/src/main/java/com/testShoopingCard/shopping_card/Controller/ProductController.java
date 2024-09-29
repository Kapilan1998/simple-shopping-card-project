package com.testShoopingCard.shopping_card.Controller;

import com.testShoopingCard.shopping_card.Dto.ProductDto;
import com.testShoopingCard.shopping_card.Dto.ResponseDto;
import com.testShoopingCard.shopping_card.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Validated
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseDto addProduct(@Valid @RequestBody ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    @GetMapping("/getById")
    public ProductDto getProductById(@RequestParam("id") Integer id) {
        return productService.getProductDetailById(id);
    }

    @GetMapping("/getAllProductsByCategoryId")
    public List<ProductDto> getAllProductsByCategoryId(@RequestParam("categoryId") Integer categoryId) {
        return productService.getAllProductsByCategoryId(categoryId);
    }

    @PutMapping("/updateProduct")
    public ResponseDto updateProduct(@Valid @RequestBody ProductDto productDto) {
        return productService.updateProduct(productDto);
    }

    @DeleteMapping("/deletedProduct")
    public ResponseDto deleteProduct(@RequestParam("id") Integer id) {
        return productService.deleteProduct(id);
    }
}
