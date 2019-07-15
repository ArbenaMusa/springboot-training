package com.ucx.training.shop.service;


import com.ucx.training.shop.entity.FileUpload;
import com.ucx.training.shop.repository.FileUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class FileUploadService {

    private String upload_Dir = "/Users/Andi/IdeaProjects/springboot-training/src/main/resources/uploaded-files/";

    @Autowired
    private FileUploadRepository fileRepository;


    private String getFileExtension(String fileName){
        int index = fileName.lastIndexOf(".");
        if(index < 0){
            return null;
        }
        return fileName.substring(index+1);
    }

    public FileUpload uploadFile(MultipartFile file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        byte [] bytes = file.getBytes();
        String fileName = file.getOriginalFilename();
        Path path = Paths.get(upload_Dir + fileName);
        Files.write(path,bytes);

        String extension = getFileExtension(fileName);


        return new FileUpload(upload_Dir,fileName,extension);

    }

    public void save(FileUpload uploadedFile){
        fileRepository.save(uploadedFile);
    }






}
