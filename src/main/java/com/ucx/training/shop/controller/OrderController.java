package com.ucx.training.shop.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.ucx.training.shop.dto.BrandDTO;
import com.ucx.training.shop.dto.InvoiceDTO;
import com.ucx.training.shop.dto.LineItemDTO;
import com.ucx.training.shop.entity.Order;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.OrderService;
import com.ucx.training.shop.service.CartItemService;
import com.ucx.training.shop.type.Quartal;
import com.ucx.training.shop.util.PaginationUtil;
import com.ucx.training.shop.util.uimapper.InvoiceMapper;
import com.ucx.training.shop.util.uimapper.LineItemMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("v1/invoices")
public class OrderController {

    private CartItemService cartItemService;
    private OrderService orderService;

    public OrderController(CartItemService cartItemService, OrderService orderService) {
        this.cartItemService = cartItemService;
        this.orderService = orderService;
    }

    @PutMapping("{id}")
    public InvoiceDTO update(@RequestBody Order order, @PathVariable Integer id) throws ResponseException {
        try {
            Order updatedOrder = orderService.update(order.getCart(), order.getCustomer(), order);
            return InvoiceMapper.getInvoice(updatedOrder);
        } catch (IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/lineitems/{invoiceId}")
    public List<LineItemDTO> getLineItemsByInvoiceId(@PathVariable Integer invoiceId) throws ResponseException {
        try {
            List <LineItemDTO> lineItemDTOs=new ArrayList<>();
            lineItemDTOs= LineItemMapper.getLineItems(cartItemService.findAllByInvoiceId(invoiceId));
            return lineItemDTOs;
        } catch (IllegalArgumentException | NotFoundException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public InvoiceDTO findById(@PathVariable Integer id) throws ResponseException {
        try {
            Order order = orderService.findById(id);
            return InvoiceMapper.getInvoice(order);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<InvoiceDTO> findAllSorted(@RequestParam(required = false, defaultValue = "ASC") String direction, @PathVariable Integer id, @RequestParam(defaultValue = "id") String... properties) throws ResponseException {
        try {
            List<Order> orderList = orderService.findAllSorted(direction, properties);
            List<InvoiceDTO> invoiceDTOS = InvoiceMapper.getInvoices(orderList);
            return invoiceDTOS;
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/paged")
    public Map<String, Object> findAllPaged(@PageableDefault Pageable pageable) throws ResponseException {
        try {
            return PaginationUtil.getPage(orderService.findPaged(pageable), InvoiceDTO.class);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/history/paged")
    public ResponseEntity<List<JsonNode>> getOrderHistory(@PageableDefault Pageable pageable) throws ResponseException {
        try {
            return ResponseEntity.ok().body(orderService.readOrderHistory(pageable));
        } catch (Exception exception) {
            throw new ResponseException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/stats")
    Map<String, Object> getQuartalStats(){
        return orderService.getQuartalStats();
    }
}
