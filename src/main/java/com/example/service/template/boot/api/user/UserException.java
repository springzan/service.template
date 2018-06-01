package com.example.service.template.boot.api.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(UserException.class);

    public UserException() {
        this("");
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(Exception e) {
        super(e);
    }

    public static class TokenInvalidException extends UserException {
        public TokenInvalidException(String message) {
            super(message);
        }
    }
}
