package com.pio.raonback.service;

import com.pio.raonback.dto.response.category.GetCategoryListResponseDto;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

  ResponseEntity<? super GetCategoryListResponseDto> getCategoryList();

}
