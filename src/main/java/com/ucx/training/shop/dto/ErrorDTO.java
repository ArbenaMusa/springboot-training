package com.ucx.training.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorDTO implements DTOEntity{

    private LocalDateTime timeStamp;
    private String errorMessage;
    private String path;

    @Builder
    public ErrorDTO(LocalDateTime timeStamp, String errorMessage, String path) {
        this.timeStamp = timeStamp;
        this.errorMessage = errorMessage;
        this.path = path;
    }
}
