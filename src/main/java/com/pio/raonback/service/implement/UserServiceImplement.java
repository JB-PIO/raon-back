package com.pio.raonback.service.implement;

import com.pio.raonback.dto.request.user.UpdateProfileRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.user.GetFavoriteListResponseDto;
import com.pio.raonback.dto.response.user.GetProfileResponseDto;
import com.pio.raonback.entity.Favorite;
import com.pio.raonback.entity.Location;
import com.pio.raonback.entity.User;
import com.pio.raonback.repository.FavoriteRepository;
import com.pio.raonback.repository.LocationRepository;
import com.pio.raonback.repository.UserRepository;
import com.pio.raonback.security.RaonUser;
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
  private final FavoriteRepository favoriteRepository;
  private final LocationRepository locationRepository;

  @Override
  public ResponseEntity<? super GetProfileResponseDto> getProfile(RaonUser principal) {
    User user = principal.getUser();
    return GetProfileResponseDto.ok(user);
  }

  @Override
  public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Pageable pageable, RaonUser principal) {
    User user = principal.getUser();
    Page<Favorite> favoritePage = favoriteRepository.findAllByProductIsActiveTrueAndUser(user, pageable);
    return GetFavoriteListResponseDto.ok(favoritePage);
  }

  @Override
  public ResponseEntity<ResponseDto> updateProfile(UpdateProfileRequestDto dto, RaonUser principal) {
    User user = principal.getUser();

    if (dto.getNickname() != null) {
      String newNickname = dto.getNickname();
      boolean isNicknameTaken = userRepository.existsByNickname(newNickname);
      if (isNicknameTaken) return ResponseDto.nicknameExists();
      user.updateNickname(newNickname);
    }
    if (dto.getProfileImage() != null) {
      String newProfileImage = dto.getProfileImage();
      user.updateProfileImage(newProfileImage);
    }
    if (dto.getLocationId() != null) {
      Long locationId = dto.getLocationId();
      Optional<Location> optionalLocation = locationRepository.findById(locationId);
      if (optionalLocation.isEmpty()) return ResponseDto.locationNotFound();
      Location location = optionalLocation.get();
      user.updateLocation(location);
    }
    userRepository.save(user);
    return ResponseDto.ok();
  }

}
