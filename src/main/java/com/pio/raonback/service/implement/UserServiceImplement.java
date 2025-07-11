package com.pio.raonback.service.implement;

import com.pio.raonback.dto.request.user.UpdateNicknameRequestDto;
import com.pio.raonback.dto.request.user.UpdateProfileImageRequestDto;
import com.pio.raonback.dto.response.user.UpdateNicknameResponseDto;
import com.pio.raonback.dto.response.user.UpdateProfileImageResponseDto;
import com.pio.raonback.entity.UserEntity;
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

  @Override
  public ResponseEntity<? super UpdateNicknameResponseDto> updateNickname(UpdateNicknameRequestDto dto, RaonUser userDetails) {
    UserEntity userEntity = userDetails.getUserEntity();
    String newNickname = dto.getNickname();
    boolean isNicknameTaken = userRepository.existsByNickname(newNickname);
    if (isNicknameTaken) return UpdateNicknameResponseDto.nicknameExists();
    userEntity.updateNickname(newNickname);
    userRepository.save(userEntity);
    return UpdateNicknameResponseDto.ok();
  }

  @Override
  public ResponseEntity<? super UpdateProfileImageResponseDto> updateProfileImage(UpdateProfileImageRequestDto dto, RaonUser userDetails) {
    UserEntity userEntity = userDetails.getUserEntity();
    String newProfileImage = dto.getProfileImage();
    userEntity.updateProfileImage(newProfileImage);
    userRepository.save(userEntity);
    return UpdateProfileImageResponseDto.ok();
  }

}
