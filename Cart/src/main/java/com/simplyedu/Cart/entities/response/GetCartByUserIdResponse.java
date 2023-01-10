package com.simplyedu.Cart.entities.response;

import com.simplyedu.Cart.http.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCartByUserIdResponse {
    private Long id; //todo remove me especially
    private Long userId; //todo remove me
    private List<Course> courses; 
}
