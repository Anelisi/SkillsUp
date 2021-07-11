package br.com.southsystem.skiils_up.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "tb_author_profile")
public class AuthorProfile extends Profile{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String presentation;

    @JsonIgnore
    @ElementCollection
    @CollectionTable(name = "tb_author_courses", joinColumns = @JoinColumn(name = "author_profile_id"))
    @Column(name = "courseslist")
    private List<Long> authorCoursesList;

    public AuthorProfile(Long id, Long idUser, boolean ativa, String presentation) {
        super(id, idUser, ativa);
        this.presentation = presentation;
    }
}
