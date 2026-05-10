package org.example.product.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.dto.OrderDTO;
import org.example.common.dto.OrderItemDTO;
import org.example.product.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StockReduceConsumer {

    private final ProductService productService;

    @Bean
    public Consumer<Message<OrderDTO>> stockReduce() {
        return message -> {
            OrderDTO order = message.getPayload();
            log.info("Received order created event, orderId={}", order.getId());
            for (OrderItemDTO item : order.getItems()) {
                productService.reduceStock(item.getProductId(), item.getQuantity());
                log.info("Reduced stock for product={}, quantity={}", item.getProductId(), item.getQuantity());
            }
        };
    }
}
