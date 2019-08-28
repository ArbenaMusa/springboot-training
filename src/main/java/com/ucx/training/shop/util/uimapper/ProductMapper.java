package com.ucx.training.shop.util.uimapper;

import com.ucx.training.shop.dto.BrandDTO;
import com.ucx.training.shop.dto.PlatformDTO;
import com.ucx.training.shop.dto.ProductDTO;
import com.ucx.training.shop.entity.Product;

public class ProductMapper {

    public static ProductDTO getProdDTO(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Given product is null");
        }
        ProductDTO productDTO = new ProductDTO();
        if (product.getId() != null) {
            productDTO.setId(product.getId());
        }
        productDTO.setName(product.getName());
        productDTO.setUnitPrice(product.getUnitPrice());
        productDTO.setInStock(product.getInStock());
        if (product.getFileUpload() != null) {
            productDTO.setFileName(product.getFileUpload().getFileName());
        }
        if (product.getPlatform() != null) {
            PlatformDTO platformDTO = (PlatformDTO) DTOMapper.convertToDto(product.getPlatform(), PlatformDTO.class);
            productDTO.setPlatform(platformDTO);
        }
        if (product.getBrand() != null) {
            BrandDTO brandDTO = (BrandDTO) DTOMapper.convertToDto(product.getBrand(), BrandDTO.class);
            productDTO.setBrand(brandDTO);
        }

        return productDTO;
    }
}
