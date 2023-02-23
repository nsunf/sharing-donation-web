package com.sharingdonation.serviece;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sharingdonation.dto.CategoryDto;
import com.sharingdonation.entity.Category;
import com.sharingdonation.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
	private final CategoryRepository categoryRepo;
	
	public void addTestData() {
		Category category = new Category();
		
//		categoryRepo.save();
	}
	
	public List<CategoryDto> getCategoryList() {
		return categoryRepo.findAll().stream().map(CategoryDto::of).toList();
	}
}
