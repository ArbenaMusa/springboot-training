package com.ucx.training.shop.util;

import com.ucx.training.shop.dto.ProductDTO;
import com.ucx.training.shop.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductUtil {

    public ProductUtil() {

    }

    public List<ProductDTO> getProducts(List<Product> productList) {
        ProductDTO productDTO = new ProductDTO();
        List<ProductDTO> productDTOList = new ArrayList<>();

        productList.forEach(e -> {
            productDTO.setName(e.getName());
            productDTO.setUnitPrice(e.getUnitPrice());
            productDTO.setFilePath(e.getFileUpload().getFilePath());
            productDTOList.add(productDTO);
        });

        return productDTOList;

    }


    public ProductDTO getProduct(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setUnitPrice(product.getUnitPrice());
        productDTO.setFilePath(product.getFileUpload().getFilePath());
        return productDTO;
    }
}
