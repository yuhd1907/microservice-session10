package ra.edu.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ra.edu.notificationservice.entity.Medicine;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.notification.customer-email}")
    private String customerEmail;

    public void sendOrderConfirmation(Medicine medicine, Integer quantity) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(customerEmail);
        message.setSubject("Xác nhận đơn hàng");
        message.setText("""
                Cảm ơn bạn đã đặt hàng tại Pharma Medicine:
                Tên thuốc : %s
                Số lượng : %d
                Tổng tiền : %s
                """.formatted(medicine.getMedicineName(), quantity,
                medicine.getPrice().multiply(java.math.BigDecimal.valueOf(quantity))));

        mailSender.send(message);
    }
}
