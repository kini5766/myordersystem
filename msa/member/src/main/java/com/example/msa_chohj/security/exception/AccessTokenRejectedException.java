package com.example.msa_chohj.security.exception;

public class AccessTokenRejectedException extends RuntimeException {

    public AccessTokenRejectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessTokenRejectedException(String message) {
        super(message);
    }
}
