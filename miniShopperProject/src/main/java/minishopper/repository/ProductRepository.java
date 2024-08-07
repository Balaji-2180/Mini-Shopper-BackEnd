package minishopper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import minishopper.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

	Product findByProductId(String productId);

	@Query("select p from product p where trim(upper(p.productName)) = :productName")
	Product findByProductName(@Param("productName") String productName);

	List<Product> findByBrand(String brand);

	List<Product> findByCategory(String category);

	@Query("select p from product p where p.stock!=0")
	List<Product> findAvailableProducts();

	@Transactional
	@Modifying
	@Query("update product p set p.stock=:value where p.productId=:productId")
	void updateStock(@Param("productId") String productId, @Param("value") int stock);

}
