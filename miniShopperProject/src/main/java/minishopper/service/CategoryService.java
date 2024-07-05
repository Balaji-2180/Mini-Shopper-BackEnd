package minishopper.service;

import java.util.List;

import minishopper.entity.Category;

public interface CategoryService {

	List<Category> fetchAllCategories();

	Category fetchCategoryById(String CategoryId);

}
