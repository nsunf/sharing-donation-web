package com.sharingdonation.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sharingdonation.filter.LayoutFilter;
import com.sharingdonation.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
	@Value("${uploadPath}")
	String uploadPath;
	
	private final CategoryService categoryService;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")
			.addResourceLocations(this.uploadPath);
	}

//	@Override
//	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//		PageableHandlerMethodArgumentResolver pageableArgumentResolver = new PageableHandlerMethodArgumentResolver();
//        pageableArgumentResolver.setOneIndexedParameters(true);// 페이징시 0부터 시작이나 true 로 해주면 1부터 시작
//        argumentResolvers.add(pageableArgumentResolver);
//	}
	
	@Bean
	public FilterRegistrationBean<?> filterBean() {
		FilterRegistrationBean<?> registrationBean = new FilterRegistrationBean<LayoutFilter>(new LayoutFilter(categoryService));
		
		registrationBean.addUrlPatterns("/sharing/area/*");
		
		return registrationBean;
	}
}
