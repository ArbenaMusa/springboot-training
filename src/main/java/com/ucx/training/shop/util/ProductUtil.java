package com.ucx.training.shop.util;

import com.ucx.training.shop.dto.ProductDTO;
import com.ucx.training.shop.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductUtil {

    private ProductUtil() {}

    //TODO: Handle exceptions here
    public static List<ProductDTO> getProducts(List<Product> productList) {
        ProductDTO productDTO = new ProductDTO();
        List<ProductDTO> productDTOList = new ArrayList<>();

        productList.forEach(e -> {
            productDTO.setName(e.getName());
            productDTO.setUnitPrice(e.getUnitPrice());
            productDTO.setFileName(e.getFileUpload().getFilePath());
            productDTOList.add(productDTO);
        });

        return productDTOList;
    }

    //TODO: Throw exceptions here
    public static ProductDTO getProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null");
        }
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setUnitPrice(product.getUnitPrice());
        //productDTO.setFileName(product.getFileUpload().getFilePath());
        return productDTO;
    }
}