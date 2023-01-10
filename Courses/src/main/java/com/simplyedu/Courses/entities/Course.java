package com.simplyedu.Courses.entities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String title;
    private String shortDescription;
    private String longDescription;
    private String imageUrl;
    private double rating;
    private int lengthInMinutes;
    private double price;
    private LocalDate creationDate;
    private LocalDate lastUpdateDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "COURSE_CATEGORIES",
            joinColumns = @JoinColumn(name = "COURSE_ID"),
            inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID"))
    private List<Category> categories;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Chapter> chapters;

    private int numberOfStudents;
    private String language;
}
