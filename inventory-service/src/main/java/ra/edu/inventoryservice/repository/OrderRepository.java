package ra.edu.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.edu.inventoryservice.entity.Order;

public interface OrderRepository extends JpaRepository<Order, String> {
}
