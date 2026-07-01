package ra.edu.pharmacyservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ra.edu.pharmacyservice.event.OrderEvent;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Value("${app.kafka.topic.medicine-stock-events}")
    private String medicineStockEventsTopic;

    public OrderEvent sendOrderEvent(Long medicineId, Integer quantity) {
        OrderEvent orderEvent = OrderEvent.builder()
                .orderId(UUID.randomUUID().toString())
                .medicineId(medicineId)
                .quantity(quantity)
                .timestamp(LocalDateTime.now())
                .build();

        // Dùng medicineId làm Message Key để đảm bảo các đơn hàng của cùng
        // một loại thuốc luôn đi vào cùng một Partition
        kafkaTemplate.send(medicineStockEventsTopic, String.valueOf(medicineId), orderEvent);
        log.info("Đã gửi sự kiện đơn hàng: {} với Key: {}", orderEvent.getOrderId(), medicineId);

        return orderEvent;
    }
}
