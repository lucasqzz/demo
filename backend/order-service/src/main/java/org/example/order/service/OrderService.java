package org.example.order.service;

import org.example.common.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    OrderDTO createOrder(OrderDTO dto);

    OrderDTO getOrderById(Long id);

    List<OrderDTO> getOrdersByUserId(Long userId);
}
