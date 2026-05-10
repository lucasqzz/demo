package org.example.order.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.dto.*;
import org.example.common.exception.BusinessException;
import org.example.common.response.Result;
import org.example.common.response.ResultCode;
import org.example.order.entity.Order;
import org.example.order.feign.ProductFeignClient;
import org.example.order.feign.UserFeignClient;
import org.example.order.mapper.OrderMapper;
import org.example.order.mq.OrderMessageProducer;
import org.example.order.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final UserFeignClient userFeignClient;
    private final ProductFeignClient productFeignClient;
    private final OrderMessageProducer orderMessageProducer;
    private final ObjectMapper objectMapper;

    @Override
    public OrderDTO createOrder(OrderDTO dto) {
        Result<UserDTO> userResult = userFeignClient.getUserById(dto.getUserId());
        if (userResult.getCode() != ResultCode.SUCCESS.getCode()) {
            throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "User not found or service unavailable");
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItemDTO> enrichedItems = new ArrayList<>();

        for (OrderItemDTO item : dto.getItems()) {
            Result<ProductDTO> productResult = productFeignClient.getProductById(item.getProductId());
            if (productResult.getCode() != ResultCode.SUCCESS.getCode()) {
                throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "Product not found: " + item.getProductId());
            }
            ProductDTO product = productResult.getData();
            item.setProductName(product.getName());
            item.setUnitPrice(product.getPrice());
            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            enrichedItems.add(item);
        }

        Order order = new Order();
        order.setUserId(dto.getUserId());
        order.setTotalAmount(totalAmount);
        order.setStatus("CREATED");
        try {
            order.setItemsJson(objectMapper.writeValueAsString(enrichedItems));
        } catch (JsonProcessingException e) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR);
        }
        orderMapper.insert(order);

        OrderDTO result = toDTO(order, enrichedItems);
        orderMessageProducer.sendOrderCreatedEvent(result);
        return result;
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderMapper.findById(id);
        if (order == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        return toDTO(order, parseItems(order.getItemsJson()));
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        return orderMapper.findByUserId(userId).stream()
                .map(o -> toDTO(o, parseItems(o.getItemsJson())))
                .toList();
    }

    private OrderDTO toDTO(Order order, List<OrderItemDTO> items) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setItems(items);
        dto.setCreatedAt(order.getCreatedAt());
        return dto;
    }

    private List<OrderItemDTO> parseItems(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            return List.of();
        }
    }
}
