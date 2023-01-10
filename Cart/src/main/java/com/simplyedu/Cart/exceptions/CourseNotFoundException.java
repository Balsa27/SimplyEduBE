package com.simplyedu.Cart.exceptions;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String nothing_to_remove) {
        super(nothing_to_remove);
    }
}
