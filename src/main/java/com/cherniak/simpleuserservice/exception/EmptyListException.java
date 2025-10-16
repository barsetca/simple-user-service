package com.cherniak.simpleuserservice.exception;

public class EmptyListException extends RuntimeException {
    public EmptyListException(String message) {
        super(message);
    }
}
