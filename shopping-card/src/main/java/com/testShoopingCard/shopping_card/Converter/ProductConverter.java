package com.testShoopingCard.shopping_card.Converter;

import com.testShoopingCard.shopping_card.Dto.ImageDto;
import com.testShoopingCard.shopping_card.Dto.ProductDto;
import com.testShoopingCard.shopping_card.Entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductConverter {
    private final CategoryConverter categoryConverter;
    private final ImageConverter imageConverter;
    private final ModelMapper modelMapper;


    public Product convertToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setInventory(productDto.getInventory());
        product.setBrand(productDto.getBrand());
        product.setDescription(productDto.getDescription());
        return product;
    }

    /**
     * Converts a Product entity to a ProductDto.
     *
     * @param product the Product entity to convert
     * @return a ProductDto object with the mapped fields from the Product entity
     *
     * This method uses ModelMapper to automatically map fields between the Product entity
     * and the ProductDto if their field names and types are identical.
     *
     */
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product,ProductDto.class);
//        ProductDto productDto = new ProductDto();
//        productDto.setId(product.getId());
//        productDto.setName(product.getName());
//        productDto.setPrice(product.getPrice());
//        productDto.setInventory(product.getInventory());
//        productDto.setBrand(product.getBrand());
//        productDto.setDescription(product.getDescription());
        productDto.setCategory(categoryConverter.convertToDto(product.getCategory()));
        List<ImageDto> imageDtoList = product.getImages().stream().map(imageConverter::convertToDto).toList();
        productDto.setImages(imageDtoList);
        return productDto;
    }

    // Update the existing product with new details from ProductDto
    public Product updateEntity(ProductDto productDto, Product existingProduct) {
        existingProduct.setName(productDto.getName());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setInventory(productDto.getInventory());
        existingProduct.setBrand(productDto.getBrand());
        existingProduct.setDescription(productDto.getDescription());
        return existingProduct;
    }

}
