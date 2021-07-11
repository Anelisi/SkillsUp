package br.com.southsystem.skiils_up.services;

import br.com.southsystem.skiils_up.dto.OrderDTO;
import br.com.southsystem.skiils_up.enums.PaymentStatus;
import br.com.southsystem.skiils_up.models.Order;
import br.com.southsystem.skiils_up.repositories.OrderRepository;
import br.com.southsystem.skiils_up.services.exceptions.DataIntegrityException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.southsystem.skiils_up.enums.ErrorMessages.IMPOSSIBLE_DELETE;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository repo;
    private final StudentProfileService studentProfileService;

    public Order create(Order order) {
        order.setIdOrder(null);
        return this.repo.save(order);
    }

    public Order findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<Order> findByPaymentStatus(PaymentStatus paymentStatus) {
        try {
            return repo.findByPaymentStatus(paymentStatus);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status de pagamento inexistente " + e.getMessage());
        }
    }

    public void addCourseToOrder(Long id, Long idCourse) {
        Order order = findById(id);
        try {
            order.getCourseList().add(idCourse);
            repo.save(order);
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Este perfil de estudante não existe.");
        }
    }

    public Order update(Order order) {
        this.findById(order.getIdOrder());
        return this.repo.save(order);
    }

    public void delete(Long id) {
        findById(id);

        try {
            repo.deleteById(id);
        }catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(IMPOSSIBLE_DELETE.getDesc());
        }
    }

    public Page<Order> findPage(Integer page, Integer linesPerPage) {
        PageRequest pageRequest = PageRequest.of(page,linesPerPage);
        return repo.findAll(pageRequest);
    }
    public Order fromDTO(OrderDTO orderDTO) {
        return new Order(orderDTO.getIdOrder(), studentProfileService.findById(orderDTO.getIdStudent()),
                orderDTO.getDiscount(),
                orderDTO.getPaymentStatus());
    }
}
