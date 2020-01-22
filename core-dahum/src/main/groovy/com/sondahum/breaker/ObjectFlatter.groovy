package com.sondahum.breaker;


class ObjectFlatter {


    static Map<String, Object> objToMap(Object obj) {
        Map<String, Object> resultMap =
                obj.getClass().declaredFields.findAll { !it.synthetic } // TODO synthetic정체 알기
                        .collectEntries { [(it.name): obj."$it.name"] } // ${variable}이거랑 같다.. 그루비 미쳤다.

        return resultMap
    }

    static List<String> getFieldsList(Object obj) {
        List<String> fieldNameList =
                obj.getClass().getDeclaredFields().findAll {!it.synthetic}.collect {it.name}

        return fieldNameList
    }

    //TODO : need to approve
    static List<String> getFieldsListWithAllSuperClasses(Object obj) {
        List<String> fieldNameList = []
        Class model = obj.getClass()

        while (model) {
            fieldNameList.addAll(model.getDeclaredFields().findAll {!it.synthetic}.collect {it.name})
            model = model.getSuperclass()
        }
        return fieldNameList
    }
}