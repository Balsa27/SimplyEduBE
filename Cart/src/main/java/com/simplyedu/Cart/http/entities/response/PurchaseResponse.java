package com.simplyedu.Cart.http.entities.response;

import com.simplyedu.Cart.entities.CoursePurchase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseResponse {
    Set<CoursePurchase> coursePurchaseResponse;
}
