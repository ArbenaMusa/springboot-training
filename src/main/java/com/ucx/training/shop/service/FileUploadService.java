package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.FileUpload;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.repository.FileUploadRepository;
import com.ucx.training.shop.type.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileUploadService extends BaseService<FileUpload, Integer> {

    @Autowired
    private FileUploadRepository fileUploadRepository;

    public FileUpload findByProductAndRecordStatusActive(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("The given product is null");
        }

        FileUpload foundFileUpload = fileUploadRepository.findByProductAndRecordStatus(product, RecordStatus.ACTIVE);
        if (foundFileUpload == null) {
            throw new RuntimeException("There isn't a file upload with the given product and record status active");
        }
        return foundFileUpload;
    }
}
