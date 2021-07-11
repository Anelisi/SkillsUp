package br.com.southsystem.skiils_up.controllers;

import br.com.southsystem.skiils_up.dto.OrderDTO;
import br.com.southsystem.skiils_up.enums.PaymentStatus;
import br.com.southsystem.skiils_up.models.Order;
import br.com.southsystem.skiils_up.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private final OrderService service;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<OrderDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage){
        Page<Order> list = service.findPage(page,linesPerPage);
        Page<OrderDTO> dtoList = list.map(OrderDTO::new);
        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        Order obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/{paymentStatus}")
    private ResponseEntity<List<Order>> findByPaymentStatus(@PathVariable PaymentStatus paymentStatus) {
        List<Order> orderList = service.findByPaymentStatus(paymentStatus);
        return ResponseEntity.ok().body(orderList);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody OrderDTO orderDTO) {
        Order order = service.fromDTO(orderDTO);
        order = service.create(order);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(order.getIdOrder()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "order-course/{id}/{idCourse}")
    public ResponseEntity<Void> savedCourse(@PathVariable Long idCourse,
                                            @PathVariable Long id) {
        service.addCourseToOrder(id,idCourse);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody Order order, @PathVariable Long id) {
        order.setIdOrder(id);
        service.update(order);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
