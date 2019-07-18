package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.FileUpload;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.FileUploadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("upload")
@Log4j2
public class FileUploadController {

    private FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    //TODO: Implement upload logic here
    @PostMapping
    public FileUpload uploadFile(@RequestParam("files") MultipartFile file, @RequestParam("productId") Integer productId) throws ResponseException {
        FileUpload uploadedFile = null;
        try {
            uploadedFile = fileUploadService.uploadFile(file);
            fileUploadService.save(uploadedFile, productId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        if (uploadedFile == null) throw new RuntimeException("File upload failed");
        return uploadedFile;
    }

}


