package com.pio.raonback.service.implement;

import com.pio.raonback.dto.response.category.GetCategoryListResponseDto;
import com.pio.raonback.entity.Category;
import com.pio.raonback.repository.CategoryRepository;
import com.pio.raonback.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImplement implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  public ResponseEntity<? super GetCategoryListResponseDto> getCategories() {
    List<Category> rootCategories = categoryRepository.findAllByParentNull();
    return GetCategoryListResponseDto.ok(rootCategories);
  }

}
