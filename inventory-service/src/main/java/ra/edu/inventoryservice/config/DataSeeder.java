package ra.edu.inventoryservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ra.edu.inventoryservice.entity.Medicine;
import ra.edu.inventoryservice.repository.MedicineRepository;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final MedicineRepository medicineRepository;

    @Override
    public void run(String... args) {
        if (medicineRepository.count() == 0) {
            medicineRepository.save(new Medicine(null, "Panadol", "Thuốc giảm đau, hạ sốt", BigDecimal.valueOf(15000), 100));
            medicineRepository.save(new Medicine(null, "Amoxicillin", "Kháng sinh", BigDecimal.valueOf(25000), 200));
            medicineRepository.save(new Medicine(null, "Vitamin C", "Tăng sức đề kháng", BigDecimal.valueOf(10000), 300));
        }
    }
}
