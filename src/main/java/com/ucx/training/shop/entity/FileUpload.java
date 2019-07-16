package com.ucx.training.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class FileUpload extends BaseEntity<Integer> {

    private String filePath;
    private String fileName;
    private String fileExtension;
    @OneToOne
    private Product product;

    @Builder
    public FileUpload(String filePath, String fileName, String fileExtension) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
    }
}
