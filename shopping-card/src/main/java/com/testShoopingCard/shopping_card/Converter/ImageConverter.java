package com.testShoopingCard.shopping_card.Converter;

import com.testShoopingCard.shopping_card.Dto.ImageDto;
import com.testShoopingCard.shopping_card.Entity.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageConverter {
    public ImageDto convertToDto(Image image) {
        ImageDto imageDto = new ImageDto();
        imageDto.setImageId(image.getId());
        imageDto.setImageName(image.getFileName());
        imageDto.setDownloadURL(image.getDownloadURL());
        imageDto.setFileSize(image.getFileSize());
        return imageDto;
    }
}
