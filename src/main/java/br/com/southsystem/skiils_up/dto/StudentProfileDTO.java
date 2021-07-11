package br.com.southsystem.skiils_up.dto;

import br.com.southsystem.skiils_up.models.StudentProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class StudentProfileDTO {

    private Long id;
    private Long idUser;
    private boolean ativa;
    private Integer coursesCompleted;
    private List<Long> savedItems;
    private List<Long> ordersList;

    public StudentProfileDTO(StudentProfile studentProfile) {
        this.id = studentProfile.getId();
        this.idUser = studentProfile.getIdUser();
        this.ativa = studentProfile.isEnable();
        this.coursesCompleted = studentProfile.getCoursesCompleted();
    }
}

