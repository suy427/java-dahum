package com.sondahum.javadahum.core.breaker;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ObjectFlatter {

    private final Gson gson = new Gson();

    Map<String, Object> toMap() {
        return objToMap(this);
    }

    Map<String, Object> objToMap(Object obj) {
        if (obj == null)
            return null;

        String jsonString = gson.toJson(obj);
        LinkedHashMap<Object, Object> resultMap = new LinkedHashMap<>();


       Set<Entry<String, JsonElement>> set = JsonParser.parseString(jsonString).getAsJsonObject().entrySet();


        return map
    }

    static Object jsonToObject(String jsonString){
        try{
            if (jsonString)
                return new JsonSlurper().parseText(jsonString)
        }catch(Exception e){
            e.printStackTrace()
            return jsonString
        }
        return null
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

//    static List<String> getFieldNameList(Object obj) {
//        List<String> fieldNameList = Arrays.stream(obj.getClass().getDeclaredFields())
//                .filter(field -> !field.isSynthetic())
//                .map(Field::getName)
//                .collect(Collectors.toList());
//
//        return fieldNameList;
//    }
//
//    //TODO : need to approve
//    static List<String> getFieldNameListWithAllSuperClasses(Object obj) {
//        List<String> ret = new LinkedList<>();
//        Class model = obj.getClass();
//
//        while (model != null) {
//            ret.addAll(
//                    Arrays.stream(model.getDeclaredFields())
//                            .filter(field -> !field.isSynthetic())
//                            .map(Field::getName)
//                            .collect(Collectors.toList()));
//
//            model = model.getSuperclass();
//        }
//        return ret;
//    }
}