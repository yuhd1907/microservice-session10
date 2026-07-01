package ra.edu.inventoryservice.listener;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ra.edu.inventoryservice.entity.Medicine;
import ra.edu.inventoryservice.entity.Order;
import ra.edu.inventoryservice.event.OrderEvent;
import ra.edu.inventoryservice.repository.MedicineRepository;
import ra.edu.inventoryservice.repository.OrderRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final MedicineRepository medicineRepository;
    private final OrderRepository orderRepository;

    @KafkaListener(topics = "${app.kafka.topic.medicine-stock-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void onOrderEvent(OrderEvent orderEvent) {
        Medicine medicine = medicineRepository.findById(orderEvent.getMedicineId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thuốc id=" + orderEvent.getMedicineId()));

        Order order = new Order();
        order.setId(orderEvent.getOrderId());
        order.setMedicineId(medicine.getId());
        order.setPriceSell(medicine.getPrice());
        order.setQuantity(orderEvent.getQuantity());
        order.setTimestamp(orderEvent.getTimestamp());
        order.setTotalAmount(medicine.getPrice().multiply(java.math.BigDecimal.valueOf(orderEvent.getQuantity())));
        orderRepository.save(order);

        medicine.setQuantity(medicine.getQuantity() - orderEvent.getQuantity());
        medicineRepository.save(medicine);

        log.info("Thêm mới đơn hàng thành công !");
    }
}
