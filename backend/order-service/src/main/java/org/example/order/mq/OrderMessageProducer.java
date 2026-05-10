package org.example.order.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.dto.OrderDTO;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMessageProducer {

    private final StreamBridge streamBridge;

    public void sendOrderCreatedEvent(OrderDTO orderDTO) {
        Message<OrderDTO> message = MessageBuilder.withPayload(orderDTO).build();
        boolean sent = streamBridge.send("orderCreated-out-0", message);
        log.info("Order created event sent, orderId={}, success={}", orderDTO.getId(), sent);
    }
}
