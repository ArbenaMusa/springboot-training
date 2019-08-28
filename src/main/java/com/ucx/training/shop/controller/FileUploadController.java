package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.FileUpload;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.FileUploadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("v1/upload")
@Log4j2
public class FileUploadController {

    private FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping
    public FileUpload uploadFile(@RequestParam("files") MultipartFile file, @RequestParam("productId") Integer productId) throws ResponseException {
        FileUpload uploadedFile = null,
                savedUpload = null;
        try {
            uploadedFile = fileUploadService.uploadFile(file);
            savedUpload = fileUploadService.save(uploadedFile, productId);
        } catch (IOException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        if (uploadedFile == null) throw new ResponseException("File upload failed", HttpStatus.BAD_REQUEST);
        return savedUpload;
    }


}


