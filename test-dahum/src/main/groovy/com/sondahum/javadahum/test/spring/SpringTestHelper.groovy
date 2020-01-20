package com.sondahum.javadahum.test.spring

import com.sondahum.javadahum.test.AbstractTestHelper
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultHandler
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.StatusResultMatchers
import org.springframework.util.MultiValueMap

//MockMvc
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
//RestDocs
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*



@AutoConfigureRestDocs(uriScheme="http", uriHost="0.0.0.0", uriPort=0)
class SpringTestHelper extends AbstractTestHelper{

    @Autowired
    MockMvc mvc

    class RequestValues {}
    class PathValues extends RequestValues { List<Object> values }
    class ParameterValues extends RequestValues { Object values }
    class MultipartValues extends RequestValues { List<Object> values }
    class BodyValues  extends RequestValues { Object values }
    class HeaderValues extends RequestValues { Object values }
    class ResponseHandler extends RequestValues { Closure closureForResponse }

    static void sizeChecker(def object) {
        switch (object) {
            case { it instanceof Map }:
                println "SIZE: ${object.size()}"
                break
            case { it instanceof List }:
                println "SIZE: ${object.size()}"
                break
            default:
                println "VALUE: ${object}"
                break
        }
    }

    static void printStart(String title){
        println "\n================================================== ${title}"
    }

    static void printEnd(def resultObject){
        if (resultObject)
            sizeChecker(resultObject)
        println "==================================================\n"
    }

    class RequestValuesHandler{
        RequestValuesHandler(){
        }
        RequestValuesHandler(RequestValues... values){
            for (int i=0; i<values.length; i++){
                RequestValues valueObject = values[i]
                if (valueObject instanceof PathValues)
                    this.pathValues = valueObject
                else if (valueObject instanceof ParameterValues)
                    this.paramValues = valueObject
                else if (valueObject instanceof MultipartValues)
                    this.multipartValues = valueObject
                else if (valueObject instanceof BodyValues)
                    this.bodyValues = valueObject
                else if (valueObject instanceof HeaderValues)
                    this.headerValues = valueObject
                else if (valueObject instanceof ResponseHandler)
                    this.responseHandler = valueObject
            }
        }
        PathValues pathValues
        ParameterValues paramValues
        MultipartValues multipartValues
        BodyValues bodyValues
        HeaderValues headerValues
        ResponseHandler responseHandler
        Object[] getPathArray(){ return (this.pathValues?.values ?: []).toArray() }

        MultiValueMap<String, String> getParams(){ return generateParams(this.paramValues?.values ?: [:]) }
        String getBodyString(){ return toJsonString(this.bodyValues?.values ?: [:]) }
        List<MockMultipartFile> getMultipartFileList(){ return this.multipartValues?.values ?: [] }
        MultiValueMap<String, String> getHeaders(){ return generateParams(this.headerValues?.values ?: [:]) }
        Closure getClosureForResponse(){ return responseHandler?.closureForResponse }
    }

    static Object toObject(String jsonString){
        try{
            if (jsonString)
                return new JsonSlurper().parseText(jsonString)
        }catch(e){
            return jsonString
        }
        return null
    }

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
               )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())

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
