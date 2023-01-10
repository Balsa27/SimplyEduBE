package com.simplyedu.Courses.entities.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryNameResponse  {
    private List<String> categoryNames;
   
}
