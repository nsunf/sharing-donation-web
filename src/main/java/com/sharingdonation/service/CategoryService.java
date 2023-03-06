package com.sharingdonation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sharingdonation.dto.CategoryDto;
import com.sharingdonation.entity.Category;
import com.sharingdonation.repository.CategoryRepository;
import com.sharingdonation.repository.SharingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
	private final CategoryRepository categoryRepo;
	
	public void addTestData() {
		String[] catNames = { "전체", "가전", "생활", "유아", "의류", "스포츠", "도서", "취미", "기타"};
		List<Category> categoryList = new ArrayList<>();
		
		for (int i = 0; i < catNames.length; i++) {
			Category category = new Category();
			category.setCategoryName(catNames[i]);
			category.setDelYn("N");
			category.setImgName("test-img-name");
			category.setImgUrl("test-img-url");
			categoryList.add(category);
		}
		
		categoryRepo.saveAll(categoryList);
	}
	

	public List<CategoryDto> getCategoryDtoLIst() {
		return categoryRepo.findAll().stream().map(CategoryDto::of).toList();
	}
}
