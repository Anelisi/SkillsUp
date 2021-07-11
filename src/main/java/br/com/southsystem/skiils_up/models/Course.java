package br.com.southsystem.skiils_up.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "tb_course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;

    @JsonIgnore
    @OneToMany(mappedBy = "ratingsCourse")
    private Set<Rating> ratings = new LinkedHashSet<>();

    public Course(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        final var course = (Course) o;
        return getId().equals(course.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

