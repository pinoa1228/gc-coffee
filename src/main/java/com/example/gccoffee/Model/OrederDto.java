package com.example.gccoffee.Model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrederDto (
        String email,
        String address,
        String postcode,
        List<OrderItem> orderItems

){

}
