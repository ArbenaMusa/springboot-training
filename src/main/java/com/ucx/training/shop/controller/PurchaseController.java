package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.*;
import com.ucx.training.shop.entity.Order;
import com.ucx.training.shop.entity.CartItem;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.CartItemService;
import com.ucx.training.shop.service.PurchaseService;
import com.ucx.training.shop.util.uimapper.DTOMapper;
import com.ucx.training.shop.util.uimapper.InvoiceMapper;
import com.ucx.training.shop.util.uimapper.LineItemMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("v1/purchases")
public class PurchaseController {

    private PurchaseService purchaseService;
    private CartItemService cartItemService;

    public PurchaseController(PurchaseService purchaseService, CartItemService cartItemService) {
        this.purchaseService = purchaseService;
        this.cartItemService = cartItemService;
    }

    @PostMapping("lineitems")
    public Map<String, Integer> addToCart(@RequestBody CartDTO cartDTO) throws ResponseException {
        Map<String, Integer> resultMap = new HashMap<>();
        try {
            Integer invoiceId = purchaseService.addToCart(cartDTO.getProductId(), cartDTO.getQuantity(), cartDTO.getOrderId());
            resultMap.put("invoiceId", invoiceId);
        } catch (IllegalArgumentException | NotFoundException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return resultMap;
    }

    /*@PostMapping
    public InvoiceDTO buy(@RequestBody PurchaseDTO purchaseDTO) throws ResponseException {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        try {
            Order order = purchaseService.buy(purchaseDTO.getCostumerId(), purchaseDTO.getInvoiceId());
            invoiceDTO.setCreatedDateTime(order.getCreateDateTime());
            invoiceDTO.setCostumerName(order.getCustomer().getName());
            invoiceDTO.setInvoiceNumber(order.getInvoiceNumber());
            invoiceDTO.setTotal(order.getTotal());
            invoiceDTO.setLineItemList(converToDTOList(order.getCart()));
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return invoiceDTO;
    }*/

    @PostMapping
    public DTOEntity buy(@RequestBody PurchaseDTO purchaseDTO) throws ResponseException {
        try {
            Order createdOrder = purchaseService.newBuy(purchaseDTO);
            return DTOMapper.convertToDto(createdOrder, InvoiceDTO.class);
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private LineItemDTO convertToDTO(CartItem cartItemList) {
        LineItemDTO lineItemDTO = LineItemDTO.builder()
                .product(cartItemList.getProduct().getName())
                .invoiceId(cartItemList.getOrder().getId())
                .quantity(cartItemList.getQuantity())
                .build();
        return lineItemDTO;
    }

    private List<LineItemDTO> converToDTOList(List<CartItem> cartItemList) {
        List<LineItemDTO> lineItemDTOS = cartItemList.stream()
                .map(lineItem -> convertToDTO(lineItem))
                .collect(Collectors.toList());
        return lineItemDTOS;
    }

    @DeleteMapping("lineitems/{id}")
    public LineItemDTO cancelLineItem(@PathVariable Integer id) throws ResponseException {
        try {
            CartItem cartItem = purchaseService.cancelLineItem(id);
            LineItemDTO lineItemDTO = LineItemMapper.getLineItem(cartItem, cartItem.getProduct());
            return lineItemDTO;
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    public InvoiceDTO cancelPurchase(@PathVariable Integer id) throws ResponseException {
        try {
            Order order = purchaseService.cancelPurchase(id);
            InvoiceDTO invoiceDTO = InvoiceMapper.getInvoice(order);
            return invoiceDTO;
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("lineitems/{lineItemId}")
    public LineItemDTO changeQuantity(@RequestBody CartItem cartItem, @PathVariable Integer lineItemId) throws NotFoundException, ResponseException {
        try {
            CartItem updatedCartItem = purchaseService.changeQuantity(cartItem, lineItemId);
            return new LineItemDTO(updatedCartItem.getProduct().getName(), updatedCartItem.getQuantity(), updatedCartItem.getOrder().getId());
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
