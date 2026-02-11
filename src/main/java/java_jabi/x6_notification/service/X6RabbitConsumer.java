package java_jabi.x6_notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java_jabi.x6_notification.model.Order;
import java_jabi.x6_notification.model.OrderProducts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.text.DecimalFormat;

@Slf4j
@Component
public class X6RabbitConsumer {
    final ObjectMapper objectMapper = new ObjectMapper();


    @RabbitListener(queues = "x6_queue")
    public void receive(String message) throws JsonProcessingException {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMinimumFractionDigits(2);

        Order order = objectMapper.readValue(message, Order.class);
        log.info("=================================");
        log.info(" Получен новый заказ ");
        log.info(" Order ID: {}", order.getOrderId());
        log.info(" User ID: {}", order.getUserId());
        log.info(" Description : {}", order.getDescription());
        log.info(" OrderDate : {}", order.getOrderDate());
        for (OrderProducts item : order.getItems()) {
            log.info(" Товар  ");
            log.info(" Product ID: {}", item.getProductId());
            log.info(" Quantity: {}", item.getQuantity());
            log.info(" Comments : {}", item.getComments());
            log.info(" UpdateDate : {}", item.getUpdateDate());
        }
    }
}
