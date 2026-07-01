package ra.edu.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.edu.notificationservice.entity.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
}
