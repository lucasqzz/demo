package org.example.order.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {

    private Long id;
    private Long userId;
    private BigDecimal totalAmount;
    private String status;
    private String itemsJson;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
