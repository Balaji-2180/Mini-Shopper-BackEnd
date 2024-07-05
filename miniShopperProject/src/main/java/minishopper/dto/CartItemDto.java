package minishopper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import minishopper.entity.Cart;
import minishopper.entity.CartItem;
import minishopper.entity.Product;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {

	private int cartItemId;
	private ProductDto product;
	private int quantity;
	private double totalPrice;

}
