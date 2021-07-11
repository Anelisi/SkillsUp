package br.com.southsystem.skiils_up.dto;

import br.com.southsystem.skiils_up.enums.PaymentStatus;
import br.com.southsystem.skiils_up.models.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderDTO {
    private Long idOrder;
    private Long idStudent;
    @Min(0)
    private BigDecimal discount;
    private PaymentStatus paymentStatus;
    private LocalDateTime instant;

    public OrderDTO(Order order) {
        this.idOrder = order.getIdOrder();
        this.idStudent = order.getStudent().getId();
        this.discount = order.getDiscount();
        this.paymentStatus = order.getPaymentStatus();
        this.instant = order.getInstant();
    }
}
