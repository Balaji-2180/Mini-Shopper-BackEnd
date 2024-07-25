package minishopper.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart", indexes= {@Index(name="idx_cart_id", columnList = "cart_id")})
public class Cart {
	
	@Id
	private String cartId;
	
	@OneToOne	
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
	private List<CartItem> items=new ArrayList<>();
	
	

}
