package com.simplyedu.UserCourses.entities.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class EnrollUserInCourseResponse extends Response {
    public EnrollUserInCourseResponse(String message) {
        super(message);
    }
}
