package com.ucx.training.shop.util;

import javax.persistence.Entity;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import java.util.ArrayList;
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

    public static List<Map<String, Object>> ListToMap(List<Tuple> tuples) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        tuples.forEach(tuple -> {
            Map<String, Object> item = EntityUtil.toMap(tuple);
            resultList.add(item);
        });
        return resultList;
    }
}
