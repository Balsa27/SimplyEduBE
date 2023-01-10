package com.simplyedu.Cart.entities.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AddCartItemResponse extends Response{
    public AddCartItemResponse(String message) {
        super(message);
    }
}
