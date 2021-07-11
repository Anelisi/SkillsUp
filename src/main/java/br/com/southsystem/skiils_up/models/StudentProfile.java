package br.com.southsystem.skiils_up.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_student_profile")
public class StudentProfile extends Profile {

    private Integer coursesCompleted;
    @ElementCollection
    @CollectionTable(name = "tb_saved_items", joinColumns = @JoinColumn(name = "student_profile_id"))
    @Column(name = "course_id")
    private List<Long> savedItems;

    @ElementCollection
    @CollectionTable(name = "tb_student_orders", joinColumns = @JoinColumn(name = "student_profile_id"))
    @Column(name = "orders_id")
    private List<Long> ordersList;

    public StudentProfile(Long id, Long idUser, boolean ativa) {
        super(id, idUser, ativa);
        this.coursesCompleted = 0;
    }
}
