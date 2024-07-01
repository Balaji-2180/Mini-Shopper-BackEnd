package com.example.demo.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Category;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Service.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService{
	
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
		//System.out.println("in service impl "+categoryRepository.findByCategoryId(CategoryId));
		return categoryRepository.findByCategoryId(CategoryId);
	}

}
