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



@Service
@Transactional
@Log4j2
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

    public FileUpload updateRecordStatus(Integer productId) throws NotFoundException{
        Product foundProduct = productService.findById(productId);
        if(foundProduct == null){
            throw new NotFoundException("Product doesn't exist");
        }
        FileUpload foundFileUpload = fileRepository.findByProduct(foundProduct);
        super.remove(foundFileUpload.getId());
        return foundFileUpload;

    }

    public void updatePicture(MultipartFile file, Integer productId) throws NotFoundException,IOException{
        updateRecordStatus(productId);
        FileUpload fileUpload = uploadFile(file);
        save(fileUpload,productId);
    }

    public FileUpload updateImage(MultipartFile file,Product product) throws NotFoundException,IOException{
       // updateRecordStatus(product);
        FileUpload fileUpload = uploadFile(file);
        return save(fileUpload,product.getId());
    }



}
