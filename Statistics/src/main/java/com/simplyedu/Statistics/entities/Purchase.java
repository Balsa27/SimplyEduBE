package com.simplyedu.Statistics.entities;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Purchase {
    Long id;
    Long userId;
    Long courseId;
    LocalDate purchaseDate;
}
