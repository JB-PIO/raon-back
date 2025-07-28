package com.pio.raonback.service.implement;

import com.pio.raonback.dto.request.user.UpdateProfileRequestDto;
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
