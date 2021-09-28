package com.example.gccoffee.Service;

import com.example.gccoffee.Model.Email;
import com.example.gccoffee.Model.Order;
import com.example.gccoffee.Model.OrderItem;

import java.util.List;

public interface OrderService {
    Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);

}
