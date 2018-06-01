package com.example.service.template.boot.api.sign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInterceptor extends HandlerInterceptorAdapter{
    private static final Logger logger = LoggerFactory.getLogger(SignInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod) handler;
        if(null!=handlerMethod.getBeanType().getAnnotation(NoNeedSign.class)){
            return true;
        }
        if(null!=handlerMethod.getMethodAnnotation(NoNeedSign.class)){
            return true;
        }
        AuthorizationInfo authInfo=SignUtils.parseAuthInfo(request);
        if(null==authInfo){
            throw new SignException.SignParamIllegalException("签名信息缺少必要参数");
        }
        if(SignUtils.checkTimeout(authInfo)){
            throw new SignException.SignTimeoutException("签名超时，签名时间跟当前时间间隔太长，需要重签");
        }
        if(!SignUtils.checkSign(authInfo)){
            throw new SignException("签名不正确");
        }
        return super.preHandle(request, response, handler);
    }
}
