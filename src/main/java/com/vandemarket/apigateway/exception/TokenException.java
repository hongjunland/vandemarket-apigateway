package com.vandemarket.apigateway.exception;

public class TokenException extends RuntimeException {
    public TokenException(String msg) {
        super(msg);
    }
}