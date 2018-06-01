package com.example.service.template.boot.api;

import org.springframework.http.HttpStatus;

public final class ErrorApiResponse {
    private String code;
    private String message;
    private String type;


    public static ErrorApiResponse badRequest(String message) {
        ErrorApiResponse error = new ErrorApiResponse();
        error.code = HttpStatus.BAD_REQUEST.value() + "";
        error.type = HttpStatus.BAD_REQUEST.getReasonPhrase();
        error.message = message;
        return error;
    }

    public static ErrorApiResponse invalidSignRequest(String message) {

        return ErrorApiResponseBuilder.anErrorAPIResponse()
                .withCode("100101")
                .withType("signIllegal")
                .withMessage(message)
                .build();
    }

    public static ErrorApiResponse signTimeoutRequest(String message) {

        return ErrorApiResponseBuilder.anErrorAPIResponse()
                .withCode("100102")
                .withType("signTimeout")
                .withMessage(message)
                .build();
    }

    public static ErrorApiResponse signParamIllegalRequest(String message) {

        return ErrorApiResponseBuilder.anErrorAPIResponse()
                .withCode("100103")
                .withType("signParamIllegal")
                .withMessage(message)
                .build();
    }

    public static ErrorApiResponse invalidTokenRequest(String message) {

        return ErrorApiResponseBuilder.anErrorAPIResponse()
                .withCode("100202")
                .withType("invalidToken")
                .withMessage(message)
                .build();
    }

    public static ErrorApiResponse invalidUserRequest(String message) {

        return ErrorApiResponseBuilder.anErrorAPIResponse()
                .withCode("100201")
                .withType("invalidUser")
                .withMessage(message)
                .build();
    }

    public static ErrorApiResponse serviceBoom(String message) {
        ErrorApiResponse error = new ErrorApiResponse();
        error.code = HttpStatus.INTERNAL_SERVER_ERROR.value() + "";
        error.type = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        error.message = message;
        return error;
    }

    public static ErrorApiResponse error() {
        ErrorApiResponse response = new ErrorApiResponse();
        return response;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ErrorAPIResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public static class ErrorApiResponseBuilder {
        private String code;
        private String message;
        private String type;

        private ErrorApiResponseBuilder() {
        }

        public static ErrorApiResponseBuilder anErrorAPIResponse() {
            return new ErrorApiResponseBuilder();
        }

        public ErrorApiResponseBuilder withCode(String code) {
            this.code = code;
            return this;
        }

        public ErrorApiResponseBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ErrorApiResponseBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public ErrorApiResponseBuilder but() {
            return anErrorAPIResponse().withCode(code).withMessage(message).withType(type);
        }

        public ErrorApiResponse build() {
            ErrorApiResponse errorAPIResponse = new ErrorApiResponse();
            errorAPIResponse.setCode(code);
            errorAPIResponse.setMessage(message);
            errorAPIResponse.setType(type);
            return errorAPIResponse;
        }
    }
}
