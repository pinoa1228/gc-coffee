package com.example.gccoffee.Service;

import com.example.gccoffee.Model.Email;
import com.example.gccoffee.Model.Order;
import com.example.gccoffee.Model.OrderItem;
import com.example.gccoffee.Model.OrderStatus;
import com.example.gccoffee.Repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultOrderService implements OrderService {
    private final OrderRepository orderRepository;

    public DefaultOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems) {
        Order order=new Order(UUID.randomUUID(),email,address,postcode,orderItems, OrderStatus.ACCEPTED, LocalDateTime.now(),LocalDateTime.now());
        return orderRepository.insert(order);
    }
}
