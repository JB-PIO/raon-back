package com.pio.raonback.controller;

import com.pio.raonback.dto.response.category.GetCategoriesResponseDto;
import com.pio.raonback.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping("")
  @Cacheable(cacheNames = "categories")
  public ResponseEntity<? super GetCategoriesResponseDto> getCategories() {
    return categoryService.getCategories();
  }

}
