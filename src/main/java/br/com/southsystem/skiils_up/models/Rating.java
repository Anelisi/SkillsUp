package br.com.southsystem.skiils_up.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double rate;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "ratings_course")
    private Course ratingsCourse;
}
