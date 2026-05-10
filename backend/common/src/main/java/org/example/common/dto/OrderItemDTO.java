package org.example.common.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {

    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
}
