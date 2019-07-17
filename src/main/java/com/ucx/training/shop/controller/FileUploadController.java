package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.FileUpload;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.service.FileUploadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("upload")
@Log4j2
public class FileUploadController {

    private FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
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
}


