package com.sharingdonation.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharingdonation.dto.StoryFormDto;
import com.sharingdonation.service.StoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StoryController {
	
	private final StoryService storyService;
	ObjectMapper mapper = new ObjectMapper();

	@PostMapping("/story/new")
	public @ResponseBody ResponseEntity<?> addStory(@RequestBody Map<String, Object> map) {
		
		StoryFormDto storyFormDto = mapper.convertValue(map, StoryFormDto.class);
		Long storyId = storyService.addStory(storyFormDto);
		
		return new ResponseEntity<Long>(storyId, HttpStatus.OK);
	}
}
