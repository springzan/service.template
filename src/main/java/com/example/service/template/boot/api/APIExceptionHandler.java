package com.example.service.template.boot.api;

import com.example.service.template.boot.api.sign.SignException;
import com.example.service.template.boot.api.user.UserException;
import com.example.service.template.boot.utils.SpringMvcUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

/**
 * API的基础异常处理类, 包含:
 * <p>
 *
 * 1. Exception-> 400
 * 2. RuntimeException-> 503
 * 3. 验证签名失败->403
 * 4. 无效的登陆token->401
 * 5. 请求太频繁->429
 * 6. ...
 *
 * 子类处理业务逻辑
 */

public class APIExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(APIExceptionHandler.class);

    public static String stackTrace(Exception ex){
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }

    @ExceptionHandler(value = SignException.SignTimeoutException.class)
    @ResponseBody
    public ResponseEntity<ErrorApiResponse> signTimeoutExceptionHandler(HttpServletRequest request,
                                                                        Exception e){
        logger.error("signTimeoutExceptionHandler: {} ", request, e);
        StringBuilder message = new StringBuilder(request.getRequestURI())
                .append(e.getMessage());
        if (SpringMvcUtils.getTraceParameter(request)) {
            message.append("\n\r");
            message.append(stackTrace(e));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ErrorApiResponse.signTimeoutRequest(message.toString()));
    }

    @ExceptionHandler(value = SignException.SignParamIllegalException.class)
    @ResponseBody
    public ResponseEntity<ErrorApiResponse> signParamIllegalExceptionHandler(HttpServletRequest request,
                                                                             Exception e) {
        logger.error("signParamIllegalExceptionHandler: {} ", request, e);
        StringBuilder message = new StringBuilder(request.getRequestURI())
                .append(e.getMessage());
        if (SpringMvcUtils.getTraceParameter(request)) {
            message.append("\n\r");
            message.append(stackTrace(e));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ErrorApiResponse.signParamIllegalRequest(message.toString()));
    }

    @ExceptionHandler(value = SignException.class)
    @ResponseBody
    public ResponseEntity<ErrorApiResponse> signExceptionHandler(HttpServletRequest request,
                                                                 Exception e) {
        logger.error("signExceptionHandler: {} ", request, e);
        StringBuilder message = new StringBuilder(request.getRequestURI())
                .append(e.getMessage());
        if (SpringMvcUtils.getTraceParameter(request)) {
            message.append("\n\r");
            e.printStackTrace(new PrintWriter(new StringWriter()));
            message.append(stackTrace(e));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ErrorApiResponse.invalidSignRequest(message.toString()));
    }

    @ExceptionHandler(value = UserException.TokenInvalidException.class)
    @ResponseBody
    public ResponseEntity<ErrorApiResponse> userExceptionHandler(HttpServletRequest request,
                                                                 Exception e) {
        logger.error("userExceptionHandler: {} ", request, e);
        StringBuilder message = new StringBuilder(request.getRequestURI())
                .append(e.getMessage());
        if (SpringMvcUtils.getTraceParameter(request)) {
            message.append("\n\r");
            message.append(stackTrace(e));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ErrorApiResponse.invalidTokenRequest(message.toString()));
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorApiResponse> defaultExceptionHandler(HttpServletRequest request,
                                                                    Exception e) {
        logger.error("defaultExceptionHandler: {} ", request, e);
        StringBuilder message = new StringBuilder(request.getRequestURI())
                .append(e.getMessage());
        if (SpringMvcUtils.getTraceParameter(request)) {
            message.append("\n\r");
            message.append(stackTrace(e));
        }
        return ResponseEntity.badRequest()
                .body(ErrorApiResponse.badRequest(message.toString()));
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<ErrorApiResponse> runtimeExceptionHandler(
            HttpServletRequest request,
            RuntimeException e) {
        logger.error("runtimeExceptionHandler: {}", request, e);

        StringBuilder message = new StringBuilder(request.getRequestURI())
                .append(e.getMessage());
        appendExceptionTrace(request, e, message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorApiResponse.serviceBoom(message.toString()));
    }



    private void appendExceptionTrace(HttpServletRequest request, RuntimeException e, StringBuilder message) {
        if (SpringMvcUtils.getTraceParameter(request)) {
            message.append("\n\r");
            message.append(stackTrace(e));
        }
    }
}
