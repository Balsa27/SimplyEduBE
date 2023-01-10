package com.simplyedu.Cart.http;

import lombok.Data;

@Data
//@Entity
public class Chapter {
    //@id
    private Long id;
    private String title;
    private String description;
    private int durationInMinutes;
}

