package com.example.gccoffee.controller;

import com.example.gccoffee.Model.Email;
import com.example.gccoffee.Model.Order;
import com.example.gccoffee.Model.OrederDto;
import com.example.gccoffee.Service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {
    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("api/v1/orders")
    public Order createOrder(@RequestBody OrederDto orederDto){

        //서비스는 자체 메소드를 가지고 컨트롤러는 dto를 받아 서비스가 필요한 매개변수로 변환하는 역할을 해아한다
        //dto에서 이메일을 string으로 받고 컨트롤러에서 email로 형변환을 해준다.
        return orderService.createOrder(new Email(orederDto.email()),orederDto.address(),orederDto.postcode(),orederDto.orderItems());

    }

}
