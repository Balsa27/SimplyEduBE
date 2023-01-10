package com.simplyedu.Purchases.entities.response;

import com.simplyedu.Purchases.entities.Purchase;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseDetailedResponse {
   List<Purchase> allPurchases;
}
