package com.testShoopingCard.shopping_card.Dto;


import lombok.Data;

@Data
public class ImageDto {
    private Integer imageId;
    private String imageName;
    private String downloadURL;
    private String  fileSize;

}
