package com.pio.raonback.service.implement;

import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.user.GetProductListResponseDto;
import com.pio.raonback.entity.ProductDetail;
import com.pio.raonback.entity.User;
import com.pio.raonback.repository.ProductDetailRepository;
import com.pio.raonback.repository.UserRepository;
import com.pio.raonback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

  private final UserRepository userRepository;
  private final ProductDetailRepository productDetailRepository;

  @Override
  public ResponseEntity<? super GetProductListResponseDto> getProductList(Long userId, Pageable pageable) {
    Optional<User> optionalUser = userRepository.findByUserIdAndIsDeletedFalseAndIsSuspendedFalse(userId);
    if (optionalUser.isEmpty()) return ResponseDto.userNotFound();
    User user = optionalUser.get();

    Page<ProductDetail> productDetailPage = productDetailRepository.findAllBySellerAndIsActiveTrue(user, pageable);
    return GetProductListResponseDto.ok(productDetailPage);
  }

}
