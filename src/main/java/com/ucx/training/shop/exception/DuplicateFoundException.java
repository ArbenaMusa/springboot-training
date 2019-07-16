package com.ucx.training.shop.exception;

/**
 * Custom Exception class, to be thrown when a duplicate element is found.
 */
public class DuplicateFoundException extends Exception {

    public DuplicateFoundException(String message) {
        super(message);
    }
}
