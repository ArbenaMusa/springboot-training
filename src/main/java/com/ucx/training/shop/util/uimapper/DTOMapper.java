package com.ucx.training.shop.util.uimapper;

import com.ucx.training.shop.dto.DTOEntity;
import org.modelmapper.ModelMapper;


public class DTOMapper {

    public DTOEntity convertToDto(Object obj, DTOEntity mapper) {
        return new ModelMapper().map(obj, mapper.getClass());
    }

    public Object convertToEntity(Object obj, DTOEntity mapper) {
        return new ModelMapper().map(mapper, obj.getClass());

    }
}
