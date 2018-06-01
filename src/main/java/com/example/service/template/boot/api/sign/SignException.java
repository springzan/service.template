package com.example.service.template.boot.api.sign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(SignException.class);

    public SignException(String message) {
        super(message);
    }

    public SignException(Exception e) {
        super(e);
    }

    public static class SignParamIllegalException extends SignException {

        public SignParamIllegalException(String message) {
            super(message);
        }
    }

    public static class SignVersionIllegalException extends SignException {

        public SignVersionIllegalException(String message) {
            super(message);
        }
    }

    public static class SignTimeoutException extends SignException {

        public SignTimeoutException(String message) {
            super(message);
        }
    }
}
