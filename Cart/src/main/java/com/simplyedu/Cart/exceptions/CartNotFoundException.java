package com.simplyedu.Cart.exceptions;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String cart_not_found) {
        super(cart_not_found);
    }
}
