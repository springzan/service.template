package com.example.service.template.web.api;

import com.google.common.collect.ImmutableMap;

import com.example.service.template.boot.api.PagingApiResponse;
import com.example.service.template.boot.api.PagingParam;
import com.example.service.template.boot.api.sign.NoNeedSign;
import com.example.service.template.boot.api.user.UserIdentity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
@RequestMapping("/demo")
public class DemoController {

    @NoNeedSign
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/data")
    public Object getData(){
        return ImmutableMap.builder()
                .put("key1","value1")
                .build();
    }

    @GetMapping("/paging")
    public PagingApiResponse getPagingApiResponse(UserIdentity user,PagingParam pageParam){
        return pageParam.aPagingApiResponseBuilder()
                .withData(new ArrayList())
                .withHasMore(false)
                .withTotal(0)
                .build();
    }
}
