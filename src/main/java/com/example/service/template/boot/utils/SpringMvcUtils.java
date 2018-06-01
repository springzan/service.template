package com.example.service.template.boot.utils;

import com.google.common.base.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class SpringMvcUtils {
    public static String mapGetAsString(Map<String, Object> body, String key) {

        return Optional.fromNullable(body.get(key)).or("").toString();
    }

    public static boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        if (parameter == null) {
            return false;
        }
        return !"false".equals(parameter.toLowerCase());
    }

    public static String header(HttpHeaders request, String key) {
        List<String> strings = request.get(key);
        if (strings == null || strings.isEmpty()) {
            return null;
        }
        return strings.get(0);
    }

    public static String param(ServletServerHttpRequest request, String key) {
        HttpServletRequest servletRequest = request.getServletRequest();
        if (servletRequest == null) {
            return null;
        }
        return servletRequest.getParameter(key);
    }

}
