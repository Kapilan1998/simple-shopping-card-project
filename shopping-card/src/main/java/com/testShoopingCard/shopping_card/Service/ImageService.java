package com.testShoopingCard.shopping_card.Service;

import com.testShoopingCard.shopping_card.ApplicationConstants.Applicationconstants;
import com.testShoopingCard.shopping_card.Dto.ImageDto;
import com.testShoopingCard.shopping_card.Entity.Image;
import com.testShoopingCard.shopping_card.Entity.Product;
import com.testShoopingCard.shopping_card.Exception.ServiceException;
import com.testShoopingCard.shopping_card.Repository.ImageRepository;
import com.testShoopingCard.shopping_card.Service.Interfaces.ImageInterface;
import com.testShoopingCard.shopping_card.Service.Interfaces.ProductInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements ImageInterface {
    private final ImageRepository imageRepository;
    private final ProductInterface productInterface;
    private final ProductService productService;


    @Override
    public Image getImageById(Integer id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Image id not found", Applicationconstants.NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    @Override
    public void deleteImageById(Integer id) {
        imageRepository.findById(id)
                .ifPresentOrElse(imageRepository::delete,
                        () -> new ServiceException("Image id not found", Applicationconstants.NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> documents, Integer productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> imageDtos = new ArrayList<>();
        for(MultipartFile file:documents){
            try{
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                double size= (double) file.getSize() / (1024 * 1024);
                image.setFileSize(String.format("%.2f MB", size));
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String downloadURL = "/api/v1/images/image/download/"+ image.getId();
                image.setDownloadURL(downloadURL);

                Image savedImage = imageRepository.save(image);
                savedImage.setDownloadURL( "/api/v1/images/image/download/"+savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadURL(savedImage.getDownloadURL());
                imageDto.setFileSize(savedImage.getFileSize());
                imageDtos.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return imageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Integer imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            double size= (double) file.getSize() / (1024 * 1024);
            image.setFileSize(String.format("%.2f MB", size));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
