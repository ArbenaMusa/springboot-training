package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.BrandDTO;
import com.ucx.training.shop.dto.LineItemDTO;
import com.ucx.training.shop.entity.CartItem;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.CartItemService;
import com.ucx.training.shop.util.PaginationUtil;
import com.ucx.training.shop.util.uimapper.LineItemMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("v1/lineitems")
public class CartItemController {

    private CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public List<LineItemDTO> findAllSorted(@RequestParam(required = false, defaultValue = "ASC") String direction, @PathVariable Integer id, @RequestParam(defaultValue = "id") String... properties) throws ResponseException {
        try {
            List<CartItem> cartItems = cartItemService.findAllSorted(direction, properties);
            List<LineItemDTO> lineItemDTOList = LineItemMapper.getLineItems(cartItems);
            return lineItemDTOList;
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/paged")
    public Map<String, Object> findAllPaged(@PageableDefault Pageable pageable) throws ResponseException {
        try {
            return PaginationUtil.getPage(cartItemService.findPaged(pageable), LineItemDTO.class);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public LineItemDTO findById(@PathVariable Integer id) throws ResponseException {
        try {
            CartItem cartItem = cartItemService.findById(id);
            return LineItemMapper.getLineItem(cartItem, cartItem.getProduct());
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
