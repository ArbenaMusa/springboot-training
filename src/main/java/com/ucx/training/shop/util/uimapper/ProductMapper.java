package com.ucx.training.shop.util.uimapper;

import com.ucx.training.shop.dto.ProductDTO;
import com.ucx.training.shop.entity.Product;

public class ProductMapper {

    public static ProductDTO getProdDTO(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Given product is null");
        }
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setUnitPrice(product.getUnitPrice());
        productDTO.setInStock(product.getInStock());
        if (product.getFileUpload() != null) {
            productDTO.setFileName(product.getFileUpload().getFileName());
        }

        return productDTO;
    }
}
