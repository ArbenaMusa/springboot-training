package com.ucx.training.shop.util;

import com.ucx.training.shop.dto.DTOEntity;
import com.ucx.training.shop.dto.ProductDTO;
import com.ucx.training.shop.entity.BaseEntity;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.util.uimapper.DTOMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaginationUtil {

    public static  <T extends BaseEntity> Map<String, Object> getPage(Page<T> page,Class dtoClass) throws ResponseException {
        try {
            Map<String, Object> resultMap = new HashMap<>();
            List<T> rows = page.getContent();

            List<DTOEntity> content = DTOMapper.converToDTOList(rows, dtoClass);
            resultMap.put("content", content);

            Integer pageNumber = page.getNumber();
            resultMap.put("pageNumber", pageNumber);

            Integer pageSize = page.getSize();
            resultMap.put("pageSize", pageSize);

            Integer totalPages = page.getTotalPages();
            resultMap.put("totalPages", totalPages);

            Boolean isFirstPage = page.isFirst();
            resultMap.put("firstPage", isFirstPage);

            Boolean isLastPage = page.isLast();
            resultMap.put("lastPage", isLastPage);

            Sort contentSort = page.getSort();
            resultMap.put("sort", contentSort.toString());

            return resultMap;
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
