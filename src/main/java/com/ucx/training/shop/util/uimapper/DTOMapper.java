package com.ucx.training.shop.util.uimapper;

import com.ucx.training.shop.dto.DTOEntity;
import com.ucx.training.shop.entity.BaseEntity;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;


public class DTOMapper {

    public static DTOEntity convertToDto(Object obj, DTOEntity mapper) {
        return new ModelMapper().map(obj, mapper.getClass());
    }

    public static List<DTOEntity> converToDTOList(List<? extends BaseEntity> objectList, DTOEntity mapper) {
        return objectList.stream()
                .map(e -> convertToDto(e, mapper))
                .collect(Collectors.toList());
    }

}
