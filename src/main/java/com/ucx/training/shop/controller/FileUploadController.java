package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.FileUpload;;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.service.FileUploadService;
import com.ucx.training.shop.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Log4j2
@RestController
@RequestMapping("upload")
public class FileUploadController {

    private FileUploadService fileUploadService;
    private ProductService productService;

    public FileUploadController(FileUploadService fileUploadService,ProductService productService) {
        this.productService = productService;
        this.fileUploadService = fileUploadService;
    }

    @PostMapping
    public FileUpload uploadFile(@RequestParam("files") MultipartFile file, @RequestParam("productId") Integer productId) {
        FileUpload uploadedFile = null;
        try {
            uploadedFile = fileUploadService.uploadFile(file);
            fileUploadService.save(uploadedFile, productId);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        } catch(NotFoundException nfe){
                log.error(nfe.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (uploadedFile == null) throw new RuntimeException("File upload failed");
        return uploadedFile;
    }

    @PutMapping
    public FileUpload updatePicture(@RequestParam("files") MultipartFile file, @RequestParam("productId") Integer productId) {
        FileUpload fileUpload = null;
        FileUpload files = null;
        try {
           fileUploadService.updateRecordStatus(productId);
            fileUpload = this.uploadFile(file, productId);
            files = fileUploadService.update(fileUpload,productId);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return files;
    }
}


