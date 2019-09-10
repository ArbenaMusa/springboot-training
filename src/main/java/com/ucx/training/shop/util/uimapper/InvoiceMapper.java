package com.ucx.training.shop.util.uimapper;


import com.ucx.training.shop.dto.InvoiceDTO;
import com.ucx.training.shop.dto.LineItemDTO;
import com.ucx.training.shop.entity.Order;
import com.ucx.training.shop.entity.CartItem;

import java.util.ArrayList;
import java.util.List;

public class InvoiceMapper {

    private InvoiceMapper(){}

    public static List<InvoiceDTO> getInvoices(List<Order> list){
        List<LineItemDTO> lineItemDTOList = new ArrayList<>();
        List<InvoiceDTO> invoiceDTOList = new ArrayList<>();
        list.forEach(e->{
            InvoiceDTO invoiceDTO = new InvoiceDTO();
            List<CartItem> cartItems = e.getCart();
            cartItems.forEach(f->{
                LineItemDTO lineItemDTO = new LineItemDTO();
                lineItemDTO.setQuantity(f.getQuantity());
                lineItemDTO.setProduct(f.getProduct().getName());
                lineItemDTO.setInvoiceId(f.getOrder().getId());
                lineItemDTOList.add(lineItemDTO);
            });
            invoiceDTO.setTotal(e.getTotal());
            invoiceDTO.setCreateDateTime(e.getCreateDateTime());
            invoiceDTO.setCostumerName(e.getCustomer().getName());
            invoiceDTO.setLineItemList(lineItemDTOList);
            invoiceDTOList.add(invoiceDTO);
        });
            return invoiceDTOList;
    }

    public static InvoiceDTO getInvoice(Order order){
        if (order == null) {
            throw new IllegalArgumentException("Invoice cannot be null!");
        }
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        LineItemDTO lineItemDTO = new LineItemDTO();
        List<LineItemDTO> lineItemDTOList = new ArrayList<>();
        List<CartItem> cartItemList = order.getCart();

        cartItemList.forEach(e->{
            lineItemDTO.setInvoiceId(e.getOrder().getId());
            lineItemDTO.setProduct(e.getProduct().getName());
            lineItemDTO.setQuantity(e.getQuantity());
            lineItemDTOList.add(lineItemDTO);
        });
        invoiceDTO.setCostumerName(order.getCustomer().getName());
        invoiceDTO.setTotal(order.getTotal());
        invoiceDTO.setCreateDateTime(order.getCreateDateTime());
        invoiceDTO.setLineItemList(lineItemDTOList);
        return invoiceDTO;
    }
}
