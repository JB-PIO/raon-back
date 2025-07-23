package com.pio.raonback.service.implement;

import com.pio.raonback.dto.request.product.PostProductRequestDto;
import com.pio.raonback.dto.request.product.PutFavoriteRequestDto;
import com.pio.raonback.dto.request.product.UpdateProductRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.product.*;
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
  private final FavoriteRepository favoriteRepository;

  @Override
  public ResponseEntity<? super GetProductListResponseDto> getProductList(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Product> productPage = productRepository.findAllByIsSoldFalseAndIsActiveTrueOrderByCreatedAtDesc(pageable);
    return GetProductListResponseDto.ok(productPage);
  }

  @Override
  public ResponseEntity<? super GetNearbyProductListResponseDto> getNearbyProductList(Long locationId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    boolean isLocationExists = locationRepository.existsById(locationId);
    if (!isLocationExists) return ResponseDto.locationNotFound();
    List<Location> nearbyLocations = locationRepository.findAllByWithinRadius(locationId, 10000L);
    Page<Product> productPage = productRepository.findAllByIsSoldFalseAndIsActiveTrueAndLocationInOrderByCreatedAtDesc(nearbyLocations, pageable);
    return GetNearbyProductListResponseDto.ok(productPage);
  }

  @Override
  public ResponseEntity<? super GetProductResponseDto> getProduct(Long productId) {
    Optional<Product> optionalProduct = productRepository.findByIsActiveTrueAndProductId(productId);
    if (optionalProduct.isEmpty()) return ResponseDto.productNotFound();
    Product product = optionalProduct.get();
    return GetProductResponseDto.ok(product);
  }

  @Override
  @Transactional
  public ResponseEntity<? super PostProductResponseDto> postProduct(PostProductRequestDto dto, RaonUser principal) {
    User seller = principal.getUser();

    Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
    if (optionalCategory.isEmpty()) return ResponseDto.categoryNotFound();
    Category category = optionalCategory.get();
    if (!category.getIsLeaf()) return ResponseDto.notLeafCategory();

    Optional<Location> optionalLocation = locationRepository.findById(dto.getLocationId());
    if (optionalLocation.isEmpty()) return ResponseDto.locationNotFound();
    Location location = optionalLocation.get();

    Product product =
        new Product(seller, category, location, dto.getTitle(), dto.getDescription(), dto.getPrice(), dto.getStatus(), dto.getTradeType());
    productRepository.save(product);

    List<String> imageUrls = dto.getImageUrlList();
    if (imageUrls == null) return PostProductResponseDto.ok(product.getProductId());

    List<ProductImage> productImages = new ArrayList<>();
    for (String imageUrl : imageUrls) {
      ProductImage productImage = new ProductImage(product, imageUrl);
      productImages.add(productImage);
    }

    productImageRepository.saveAll(productImages);
    return PostProductResponseDto.ok(product.getProductId());
  }

  @Override
  public ResponseEntity<? super CreateChatResponseDto> createChat(Long productId, RaonUser principal) {
    User buyer = principal.getUser();

    Optional<Product> optionalProduct = productRepository.findByIsActiveTrueAndProductId(productId);
    if (optionalProduct.isEmpty()) return ResponseDto.productNotFound();
    Product product = optionalProduct.get();

    User seller = product.getSeller();
    if (seller.equals(buyer)) return ResponseDto.ownProduct();

    boolean isChatExists = chatRepository.existsByProductAndBuyerAndSeller(product, buyer, seller);
    if (isChatExists) return ResponseDto.chatExists();

    Chat chat = new Chat(product, buyer, seller);
    chatRepository.save(chat);

    return CreateChatResponseDto.ok(chat.getChatId());
  }

  @Override
  @Transactional
  public ResponseEntity<? super UpdateProductResponseDto> updateProduct(Long productId, UpdateProductRequestDto dto, RaonUser principal) {
    User seller = principal.getUser();

    Optional<Product> optionalProduct = productRepository.findByIsActiveTrueAndProductId(productId);
    if (optionalProduct.isEmpty()) return ResponseDto.productNotFound();
    Product product = optionalProduct.get();

    if (!product.getSeller().equals(seller)) return ResponseDto.noPermission();
    if (product.getIsSold()) return ResponseDto.soldProduct();

    Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
    if (optionalCategory.isEmpty()) return ResponseDto.categoryNotFound();
    Category category = optionalCategory.get();
    if (!category.getIsLeaf()) return ResponseDto.notLeafCategory();

    Optional<Location> optionalLocation = locationRepository.findById(dto.getLocationId());
    if (optionalLocation.isEmpty()) return ResponseDto.locationNotFound();
    Location location = optionalLocation.get();

    product.update(category, location, dto.getTitle(), dto.getDescription(), dto.getPrice(), dto.getStatus(), dto.getTradeType());
    productRepository.save(product);

    productImageRepository.deleteAllByProduct(product);

    List<String> imageUrls = dto.getImageUrlList();
    if (imageUrls == null) return UpdateProductResponseDto.ok(product.getProductId());

    List<ProductImage> productImages = new ArrayList<>();
    for (String imageUrl : imageUrls) {
      ProductImage productImage = new ProductImage(product, imageUrl);
      productImages.add(productImage);
    }

    productImageRepository.saveAll(productImages);
    return UpdateProductResponseDto.ok(product.getProductId());
  }

  @Override
  @Transactional
  public ResponseEntity<ResponseDto> putFavorite(Long productId, PutFavoriteRequestDto dto, RaonUser principal) {
    User user = principal.getUser();

    Optional<Product> optionalProduct = productRepository.findByIsActiveTrueAndProductId(productId);
    if (optionalProduct.isEmpty()) return ResponseDto.productNotFound();
    Product product = optionalProduct.get();
    if (product.getSeller().equals(user)) return ResponseDto.ownProduct();

    Optional<Favorite> optionalFavorite = favoriteRepository.findByUserAndProduct(user, product);
    if (dto.getIsFavorite()) {
      if (optionalFavorite.isPresent()) return ResponseDto.ok();
      Favorite favorite = new Favorite(user, product);
      favoriteRepository.save(favorite);
    } else {
      if (optionalFavorite.isEmpty()) return ResponseDto.ok();
      Favorite favorite = optionalFavorite.get();
      favoriteRepository.delete(favorite);
    }

    return ResponseDto.ok();
  }

  @Override
  public ResponseEntity<ResponseDto> increaseViewCount(Long productId) {
    Optional<Product> optionalProduct = productRepository.findByIsActiveTrueAndProductId(productId);
    if (optionalProduct.isEmpty()) return ResponseDto.productNotFound();
    Product product = optionalProduct.get();
    product.increaseViewCount();
    productRepository.save(product);
    return ResponseDto.ok();
  }

  @Override
  public ResponseEntity<ResponseDto> deleteProduct(Long productId, RaonUser principal) {
    User seller = principal.getUser();

    Optional<Product> optionalProduct = productRepository.findByIsActiveTrueAndProductId(productId);
    if (optionalProduct.isEmpty()) return ResponseDto.productNotFound();
    Product product = optionalProduct.get();

    if (!product.getSeller().equals(seller)) return ResponseDto.noPermission();

    product.delete();
    productRepository.save(product);
    return ResponseDto.ok();
  }

}
