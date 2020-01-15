package com.sondahum.breaker;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ObjectFlatter {

    Map<String, Object> toMap() {
        return objToMap(this);
    }

    static Map<String, Object> objToMap(Object obj) {
        Map<String, Object> fieldMap =
                Arrays.stream(obj.getClass().getDeclaredFields())
                        .filter(field -> !field.isSynthetic()) // synthetic field란 어떤걸까..?
                        .collect(Collectors.toMap(Field::getName, field -> {
                            Object ret = null;
                            try {
                                ret = field.get(obj);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            return ret;
                        }
                        ));

        return fieldMap;
    }

    static List<String> getFieldNameList(Object obj) {
        List<String> fieldNameList = Arrays.stream(obj.getClass().getDeclaredFields())
                .filter(field -> !field.isSynthetic())
                .map(Field::getName)
                .collect(Collectors.toList());

        return fieldNameList;
    }

    //TODO : need to approve
    static List<String> getFieldNameListWithAllSuperClasses(Object obj) {
        List<String> ret = new LinkedList<>();
        Class model = obj.getClass();

        while (model != null) {
            ret.addAll(
                    Arrays.stream(model.getDeclaredFields())
                            .filter(field -> !field.isSynthetic())
                            .map(Field::getName)
                            .collect(Collectors.toList()));

            model = model.getSuperclass();
        }
        return ret;
    }
}