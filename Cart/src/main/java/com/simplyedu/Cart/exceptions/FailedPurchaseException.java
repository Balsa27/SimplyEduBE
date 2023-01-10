package com.simplyedu.Cart.exceptions;

public class FailedPurchaseException extends RuntimeException {
    public FailedPurchaseException(String purchase_failed) {
        super(purchase_failed);
    }
}
