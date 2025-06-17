package com.pio.raonback.service.implement;

import com.pio.raonback.dto.request.product.PostProductRequestDto;
import com.pio.raonback.dto.request.product.PutProductRequestDto;
import com.pio.raonback.dto.response.product.*;
import com.pio.raonback.entity.*;
import com.pio.raonback.repository.*;
import com.pio.raonback.service.ProductService;
import jakarta.transaction.Transactional;
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
  public ResponseEntity<? super GetNearbyProductListResponseDto> getNearbyProductList(Long locationId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    boolean isLocationExist = locationRepository.existsById(locationId);
    if (!isLocationExist) return GetNearbyProductListResponseDto.locationNotFound();
    List<LocationEntity> nearbyLocationEntities = locationRepository.findAllByWithinRadius(locationId, 10000L);
    List<Long> nearbyLocationIds = nearbyLocationEntities.stream().map(LocationEntity::getLocationId).toList();
    Page<ProductDetailViewEntity> productDetailViewEntitiesPage = productDetailViewRepository.findAllByIsSoldFalseAndIsActiveTrueAndLocationIdInOrderByCreatedAtDesc(nearbyLocationIds, pageable);
    return GetNearbyProductListResponseDto.ok(productDetailViewEntitiesPage);
  }

  @Override
  public ResponseEntity<? super GetProductResponseDto> getProduct(Long productId) {
    Optional<ProductDetailViewEntity> optionalProductDetailViewEntity = productDetailViewRepository.findByIsActiveTrueAndProductId(productId);
    if (optionalProductDetailViewEntity.isEmpty()) return GetProductResponseDto.productNotFound();
    ProductDetailViewEntity productDetailViewEntity = optionalProductDetailViewEntity.get();
    List<ProductImageEntity> productImageEntities = productImageRepository.findAllByProductId(productId);
    return GetProductResponseDto.ok(productDetailViewEntity, productImageEntities);
  }

  @Override
  @Transactional
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

    List<String> imageUrls = dto.getImageUrlList();
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

  @Override
  @Transactional
  public ResponseEntity<? super PutProductResponseDto> updateProduct(Long productId, PutProductRequestDto dto, String email) {
    UserEntity userEntity = userRepository.findByEmail(email);
    if (userEntity == null) return PutProductResponseDto.authFailed();
    Long userId = userEntity.getUserId();

    Optional<ProductEntity> optionalProductEntity = productRepository.findById(productId);
    if (optionalProductEntity.isEmpty()) return PutProductResponseDto.productNotFound();
    ProductEntity productEntity = optionalProductEntity.get();

    if (!productEntity.getSellerId().equals(userId)) return PutProductResponseDto.noPermission();

    Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(dto.getCategoryId());
    if (optionalCategoryEntity.isEmpty()) return PutProductResponseDto.categoryNotFound();
    CategoryEntity categoryEntity = optionalCategoryEntity.get();
    if (!categoryEntity.getIsLeaf()) return PutProductResponseDto.notLeafCategory();

    boolean isLocationExist = locationRepository.existsById(dto.getLocationId());
    if (!isLocationExist) return PutProductResponseDto.locationNotFound();

    productEntity.update(dto);
    productRepository.save(productEntity);

    productImageRepository.deleteAllByProductId(productId);

    List<String> imageUrls = dto.getImageUrlList();
    if (imageUrls == null) return PutProductResponseDto.ok();

    List<ProductImageEntity> productImageEntities = new ArrayList<>();
    for (String imageUrl : imageUrls) {
      ProductImageEntity productImageEntity = new ProductImageEntity(productId, imageUrl);
      productImageEntities.add(productImageEntity);
    }

    productImageRepository.saveAll(productImageEntities);
    return PutProductResponseDto.ok();
  }

  @Override
  public ResponseEntity<? super DeleteProductResponseDto> deleteProduct(Long productId, String email) {
    UserEntity userEntity = userRepository.findByEmail(email);
    if (userEntity == null) return DeleteProductResponseDto.authFailed();
    Long userId = userEntity.getUserId();

    Optional<ProductEntity> optionalProductEntity = productRepository.findById(productId);
    if (optionalProductEntity.isEmpty()) return DeleteProductResponseDto.productNotFound();
    ProductEntity productEntity = optionalProductEntity.get();
    if (productEntity.getIsDeleted()) return DeleteProductResponseDto.productNotFound();

    if (!productEntity.getSellerId().equals(userId)) return DeleteProductResponseDto.noPermission();

    productEntity.delete();
    productRepository.save(productEntity);
    return DeleteProductResponseDto.ok();
  }

}
