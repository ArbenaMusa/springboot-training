package com.ucx.training.shop.exception;

/**
 * Custom Exception class, to be thrown when a searched element is not found.
 */
public class NotFoundException extends Exception {

    public NotFoundException(String message) {
        super(message);
    }
}
