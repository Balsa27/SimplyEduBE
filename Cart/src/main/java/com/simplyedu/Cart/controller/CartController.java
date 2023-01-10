package com.simplyedu.Cart.controller;

import com.simplyedu.Cart.entities.response.*;
import com.simplyedu.Cart.http.entities.response.PurchaseResponse;
import com.simplyedu.Cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController()
@CrossOrigin(origins = "*", maxAge = 3600)
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @RolesAllowed("ROLE_USER")
    @PostMapping("/cart/remove/{courseId}")
    public ResponseEntity<RemoveCartItemResponse> removeCartItem(@PathVariable(value = "courseId") Long courseId){
        return ResponseEntity.ok(cartService.removeCartItem(courseId));
    }
    
    @RolesAllowed("ROLE_USER") 
    @PostMapping("/cart/add/{courseId}")
    public ResponseEntity<AddCartItemResponse> addItemToCart(@PathVariable (value = "courseId") Long courseId){
        return ResponseEntity.ok(cartService.addItemToCart(courseId));
    }

    @RolesAllowed("ROLE_USER")
    @PostMapping("/cart/checkout")
    public ResponseEntity<PurchaseResponse> checkout(){
        
        return ResponseEntity.ok(cartService.purchaseByUserId());
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping("/cart")
    public ResponseEntity<GetCartByUserIdResponse> getCartByUserId() {
        return ResponseEntity.ok(cartService.getCartByUserId());
    }
}
