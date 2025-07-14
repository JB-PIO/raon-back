package com.pio.raonback.service.implement;

import com.pio.raonback.dto.request.product.PostProductRequestDto;
import com.pio.raonback.dto.request.product.UpdateProductRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.product.CreateChatResponseDto;
import com.pio.raonback.dto.response.product.GetNearbyProductListResponseDto;
import com.pio.raonback.dto.response.product.GetProductListResponseDto;
import com.pio.raonback.dto.response.product.GetProductResponseDto;
import com.pio.raonback.entity.*;
import com.pio.raonback.repository.*;
import com.pio.raonback.security.RaonUser;
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
  private final LocationRepository locationRepository;
  private final CategoryRepository categoryRepository;
  private final ChatRepository chatRepository;
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
    if (!isLocationExist) return ResponseDto.locationNotFound();
    List<LocationEntity> nearbyLocationEntities = locationRepository.findAllByWithinRadius(locationId, 10000L);
    List<Long> nearbyLocationIds = nearbyLocationEntities.stream().map(LocationEntity::getLocationId).toList();
    Page<ProductDetailViewEntity> productDetailViewEntitiesPage = productDetailViewRepository.findAllByIsSoldFalseAndIsActiveTrueAndLocationIdInOrderByCreatedAtDesc(nearbyLocationIds, pageable);
    return GetNearbyProductListResponseDto.ok(productDetailViewEntitiesPage);
  }

  @Override
  public ResponseEntity<? super GetProductResponseDto> getProduct(Long productId) {
    Optional<ProductDetailViewEntity> optionalProductDetailViewEntity = productDetailViewRepository.findByIsActiveTrueAndProductId(productId);
    if (optionalProductDetailViewEntity.isEmpty()) return ResponseDto.productNotFound();
    ProductDetailViewEntity productDetailViewEntity = optionalProductDetailViewEntity.get();
    List<ProductImageEntity> productImageEntities = productImageRepository.findAllByProductId(productId);
    return GetProductResponseDto.ok(productDetailViewEntity, productImageEntities);
  }

  @Override
  @Transactional
  public ResponseEntity<ResponseDto> postProduct(PostProductRequestDto dto, RaonUser user) {
    UserEntity userEntity = user.getUserEntity();
    Long sellerId = userEntity.getUserId();

    Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(dto.getCategoryId());
    if (optionalCategoryEntity.isEmpty()) return ResponseDto.categoryNotFound();
    CategoryEntity categoryEntity = optionalCategoryEntity.get();
    if (!categoryEntity.getIsLeaf()) return ResponseDto.notLeafCategory();

    boolean isLocationExist = locationRepository.existsById(dto.getLocationId());
    if (!isLocationExist) return ResponseDto.locationNotFound();

    ProductEntity productEntity = new ProductEntity(dto, sellerId);
    productRepository.save(productEntity);

    List<String> imageUrls = dto.getImageUrlList();
    if (imageUrls == null) return ResponseDto.ok();

    List<ProductImageEntity> productImageEntities = new ArrayList<>();
    Long productId = productEntity.getProductId();

    for (String imageUrl : imageUrls) {
      ProductImageEntity productImageEntity = new ProductImageEntity(productId, imageUrl);
      productImageEntities.add(productImageEntity);
    }

    productImageRepository.saveAll(productImageEntities);
    return ResponseDto.ok();
  }

  @Override
  public ResponseEntity<? super CreateChatResponseDto> createChat(Long productId, RaonUser user) {
    UserEntity userEntity = user.getUserEntity();
    Long buyerId = userEntity.getUserId();

    Optional<ProductEntity> optionalProductEntity = productRepository.findById(productId);
    if (optionalProductEntity.isEmpty()) return ResponseDto.productNotFound();
    ProductEntity productEntity = optionalProductEntity.get();

    Long sellerId = productEntity.getSellerId();
    if (buyerId.equals(sellerId)) return ResponseDto.ownProduct();

    boolean isChatExists = chatRepository.existsByProductIdAndBuyerIdAndSellerId(productId, buyerId, sellerId);
    if (isChatExists) return ResponseDto.chatExists();

    ChatEntity chatEntity = new ChatEntity(productId, buyerId, sellerId);
    chatRepository.save(chatEntity);
    Long chatId = chatEntity.getChatId();

    return CreateChatResponseDto.ok(chatId);
  }

  @Override
  @Transactional
  public ResponseEntity<ResponseDto> updateProduct(Long productId, UpdateProductRequestDto dto, RaonUser user) {
    UserEntity userEntity = user.getUserEntity();
    Long userId = userEntity.getUserId();

    Optional<ProductEntity> optionalProductEntity = productRepository.findById(productId);
    if (optionalProductEntity.isEmpty()) return ResponseDto.productNotFound();
    ProductEntity productEntity = optionalProductEntity.get();

    if (!productEntity.getSellerId().equals(userId)) return ResponseDto.noPermission();

    Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(dto.getCategoryId());
    if (optionalCategoryEntity.isEmpty()) return ResponseDto.categoryNotFound();
    CategoryEntity categoryEntity = optionalCategoryEntity.get();
    if (!categoryEntity.getIsLeaf()) return ResponseDto.notLeafCategory();

    boolean isLocationExist = locationRepository.existsById(dto.getLocationId());
    if (!isLocationExist) return ResponseDto.locationNotFound();

    productEntity.update(dto);
    productRepository.save(productEntity);

    productImageRepository.deleteAllByProductId(productId);

    List<String> imageUrls = dto.getImageUrlList();
    if (imageUrls == null) return ResponseDto.ok();

    List<ProductImageEntity> productImageEntities = new ArrayList<>();
    for (String imageUrl : imageUrls) {
      ProductImageEntity productImageEntity = new ProductImageEntity(productId, imageUrl);
      productImageEntities.add(productImageEntity);
    }

    productImageRepository.saveAll(productImageEntities);
    return ResponseDto.ok();
  }

  @Override
  public ResponseEntity<ResponseDto> increaseViewCount(Long productId) {
    Optional<ProductEntity> optionalProductEntity = productRepository.findByIsActiveTrueAndProductId(productId);
    if (optionalProductEntity.isEmpty()) return ResponseDto.productNotFound();
    ProductEntity productEntity = optionalProductEntity.get();
    productEntity.increaseViewCount();
    productRepository.save(productEntity);
    return ResponseDto.ok();
  }

  @Override
  public ResponseEntity<ResponseDto> deleteProduct(Long productId, RaonUser user) {
    UserEntity userEntity = user.getUserEntity();
    Long userId = userEntity.getUserId();

    Optional<ProductEntity> optionalProductEntity = productRepository.findById(productId);
    if (optionalProductEntity.isEmpty()) return ResponseDto.productNotFound();
    ProductEntity productEntity = optionalProductEntity.get();
    if (productEntity.getIsDeleted()) return ResponseDto.productNotFound();

    if (!productEntity.getSellerId().equals(userId)) return ResponseDto.noPermission();

    productEntity.delete();
    productRepository.save(productEntity);
    return ResponseDto.ok();
  }

}
