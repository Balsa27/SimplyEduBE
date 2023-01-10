package com.simplyedu.Purchases.entities;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CoursePurchase {
    Long courseId;
    String responseMessage;
}
