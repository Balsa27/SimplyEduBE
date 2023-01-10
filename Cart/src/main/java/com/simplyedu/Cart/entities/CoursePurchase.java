package com.simplyedu.Cart.entities;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CoursePurchase {
    Long courseId;
    String responseMessage;
}
