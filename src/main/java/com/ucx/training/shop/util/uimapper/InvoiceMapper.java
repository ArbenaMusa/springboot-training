package com.ucx.training.shop.util.uimapper;


import com.ucx.training.shop.dto.InvoiceDTO;
import com.ucx.training.shop.dto.LineItemDTO;
import com.ucx.training.shop.entity.Invoice;
import com.ucx.training.shop.entity.LineItem;

import java.util.ArrayList;
import java.util.List;

public class InvoiceMapper {

    private InvoiceMapper(){}

    public static List<InvoiceDTO> getInvoices(List<Invoice> list){
        List<LineItemDTO> lineItemDTOList = new ArrayList<>();
        List<InvoiceDTO> invoiceDTOList = new ArrayList<>();
        list.forEach(e->{
            InvoiceDTO invoiceDTO = new InvoiceDTO();
            List<LineItem> lineItems = e.getLineItemList();
            lineItems.forEach(f->{
                LineItemDTO lineItemDTO = new LineItemDTO();
                lineItemDTO.setQuantity(f.getQuantity());
                lineItemDTO.setProduct(f.getProduct().getName());
                lineItemDTO.setInvoiceId(f.getInvoice().getId());
                lineItemDTOList.add(lineItemDTO);
            });
            invoiceDTO.setTotal(e.getTotal());
            invoiceDTO.setCreatedDateTime(e.getCreateDateTime());
            invoiceDTO.setInvoiceNumber(e.getInvoiceNumber());
            invoiceDTO.setCostumerName(e.getCostumer().getName());
            invoiceDTO.setLineItemList(lineItemDTOList);
            invoiceDTOList.add(invoiceDTO);
        });
            return invoiceDTOList;
    }

    public static InvoiceDTO getInvoice(Invoice invoice){
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice cannot be null!");
        }
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        LineItemDTO lineItemDTO = new LineItemDTO();
        List<LineItemDTO> lineItemDTOList = new ArrayList<>();
        List<LineItem> lineItemList = invoice.getLineItemList();

        lineItemList.forEach(e->{
            lineItemDTO.setInvoiceId(e.getInvoice().getId());
            lineItemDTO.setProduct(e.getProduct().getName());
            lineItemDTO.setQuantity(e.getQuantity());
            lineItemDTOList.add(lineItemDTO);
        });
        invoiceDTO.setCostumerName(invoice.getCostumer().getName());
        invoiceDTO.setInvoiceNumber(invoice.getInvoiceNumber());
        invoiceDTO.setTotal(invoice.getTotal());
        invoiceDTO.setCreatedDateTime(invoice.getCreateDateTime());
        invoiceDTO.setLineItemList(lineItemDTOList);
        return invoiceDTO;
    }
}
