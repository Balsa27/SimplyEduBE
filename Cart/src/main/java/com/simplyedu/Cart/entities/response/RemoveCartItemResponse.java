package com.simplyedu.Cart.entities.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RemoveCartItemResponse extends Response {
    public RemoveCartItemResponse(String message) {
        super(message);
    }
}
