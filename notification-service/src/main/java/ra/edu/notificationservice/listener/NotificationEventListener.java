package ra.edu.notificationservice.listener;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import ra.edu.notificationservice.entity.Medicine;
import ra.edu.notificationservice.event.OrderEvent;
import ra.edu.notificationservice.repository.MedicineRepository;
import ra.edu.notificationservice.service.EmailService;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final MedicineRepository medicineRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "${app.kafka.topic.medicine-stock-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void onOrderEvent(OrderEvent orderEvent, @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        log.info("Đã gửi đơn hàng: {} với Key: {}", orderEvent.getOrderId(), key);

        Medicine medicine = medicineRepository.findById(orderEvent.getMedicineId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thuốc id=" + orderEvent.getMedicineId()));

        emailService.sendOrderConfirmation(medicine, orderEvent.getQuantity());

        log.info("Đã gửi thông báo tới email khách hàng");
    }
}
