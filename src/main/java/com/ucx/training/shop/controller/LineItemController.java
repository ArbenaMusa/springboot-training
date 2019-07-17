package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.LineItemDTO;
import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.service.LineItemService;
import com.ucx.training.shop.util.CustomerUtil;
import com.ucx.training.shop.util.LineItemUtil;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Line;
import java.util.List;

@RestController
@RequestMapping("lineitems")
public class LineItemController extends BaseController<LineItem, Integer> {

    private LineItemService lineItemService;

    public LineItemController(LineItemService lineItemService) {
        this.lineItemService = lineItemService;
    }


    @PostMapping("/lineitems")
    public LineItemDTO create1(@RequestBody LineItem lineItem) {
        LineItemDTO lineItemDTO = LineItemUtil.getLineItem(lineItem, lineItem.getProduct());
        return lineItemDTO;
    }

    @GetMapping("/all{id}")
    public List<LineItemDTO> findAllSorted1(@RequestParam(required = false, defaultValue = "ASC") String direction, @PathVariable Integer id, @RequestParam(defaultValue = "id") String... properties) {
        List<LineItem> lineItems = lineItemService.findAllSorted(direction, properties);
        List<LineItemDTO> lineItemDTOList = LineItemUtil.getLineItems(lineItems);
        return lineItemDTOList;
    }

    @GetMapping("/pages")
    public List<LineItemDTO> findAllPaged1(@RequestParam int pageNumber, @RequestParam int pageSize) {
        Page<LineItem> lineItemPage = lineItemService.findAllPaged(pageNumber, pageSize);
        List<LineItem> lineItem = lineItemPage.getContent();
        List<LineItemDTO> lineItemDTOList = LineItemUtil.getLineItems(lineItem);
        return lineItemDTOList;
    }


}
