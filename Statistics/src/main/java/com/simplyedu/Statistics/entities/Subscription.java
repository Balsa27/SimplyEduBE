package com.simplyedu.Statistics.entities;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subscription {
    private Long id;
    private Long userId;
    private LocalDate startDate;
    private LocalDate expirationDate;
    private boolean isActive;
}
