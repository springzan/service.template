package com.example.service.template.boot.api;

import com.google.common.collect.ImmutableMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpOutputMessage;

import java.io.IOException;

/**
 * 自定义MessageConverter;
 * 正常返回值里统一加一层data : {"data": response}
 * 异常返回值里统一加一层error :  {"error": response}
 * <p> Created by spring on 2017.
 */
public class CustomHttpMessageConverter extends FastJsonHttpMessageConverter{
    private static final Logger logger = LoggerFactory.getLogger(CustomHttpMessageConverter.class);

    private ImmutableMap<Object,Object> wrap(String key,Object data){
        return ImmutableMap.builder().put(key,data).build();
    }

    private static ImmutableMap<Object,Object> paging(PagingApiResponse response){
        ImmutableMap<String,Object> paging=ImmutableMap.<String,Object>builder()
                .put("page",response.getPage())
                .put("pageSize",response.getPageSize())
                .put("total",response.getTotal())
                .put("hasMore",response.isHasMore())
                .build();
        return ImmutableMap.builder()
                .put("data",response.getData())
                .put("paging",paging)
                .build();
    }

    @Override
    protected void writeInternal(Object data, HttpOutputMessage outputMessage) throws IOException {
        if(data instanceof ErrorApiResponse){
            super.writeInternal(wrap("error",data),outputMessage);
        }else if(data instanceof PagingApiResponse){
            super.writeInternal(paging((PagingApiResponse)data),outputMessage);
        }else if(data instanceof String){
            super.writeInternal(data,outputMessage);
        }else {
            super.writeInternal(wrap("data",data),outputMessage);
        }
        logger.debug("response: {} {}", JSON.toJSONString(data), outputMessage.getBody().toString());
    }
}
