package minishopper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import minishopper.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
	CartItem findByCartItemId(int cartItemId);
}
