package com.ucx.training.shop.util.uimapper;

import com.ucx.training.shop.dto.ProductDTO;
import com.ucx.training.shop.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    private ProductMapper() {}

    //TODO: Handle exceptions here
    public static List<ProductDTO> getProducts(List<Product> productList) {
        ProductDTO productDTO = new ProductDTO();
        List<ProductDTO> productDTOList = new ArrayList<>();

        productList.forEach(e -> {
            productDTO.setName(e.getName());
            productDTO.setUnitPrice(e.getUnitPrice());
            if (e.getFileUpload() != null) {
                productDTO.setFileName(e.getFileUpload().getFileName());
            }
            productDTO.setBrand(e.getBrand().getName());
            productDTO.setCategory(e.getCategory().getName());
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
        productDTO.setBrand(product.getBrand().getName());
        productDTO.setCategory(product.getCategory().getName());
        if (product.getFileUpload() != null) {
            productDTO.setFileName(product.getFileUpload().getFileName());
        }
        return productDTO;
    }
}
