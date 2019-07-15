package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileUploadRepository extends JpaRepository<FileUpload, Integer> {

}
