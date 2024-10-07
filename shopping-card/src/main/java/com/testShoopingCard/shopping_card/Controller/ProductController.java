package com.testShoopingCard.shopping_card.Controller;

import com.testShoopingCard.shopping_card.Dto.ProductDto;
import com.testShoopingCard.shopping_card.Dto.ResponseDto;
import com.testShoopingCard.shopping_card.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // adding method level security here as only accessible by admin
    // if many roles need to access, use  (@PreAuthorize("hasAnyRole('Admin', 'Manager','Student','Teacher')"))
    // only 1 role can be accessed // If the authenticated user has this role, access is granted.
    // @PreAuthorize("hasRole('ADMIN')")   // Automatically checks for "ROLE_ADMIN   //  here ROLE_ADMIN must be used, but i didn't set as ROLE_ADMIN, just i set as ADMIN, i used hasAuthority below
    @PreAuthorize("hasAuthority('ADMIN')")
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateProduct")
    public ResponseDto updateProduct(@Valid @RequestBody ProductDto productDto) {
        return productService.updateProduct(productDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deletedProduct")
    public ResponseDto deleteProduct(@RequestParam("id") Integer id) {
        return productService.deleteProduct(id);
    }
}
