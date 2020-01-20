package com.sondahum.javadahum.test.spring

class SpringTestHelper {

    Object requestGet(String url, ResultHandler resultHandlerForDocument, RequestValuesHandler valuesHandler){
        printStart("[GET] ${url}")
        //- Request
        ResultActions resultActions = mvc
                .perform(
                        RestDocumentationRequestBuilders
                                .get(url, valuesHandler.getPathArray())
                                .params(valuesHandler.getParams())
                                .headers(new HttpHeaders(valuesHandler.getHeaders()))
                                .contentType(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                                .accept(MediaType.APPLICATION_OCTET_STREAM)
//                                .accept(MediaType.APPLICATION_JSON)
//                .with(authentication(testAuthentication))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM));
        //- Document or Something
        if (resultHandlerForDocument){
            resultActions.andDo(resultHandlerForDocument)
        }
        //- Response
        MvcResult mvcResult = resultActions.andReturn()
        if (valuesHandler.getClosureForResponse())
            valuesHandler.getClosureForResponse()(mvcResult.getResponse())
        String jsonString = mvcResult.getResponse().getContentAsString()
        def resultObject = toObject(jsonString)
        printEnd(resultObject)

        return resultObject
    }
}
