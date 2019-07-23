package com.ucx.training.shop.util.uimapper;

import com.ucx.training.shop.dto.LineItemDTO;
import com.ucx.training.shop.dto.ProductDTO;
import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.entity.Product;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class LineItemMapper {

    private LineItemMapper(){}

    //TODO: Handle exceptions here
    public static LineItemDTO getLineItem(LineItem lineItem, Product product) {
        if (lineItem == null || product == null) {
            throw new IllegalArgumentException("Either the LineItem or the Product is null!");
        }
        LineItemDTO lineItemDTO = new LineItemDTO();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setFileName(product.getFileUpload().getFilePath());
        productDTO.setUnitPrice(product.getUnitPrice());
        productDTO.setName(product.getName());
        lineItemDTO.setQuantity(lineItem.getQuantity());
        lineItemDTO.setProduct(product.getName());
        lineItemDTO.setInvoiceId(lineItem.getInvoice().getId());
        return lineItemDTO;
    }

    //TODO: Handle exceptions here
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
            lineItemDTO.setInvoiceId(e.getInvoice().getId());
            lineItemDTOList.add(lineItemDTO);
        });
        return lineItemDTOList;
    }
}




