package com.simplyedu.AuthServer.exceptions;

public class MustBeAutenticatedException extends RuntimeException {
    public MustBeAutenticatedException(String message) {
        super(message);
    }
}
