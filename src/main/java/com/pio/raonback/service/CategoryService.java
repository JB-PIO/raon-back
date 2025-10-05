package com.pio.raonback.service;

import com.pio.raonback.dto.response.category.GetCategoriesResponseDto;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

  ResponseEntity<? super GetCategoriesResponseDto> getCategories();

}
