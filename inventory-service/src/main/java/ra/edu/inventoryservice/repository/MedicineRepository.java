package ra.edu.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.edu.inventoryservice.entity.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
}
