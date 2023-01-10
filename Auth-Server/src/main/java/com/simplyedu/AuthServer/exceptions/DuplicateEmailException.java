package com.simplyedu.AuthServer.exceptions;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email_already_taken) {
        super(email_already_taken);
    }
}
