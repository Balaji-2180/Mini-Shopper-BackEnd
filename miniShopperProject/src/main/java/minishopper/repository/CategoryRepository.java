package minishopper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import minishopper.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
	Category findByCategoryId(String categoryId);
}
