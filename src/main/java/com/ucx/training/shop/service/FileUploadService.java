package com.ucx.training.shop.service;


import com.ucx.training.shop.entity.FileUpload;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.FileUploadRepository;
import com.ucx.training.shop.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class FileUploadService extends BaseService<FileUpload, Integer>{

    @Value("${file.upload}")
    private String uploadDirName;
    private FileUploadRepository fileRepository;
    private ProductService productService;

    public FileUploadService(FileUploadRepository fileRepository, ProductService productService) {
        this.fileRepository = fileRepository;
        this.productService = productService;
    }

    public FileUpload uploadFile(MultipartFile file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        String directoryPathAsString = System.getProperty("user.dir") + uploadDirName;
        Path directoryPath = Paths.get(directoryPathAsString);
        if (!Files.exists(directoryPath)){
            Files.createDirectory(directoryPath);
        }
        String filePathAsString = directoryPathAsString + file.getOriginalFilename();
        Path filePath = Paths.get(filePathAsString);
        if (Files.exists(filePath)){
            throw new RuntimeException("File already exists!");
        }

        byte [] bytes = file.getBytes();
        Files.write(filePath, bytes);

        return FileUpload.builder()
                .fileName(file.getOriginalFilename())
                .filePath(filePathAsString)
                .fileExtension(FileUploadUtil.getFileExtension(file.getOriginalFilename()))
                .build();
    }

    public FileUpload save(FileUpload uploadedFile, Integer productId) throws NotFoundException{
        if (productId == null) {
            throw new IllegalArgumentException("Product id must exist");
        }
        Product product = productService.findById(productId);
        if (product == null) {
            throw new NotFoundException("Product could not be found" + productId);
        }
        uploadedFile.setProduct(product);
        return super.save(uploadedFile);
    }






}
