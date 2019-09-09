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

import java.util.List;
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

    @PostMapping
    public DTOEntity buy(@RequestBody PurchaseDTO purchaseDTO) throws ResponseException {
        try {
            Order createdOrder = purchaseService.buy(purchaseDTO);
            return DTOMapper.convertToDto(createdOrder, InvoiceDTO.class);
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
