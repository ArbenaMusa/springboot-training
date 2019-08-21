package com.ucx.training.shop.util;

import org.springframework.http.MediaType;

public class FileUploadUtil {

    private FileUploadUtil() {
    }

    public static String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index < 0) {
            return null;
        }
        return fileName.substring(index + 1);
    }

    public static MediaType getContentType(String fileName) {
        String extension = getFileExtension(fileName).toLowerCase();
        MediaType mediaType = null;
        switch (extension) {
            case "jpeg":
                mediaType = MediaType.IMAGE_JPEG;
                break;
            case "gif":
                mediaType = MediaType.IMAGE_GIF;
                break;
            case "png":
                mediaType = MediaType.IMAGE_PNG;
                break;
            default:
                break;
        }
        if (mediaType == null) {
            throw new RuntimeException("Unsupported image type");
        }
        return mediaType;
    }

}
