package br.com.southsystem.skiils_up.dto;

import br.com.southsystem.skiils_up.models.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CourseDTO {

    private Long id;
    private String name;
    private double price;

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.price = course.getPrice();
    }
}
