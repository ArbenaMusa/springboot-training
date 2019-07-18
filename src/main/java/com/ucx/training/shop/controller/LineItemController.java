package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.LineItemDTO;
import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.service.LineItemService;
import com.ucx.training.shop.util.LineItemUtil;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("lineitems")
public class LineItemController {

    private LineItemService lineItemService;

    public LineItemController(LineItemService lineItemService) {
        this.lineItemService = lineItemService;
    }

    @PostMapping
    public LineItemDTO create(@RequestBody LineItem lineItem) {
        LineItemDTO lineItemDTO = LineItemUtil.getLineItem(lineItem, lineItem.getProduct());
        return lineItemDTO;
    }

    @GetMapping
    public List<LineItemDTO> findAllSorted(@RequestParam(required = false, defaultValue = "ASC") String direction, @PathVariable Integer id, @RequestParam(defaultValue = "id") String... properties) {
        List<LineItem> lineItems = lineItemService.findAllSorted(direction, properties);
        List<LineItemDTO> lineItemDTOList = LineItemUtil.getLineItems(lineItems);
        return lineItemDTOList;
    }

    @GetMapping("/paged")
    public List<LineItemDTO> findAllPaged(@RequestParam int pageNumber, @RequestParam int pageSize) {
        Page<LineItem> lineItemPage = lineItemService.findAllPaged(pageNumber, pageSize);
        List<LineItem> lineItem = lineItemPage.getContent();
        List<LineItemDTO> lineItemDTOList = LineItemUtil.getLineItems(lineItem);
        return lineItemDTOList;
    }

}
