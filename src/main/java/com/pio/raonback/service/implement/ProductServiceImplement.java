package com.pio.raonback.service.implement;

import com.pio.raonback.dto.request.product.PostProductRequestDto;
import com.pio.raonback.dto.response.product.GetProductListResponseDto;
import com.pio.raonback.dto.response.product.PostProductResponseDto;
import com.pio.raonback.entity.*;
import com.pio.raonback.repository.*;
import com.pio.raonback.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
  private final ProductDetailViewRepository productDetailViewRepository;

  @Override
  public ResponseEntity<? super GetProductListResponseDto> getProductList(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<ProductDetailViewEntity> productDetailViewEntitiesPage = productDetailViewRepository.findAllByIsSoldFalseAndIsActiveTrueOrderByCreatedAtDesc(pageable);
    return GetProductListResponseDto.ok(productDetailViewEntitiesPage);
  }

  @Override
  public ResponseEntity<? super PostProductResponseDto> postProduct(PostProductRequestDto dto, String email) {
    UserEntity userEntity = userRepository.findByEmail(email);
    if (userEntity == null) return PostProductResponseDto.authFailed();
    Long sellerId = userEntity.getUserId();

    Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(dto.getCategoryId());
    if (optionalCategoryEntity.isEmpty()) return PostProductResponseDto.categoryNotFound();
    CategoryEntity categoryEntity = optionalCategoryEntity.get();
    if (!categoryEntity.getIsLeaf()) return PostProductResponseDto.notLeafCategory();

    boolean isLocationExist = locationRepository.existsById(dto.getLocationId());
    if (!isLocationExist) return PostProductResponseDto.locationNotFound();

    ProductEntity productEntity = new ProductEntity(dto, sellerId);
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
    return PostProductResponseDto.ok();
  }

}
