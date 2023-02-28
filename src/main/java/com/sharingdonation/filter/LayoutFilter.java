package com.sharingdonation.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.sharingdonation.dto.CategoryDto;
import com.sharingdonation.service.CategoryService;

import lombok.RequiredArgsConstructor;

@WebFilter
@RequiredArgsConstructor
public class LayoutFilter implements Filter {
	private final CategoryService categoryService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		List<CategoryDto> categoryDtoList = categoryService.getCategoryDtoLIst();
		
		request.setAttribute("categoryDtoList", categoryDtoList);

		chain.doFilter(request, response);
		
	}
}
