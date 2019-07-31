package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.DTOEntity;
import com.ucx.training.shop.dto.LineItemDTO;
import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.LineItemService;
import com.ucx.training.shop.util.uimapper.DTOMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Log4j2
@RestController
@RequestMapping("v1/lineitems")
public class LineItemController {

    private LineItemService lineItemService;

    public LineItemController(LineItemService lineItemService) {
        this.lineItemService = lineItemService;
    }

    @GetMapping
    public List<DTOEntity> findAllSorted(@RequestParam(required = false, defaultValue = "ASC") String direction, @PathVariable Integer id, @RequestParam(defaultValue = "id") String... properties) throws ResponseException {
        try {
            List<LineItem> lineItems = lineItemService.findAllSorted(direction, properties);
            return DTOMapper.converToDTOList(lineItems,new LineItemDTO());
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/paged")
    public List<DTOEntity> findAllPaged(@RequestParam int pageNumber, @RequestParam int pageSize) throws ResponseException {
        try {
            Page<LineItem> lineItemPage = lineItemService.findAllPaged(pageNumber, pageSize);
            List<LineItem> lineItem = lineItemPage.getContent();
            return DTOMapper.converToDTOList(lineItem,new LineItemDTO());
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public DTOEntity findById(@PathVariable Integer id) throws ResponseException {
        try {
            LineItem lineItem = lineItemService.findById(id);
            return new DTOMapper().convertToDto(lineItem, new LineItemDTO());
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
