package minishopper.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import minishopper.entity.Category;
import minishopper.repository.CategoryRepository;
import minishopper.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public List<Category> fetchAllCategories() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	@Override
	public Category fetchCategoryById(String CategoryId) {
		// TODO Auto-generated method stub
		return categoryRepository.findByCategoryId(CategoryId);
	}

}
