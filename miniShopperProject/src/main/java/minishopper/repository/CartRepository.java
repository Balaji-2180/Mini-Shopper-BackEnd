package minishopper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import minishopper.entity.Cart;
import minishopper.entity.User;

public interface CartRepository extends JpaRepository<Cart, String> {

	Cart findByUser(User user);

	Cart findByCartId(String cartId);

}
