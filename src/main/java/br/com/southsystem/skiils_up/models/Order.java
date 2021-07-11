package br.com.southsystem.skiils_up.models;

import br.com.southsystem.skiils_up.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.EnumType.STRING;

@NoArgsConstructor
@Setter
@Getter
@Entity(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrder;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentProfile student;

    private BigDecimal discount;

    @Enumerated(STRING)
    private PaymentStatus paymentStatus;
    private LocalDateTime instant;

    @ElementCollection
    @CollectionTable(name = "tb_order_course_list", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "course_id")
    private List<Long> courseList;

    public Order(Long idOrder, StudentProfile student, BigDecimal discount, PaymentStatus paymentStatus) {
        this.idOrder = idOrder;
        this.student = student;
        this.discount = discount;
        this.paymentStatus = paymentStatus;
        this.instant = LocalDateTime.now();
    }
}
