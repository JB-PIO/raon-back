package com.pio.raonback.service.implement;

import com.pio.raonback.dto.request.me.UpdateLocationRequestDto;
import com.pio.raonback.dto.request.me.UpdateNicknameRequestDto;
import com.pio.raonback.dto.request.me.UpdateProfileImageRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.me.GetFavoriteListResponseDto;
import com.pio.raonback.dto.response.me.GetProductListResponseDto;
import com.pio.raonback.dto.response.me.GetProfileResponseDto;
import com.pio.raonback.dto.response.me.GetTradeListResponseDto;
import com.pio.raonback.entity.*;
import com.pio.raonback.repository.*;
import com.pio.raonback.security.RaonUser;
import com.pio.raonback.service.MeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeServiceImplement implements MeService {

  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final ProductDetailRepository productDetailRepository;
  private final FavoriteRepository favoriteRepository;
  private final LocationRepository locationRepository;
  private final TradeRepository tradeRepository;
  private final RefreshTokenRepository refreshTokenRepository;

  @Override
  public ResponseEntity<? super GetProfileResponseDto> getProfile(RaonUser principal) {
    User user = principal.getUser();
    return GetProfileResponseDto.ok(user);
  }

  @Override
  public ResponseEntity<? super GetProductListResponseDto> getProducts(Pageable pageable, RaonUser principal) {
    User user = principal.getUser();
    Page<ProductDetail> productDetailPage = productDetailRepository.findAllBySellerAndIsDeletedFalse(user, pageable);
    return GetProductListResponseDto.ok(productDetailPage);
  }

  @Override
  public ResponseEntity<? super GetFavoriteListResponseDto> getFavorites(Pageable pageable, RaonUser principal) {
    User user = principal.getUser();
    Page<Favorite> favoritePage = favoriteRepository.findAllByUserAndProductIsDeletedFalse(user, pageable);
    return GetFavoriteListResponseDto.ok(favoritePage);
  }

  @Override
  public ResponseEntity<? super GetTradeListResponseDto> getTrades(String type, Pageable pageable, RaonUser principal) {
    User user = principal.getUser();
    Page<Trade> tradePage = switch (type) {
      case "buy" -> tradeRepository.findAllByBuyer(user, pageable);
      case "sell" -> tradeRepository.findAllBySeller(user, pageable);
      default -> tradeRepository.findAllByBuyerOrSeller(user, user, pageable);
    };
    return GetTradeListResponseDto.ok(tradePage);
  }

  @Override
  public ResponseEntity<ResponseDto> updateNickname(UpdateNicknameRequestDto dto, RaonUser principal) {
    User user = principal.getUser();

    String newNickname = dto.getNickname();
    boolean isNicknameTaken = userRepository.existsByNickname(newNickname);
    if (isNicknameTaken) return ResponseDto.nicknameExists();

    user.updateNickname(newNickname);
    userRepository.save(user);
    return ResponseDto.ok();
  }

  @Override
  public ResponseEntity<ResponseDto> updateProfileImage(UpdateProfileImageRequestDto dto, RaonUser principal) {
    User user = principal.getUser();

    String newProfileImage = dto.getProfileImage();
    user.updateProfileImage(newProfileImage);
    userRepository.save(user);
    return ResponseDto.ok();
  }

  @Override
  public ResponseEntity<ResponseDto> updateLocation(UpdateLocationRequestDto dto, RaonUser principal) {
    User user = principal.getUser();

    Long locationId = dto.getLocationId();
    Optional<Location> optionalLocation = locationRepository.findById(locationId);
    if (optionalLocation.isEmpty()) return ResponseDto.locationNotFound();
    Location location = optionalLocation.get();

    user.updateLocation(location);
    userRepository.save(user);
    return ResponseDto.ok();
  }

  @Override
  @Transactional
  public ResponseEntity<ResponseDto> deleteAccount(RaonUser principal) {
    User user = principal.getUser();

    favoriteRepository.deleteAllByUser(user);
    refreshTokenRepository.deleteByUser(user);
    tradeRepository.deleteAllByBuyerNullAndSeller(user);

    List<Product> products = productRepository.findAllBySellerAndIsDeletedFalse(user);
    for (Product product : products) {
      favoriteRepository.deleteAllByProduct(product);
      product.delete();
      productRepository.save(product);
    }

    user.delete();
    userRepository.save(user);

    return ResponseDto.ok();
  }

  @Override
  public ResponseEntity<ResponseDto> deleteProfileImage(RaonUser principal) {
    User user = principal.getUser();

    user.updateProfileImage(null);
    userRepository.save(user);
    return ResponseDto.ok();
  }

}
