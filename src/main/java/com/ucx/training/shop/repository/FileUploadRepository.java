package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.FileUpload;
import com.ucx.training.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileUploadRepository extends BaseRepository<FileUpload, Integer> {

    FileUpload findByProduct(Product product);


}
