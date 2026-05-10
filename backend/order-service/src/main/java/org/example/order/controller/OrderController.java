package org.example.order.controller;

import lombok.RequiredArgsConstructor;
import org.example.common.dto.OrderDTO;
import org.example.common.response.Result;
import org.example.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Result<OrderDTO> createOrder(@RequestBody OrderDTO dto) {
        return Result.success(orderService.createOrder(dto));
    }

    @GetMapping("/{id}")
    public Result<OrderDTO> getOrderById(@PathVariable Long id) {
        return Result.success(orderService.getOrderById(id));
    }

    @GetMapping("/user/{userId}")
    public Result<List<OrderDTO>> getOrdersByUserId(@PathVariable Long userId) {
        return Result.success(orderService.getOrdersByUserId(userId));
    }
}
