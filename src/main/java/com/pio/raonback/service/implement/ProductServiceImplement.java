package com.pio.raonback.service.implement;

import com.pio.raonback.dto.request.product.PostProductRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.product.PostProductResponseDto;
import com.pio.raonback.entity.CategoryEntity;
import com.pio.raonback.entity.ProductEntity;
import com.pio.raonback.entity.ProductImageEntity;
import com.pio.raonback.entity.UserEntity;
import com.pio.raonback.repository.*;
import com.pio.raonback.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImplement implements ProductService {

  private final ProductRepository productRepository;
  private final ProductImageRepository productImageRepository;
  private final UserRepository userRepository;
  private final LocationRepository locationRepository;
  private final CategoryRepository categoryRepository;

  @Override
  public ResponseEntity<? super PostProductResponseDto> postProduct(PostProductRequestDto dto, String email) {
    try {
      UserEntity userEntity = userRepository.findByEmail(email);
      if (userEntity == null) return PostProductResponseDto.authFailed();
      Long userId = userEntity.getUserId();

      Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(dto.getCategoryId());
      if (optionalCategoryEntity.isEmpty()) return PostProductResponseDto.categoryNotFound();
      CategoryEntity categoryEntity = optionalCategoryEntity.get();
      if (!categoryEntity.isLeaf()) return PostProductResponseDto.notLeafCategory();

      boolean isLocationExist = locationRepository.existsById(dto.getLocationId());
      if (!isLocationExist) return PostProductResponseDto.locationNotFound();

      ProductEntity productEntity = new ProductEntity(dto, userId);
      productRepository.save(productEntity);

      List<String> imageUrls = dto.getImageUrls();
      if (imageUrls == null) return PostProductResponseDto.ok();

      List<ProductImageEntity> productImageEntities = new ArrayList<>();
      Long productId = productEntity.getProductId();

      for (String imageUrl : imageUrls) {
        ProductImageEntity productImageEntity = new ProductImageEntity(productId, imageUrl);
        productImageEntities.add(productImageEntity);
      }

      productImageRepository.saveAll(productImageEntities);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.serverError();
    }
    return PostProductResponseDto.ok();
  }

}
