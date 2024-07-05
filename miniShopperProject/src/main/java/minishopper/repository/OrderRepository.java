package minishopper.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import minishopper.entity.Order;
import minishopper.entity.User;

public interface OrderRepository extends JpaRepository<Order, String> {

	Order findOrderByOrderId(String orderId);

	List<Order> findByUser(User user);
	
	@Transactional
	@Modifying
	@Query("update orders o set o.orderStatus = :orderStatus, o.reason = :reason, o.deliveredDate = :date where o.orderId = :orderId")
	void updateOrderStatusByOrderId(@Param("orderStatus") String orderStatus, @Param("orderId") String orderId, 
			                        @Param("reason") String reason, @Param("date") LocalDate date); 

}
