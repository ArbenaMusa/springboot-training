package com.ucx.training.shop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    public void uploadFile(MultipartFile file) throws IOException {
        String uploadedFilesDirectory = "D:\\springboot-training\\src\\main\\resources\\uploaded-files\\";
        byte[] fileToByteArray = file.getBytes();
        Path path = Paths.get(uploadedFilesDirectory + file.getOriginalFilename());
        Files.write(path, fileToByteArray);
    }
}
