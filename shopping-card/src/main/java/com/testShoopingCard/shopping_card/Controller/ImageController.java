package com.testShoopingCard.shopping_card.Controller;

import com.testShoopingCard.shopping_card.Dto.ApiResponseDto;
import com.testShoopingCard.shopping_card.Dto.ImageDto;
import com.testShoopingCard.shopping_card.Entity.Image;
import com.testShoopingCard.shopping_card.Service.ImageService;
import com.testShoopingCard.shopping_card.Service.Interfaces.ImageInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/images")
@Validated
@RequiredArgsConstructor
@Slf4j
public class ImageController {
    private final ImageInterface imageInterface;
    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponseDto> saveImages(List<MultipartFile> files, @RequestParam Integer productId) {
        try {
            List<ImageDto> imageDtos = imageInterface.saveImages(files, productId);
            return ResponseEntity.ok(new ApiResponseDto("Image uploaded", imageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDto("Upload failed", e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Integer imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource byteArrayResource = new ByteArrayResource(image.getImage().getBytes(1,(int)image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + image.getFileName()+ "\"")
                .body(byteArrayResource);
    }

    @PutMapping("/image/{imageId}/update ")
    public ResponseEntity<ApiResponseDto> updateImage(@PathVariable Integer imageId, @RequestBody MultipartFile file){
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null){
                imageService.updateImage(file,imageId);
                return ResponseEntity.ok(new ApiResponseDto("Image updated successfully",null));
            }
        } catch (ConfigDataResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponseDto(e.getMessage(),null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponseDto("Update Failed",INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/image/{imageId}/delete ")
    public ResponseEntity<ApiResponseDto> deleteImage(@PathVariable Integer imageId){
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null){
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponseDto("Image deleted successfully",null));
            }
        } catch (ConfigDataResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponseDto(e.getMessage(),null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponseDto("Image deleting process Failed",INTERNAL_SERVER_ERROR));
    }
}
