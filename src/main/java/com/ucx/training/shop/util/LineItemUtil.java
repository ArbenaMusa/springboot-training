package com.ucx.training.shop.util;

import com.ucx.training.shop.dto.LineItemDTO;
import com.ucx.training.shop.dto.ProductDTO;
import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class LineItemUtil {

    public LineItemUtil() {

    }

    public static LineItemDTO getLineItem(LineItem lineItem, Product product) {
        LineItemDTO lineItemDTO = new LineItemDTO();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setFileName(product.getFileUpload().getFilePath());
        productDTO.setUnitPrice(product.getUnitPrice());
        productDTO.setName(product.getName());
        lineItemDTO.setQuantity(lineItem.getQuantity());
        lineItemDTO.setProduct(product.getName());

        return lineItemDTO;
    }

    public static List<LineItemDTO> getLineItems(List<LineItem> lineItemList) {
        LineItemDTO lineItemDTO = new LineItemDTO();
        List<LineItemDTO> lineItemDTOList = new ArrayList<>();
        lineItemList.forEach(e -> {
            Product product = e.getProduct();
            ProductDTO productDTO = new ProductDTO();
            productDTO.setName(product.getName());
            productDTO.setFileName(product.getFileUpload().getFilePath());
            productDTO.setUnitPrice(product.getUnitPrice());
            lineItemDTO.setProduct(product.getName());
            lineItemDTO.setQuantity(e.getQuantity());
            lineItemDTOList.add(lineItemDTO);
        });
        return lineItemDTOList;
    }
}




