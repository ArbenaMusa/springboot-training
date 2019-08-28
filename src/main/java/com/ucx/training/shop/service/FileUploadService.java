package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.FileUpload;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.FileUploadRepository;
import com.ucx.training.shop.type.RecordStatus;
import com.ucx.training.shop.util.FileUploadUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
@Transactional
@Log4j2
public class FileUploadService extends BaseService<FileUpload, Integer> {

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
        if (!Files.exists(directoryPath)) {
            Files.createDirectory(directoryPath);
        }
        String filePathAsString = directoryPathAsString + file.getOriginalFilename();
        Path filePath = Paths.get(filePathAsString);

        byte[] bytes = file.getBytes();
        Files.write(filePath, bytes, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);

        return FileUpload.builder()
                .fileName(file.getOriginalFilename())
                .filePath(filePathAsString)
                .fileExtension(FileUploadUtil.getFileExtension(file.getOriginalFilename()))
                .build();
    }


    public FileUpload save(FileUpload uploadedFile, Integer productId) throws NotFoundException {
        if (uploadedFile == null || productId == null) {
            throw new IllegalArgumentException("Product id must exist");
        }
        Product foundProduct = productService.findById(productId);
        if (foundProduct == null) {
            throw new NotFoundException("Product could not be found" + productId);
        }
        FileUpload foundFileUpload = fileRepository.findByProductAndRecordStatus(foundProduct, RecordStatus.ACTIVE);
        if (foundFileUpload == null) {
            uploadedFile.setProduct(foundProduct);
            return super.save(uploadedFile);
        }
        return super.update(uploadedFile, foundFileUpload.getId());
    }
}