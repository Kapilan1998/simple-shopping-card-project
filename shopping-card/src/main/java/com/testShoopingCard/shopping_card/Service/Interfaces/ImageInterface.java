package com.testShoopingCard.shopping_card.Service.Interfaces;

import com.testShoopingCard.shopping_card.Dto.ImageDto;
import com.testShoopingCard.shopping_card.Entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageInterface {
    Image getImageById(Integer id);
    void deleteImageById(Integer id);
    List<ImageDto> saveImages(List<MultipartFile> file, Integer productId);
    void updateImage(MultipartFile file,Integer imageId);
}
