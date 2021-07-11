package br.com.southsystem.skiils_up.repositories;


import br.com.southsystem.skiils_up.enums.PaymentStatus;
import br.com.southsystem.skiils_up.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByPaymentStatus(PaymentStatus paymentStatus);
}
