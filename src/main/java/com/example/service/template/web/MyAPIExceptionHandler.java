package com.example.service.template.web;

import com.example.service.template.boot.api.APIExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.example.service.template.web.api")
public class MyAPIExceptionHandler extends APIExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(MyAPIExceptionHandler.class);

    // TODO: 16/10/19 define your own business exception
    // such as:
    // 1. 第三方投单接口异常
    // 2.
}
