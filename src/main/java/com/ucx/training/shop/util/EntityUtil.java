package com.ucx.training.shop.util;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityUtil {

    public static Map<String, Object> toMap(Tuple tuple) {
        Map<String, Object> item = new HashMap<>();
        List<TupleElement<?>> columns = tuple.getElements();
        for (TupleElement column : columns) item.put(column.getAlias(), tuple.get(column.getAlias()));
        return item;
    }
}
