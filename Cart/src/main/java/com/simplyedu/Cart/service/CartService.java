package com.simplyedu.Cart.service;

import com.simplyedu.Cart.entities.response.*;
import com.simplyedu.Cart.http.entities.response.PurchaseResponse;

public interface CartService {
    RemoveCartItemResponse removeCartItem(Long courseId);
    AddCartItemResponse addItemToCart(Long courseId);
    PurchaseResponse purchaseByUserId();
    GetCartByUserIdResponse getCartByUserId();
}
