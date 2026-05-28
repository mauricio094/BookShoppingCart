package com.bookmarket.bookshoppingcart.entity.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(final String message) {
        super(message);
    }
}