package ptudvj2ee.bai5_qlsp_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptudvj2ee.bai5_qlsp_jpa.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
