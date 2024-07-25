package minishopper.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import minishopper.entity.Category;

public interface CategoryService {
	
	@Cacheable(value = "allCategories") 
	List<Category> fetchAllCategories();
	
	@Cacheable(value = "category", key = "#categoryId") 
	Category fetchCategoryById(String categoryId);

}
