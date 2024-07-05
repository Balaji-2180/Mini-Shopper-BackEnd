package minishopper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import minishopper.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
	OrderItem findById(int orderItemId);
}
