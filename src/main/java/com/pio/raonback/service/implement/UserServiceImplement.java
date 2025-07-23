package com.pio.raonback.service.implement;

import com.pio.raonback.dto.request.user.UpdateLocationRequestDto;
import com.pio.raonback.dto.request.user.UpdateNicknameRequestDto;
import com.pio.raonback.dto.request.user.UpdateProfileImageRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.Location;
import com.pio.raonback.entity.User;
import com.pio.raonback.repository.LocationRepository;
import com.pio.raonback.repository.UserRepository;
import com.pio.raonback.security.RaonUser;
import com.pio.raonback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

  private final UserRepository userRepository;
  private final LocationRepository locationRepository;

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
    Optional<Location> optionalLocation = locationRepository.findById(dto.getLocationId());
    if (optionalLocation.isEmpty()) return ResponseDto.locationNotFound();
    Location location = optionalLocation.get();
    user.updateLocation(location);
    userRepository.save(user);
    return ResponseDto.ok();
  }

}
