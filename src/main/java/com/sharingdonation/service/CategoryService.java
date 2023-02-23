package com.sharingdonation.service;

import java.util.ArrayList;
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

}
