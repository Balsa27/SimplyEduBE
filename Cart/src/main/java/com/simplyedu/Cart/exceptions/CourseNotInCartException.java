package com.simplyedu.Cart.exceptions;

public class CourseNotInCartException extends RuntimeException {
    public CourseNotInCartException(String nothing_to_remove) {
        super(nothing_to_remove);
    }
}
