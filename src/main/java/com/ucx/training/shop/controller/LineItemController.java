package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.LineItemDTO;
import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.LineItemService;
import com.ucx.training.shop.util.LineItemUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Log4j2
@RestController
@RequestMapping("lineitems")
public class LineItemController {

    private LineItemService lineItemService;

    public LineItemController(LineItemService lineItemService) {
        this.lineItemService = lineItemService;
    }

    //TODO: To be reviewed.
    @PostMapping
    public LineItemDTO create(@RequestBody LineItem lineItem) throws ResponseException {
        try {
            LineItem createdLineItem = lineItemService.save(lineItem);
            LineItemDTO lineItemDTO = LineItemUtil.getLineItem(createdLineItem, lineItem.getProduct());
            return lineItemDTO;
        } catch (IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{id}")
    public void update(@RequestBody LineItem lineItem, @PathVariable Integer id) throws ResponseException {
        try {
            LineItem updatedInvoice = lineItemService.update(lineItem, id);
//            return LineItemUtil.getLineItem(updatedInvoice);
        } catch (IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<LineItemDTO> findAllSorted(@RequestParam(required = false, defaultValue = "ASC") String direction, @RequestParam(defaultValue = "id") String... properties) throws ResponseException {
        try {
            List<LineItem> lineItems = lineItemService.findAllSorted(direction, properties);
            List<LineItemDTO> lineItemDTOList = LineItemUtil.getLineItems(lineItems);
            return lineItemDTOList;
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public LineItem findById(@PathVariable Integer id) throws ResponseException {
        try {
            LineItem lineItem = lineItemService.findById(id);
            return lineItem;
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/paged")
    public List<LineItemDTO> findAllPaged(@RequestParam int pageNumber, @RequestParam int pageSize) throws ResponseException {
        try {
            Page<LineItem> lineItemPage = lineItemService.findAllPaged(pageNumber, pageSize);
            List<LineItem> lineItem = lineItemPage.getContent();
            List<LineItemDTO> lineItemDTOList = LineItemUtil.getLineItems(lineItem);
            return lineItemDTOList;
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable Integer id) throws ResponseException {
        try {
            lineItemService.remove(id);
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
