package com.example.gccoffee.Repository;

import com.example.gccoffee.Model.Order;
import com.example.gccoffee.Model.OrderItem;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class OrderJdbcRepository implements OrderRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    //하나라도 오류나면 롤백하게
    @Transactional
    public Order insert(Order order) {
        jdbcTemplate.update("INSERT INTO orders(order_id, email, address, postcode, order_status, created_at, updated_at) " +
                        "VALUES (UUID_TO_BIN(:orderId), :email, :address, :postcode, :orderStatus, :createdAt, :updatedAt)",
                toOrderParamMap(order));
        order.getOrderItems()
                .forEach(item ->
                        jdbcTemplate.update("INSERT INTO order_items(order_id, product_id, category, price, quantity, created_at, updated_at) " +
                                        "VALUES (UUID_TO_BIN(:orderId), UUID_TO_BIN(:productId), :category, :price, :quantity, :createdAt, :updatedAt)",
                                toOrderItemParamMap(order.getOrderId(), order.getCreatedAt(), order.getUpdatesAt(), item)));
        return order;


    }
    private Map<String,Object> toOrderParamMap(Order order){
        var paramap=new HashMap<String,Object>();
        paramap.put("orderId",order.getOrderId().toString().getBytes());
        paramap.put("email",order.getEmail().getAddress());
        paramap.put("address",order.getAddress());
        paramap.put("postcode",order.getPostcode());
        paramap.put("orderStatus",order.getOrderStatus().toString());
        paramap.put("createdAt",order.getCreatedAt());
        paramap.put("updatedAt",order.getUpdatesAt());
        return paramap;

    }

    private Map<String,Object> toOrderItemParamMap(UUID orderId, LocalDateTime createdAt, LocalDateTime updatedAt , OrderItem item){
        var paramap=new HashMap<String,Object>();
        paramap.put("orderId",orderId.toString().getBytes());
        paramap.put("productId",item.productId().toString().getBytes());
        paramap.put("category",item.category().toString());
        paramap.put("price",item.price());
        paramap.put("quantity",item.quantity());
        paramap.put("createdAt",createdAt);
        paramap.put("updatedAt",updatedAt);
        return paramap;

    }
}
