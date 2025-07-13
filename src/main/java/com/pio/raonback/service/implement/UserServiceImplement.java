package com.pio.raonback.service.implement;

import com.pio.raonback.dto.request.user.UpdateLocationRequestDto;
import com.pio.raonback.dto.request.user.UpdateNicknameRequestDto;
import com.pio.raonback.dto.request.user.UpdateProfileImageRequestDto;
import com.pio.raonback.dto.response.user.UpdateLocationResponseDto;
import com.pio.raonback.dto.response.user.UpdateNicknameResponseDto;
import com.pio.raonback.dto.response.user.UpdateProfileImageResponseDto;
import com.pio.raonback.entity.UserEntity;
import com.pio.raonback.repository.LocationRepository;
import com.pio.raonback.repository.UserRepository;
import com.pio.raonback.security.RaonUser;
import com.pio.raonback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

  private final UserRepository userRepository;
  private final LocationRepository locationRepository;

  @Override
  public ResponseEntity<? super UpdateNicknameResponseDto> updateNickname(UpdateNicknameRequestDto dto, RaonUser user) {
    UserEntity userEntity = user.getUserEntity();
    String newNickname = dto.getNickname();
    boolean isNicknameTaken = userRepository.existsByNickname(newNickname);
    if (isNicknameTaken) return UpdateNicknameResponseDto.nicknameExists();
    userEntity.updateNickname(newNickname);
    userRepository.save(userEntity);
    return UpdateNicknameResponseDto.ok();
  }

  @Override
  public ResponseEntity<? super UpdateProfileImageResponseDto> updateProfileImage(UpdateProfileImageRequestDto dto, RaonUser user) {
    UserEntity userEntity = user.getUserEntity();
    String newProfileImage = dto.getProfileImage();
    userEntity.updateProfileImage(newProfileImage);
    userRepository.save(userEntity);
    return UpdateProfileImageResponseDto.ok();
  }

  @Override
  public ResponseEntity<? super UpdateLocationResponseDto> updateLocation(UpdateLocationRequestDto dto, RaonUser user) {
    UserEntity userEntity = user.getUserEntity();
    Long locationId = dto.getLocationId();
    boolean isLocationValid = locationRepository.existsById(locationId);
    if (!isLocationValid) return UpdateLocationResponseDto.locationNotFound();
    userEntity.updateLocation(locationId);
    userRepository.save(userEntity);
    return UpdateLocationResponseDto.ok();
  }

}
