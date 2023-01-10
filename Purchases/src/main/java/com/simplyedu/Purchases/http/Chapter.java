package com.simplyedu.Purchases.http;

import lombok.Data;


@Data
public class Chapter {
    private Long id;
    private String title;
    private String description;
    private int durationInMinutes;
}
