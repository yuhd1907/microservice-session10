package ra.edu.pharmacyservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ra.edu.pharmacyservice.service.OrderEventProducer;

@RestController
@RequestMapping("/api/pharmacy")
@RequiredArgsConstructor
public class PharmacyController {

    private final OrderEventProducer orderEventProducer;

    @PostMapping("/sell")
    public String sell(@RequestParam Long medicineId, @RequestParam Integer quantity) {
        orderEventProducer.sendOrderEvent(medicineId, quantity);
        return "Thanh toán thành công! Sự kiện đã được gửi tới Kafka.";
    }
}
