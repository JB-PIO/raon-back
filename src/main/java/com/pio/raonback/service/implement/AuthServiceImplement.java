package com.pio.raonback.service.implement;

import com.pio.raonback.dto.request.auth.SignInRequestDto;
import com.pio.raonback.dto.request.auth.SignUpRequestDto;
import com.pio.raonback.dto.response.auth.SignInResponseDto;
import com.pio.raonback.dto.response.auth.SignUpResponseDto;
import com.pio.raonback.entity.UserEntity;
import com.pio.raonback.provider.JwtProvider;
import com.pio.raonback.repository.LocationRepository;
import com.pio.raonback.repository.UserRepository;
import com.pio.raonback.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

  private final UserRepository userRepository;
  private final LocationRepository locationRepository;
  private final JwtProvider jwtProvider;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;


  @Override
  public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
    String email = dto.getEmail();
    boolean isEmailTaken = userRepository.existsByEmail(email);
    if (isEmailTaken) return SignUpResponseDto.emailExists();

    String nickname = dto.getNickname();
    boolean isNicknameTaken = userRepository.existsByNickname(nickname);
    if (isNicknameTaken) return SignUpResponseDto.nicknameExists();

    boolean isLocationValid = locationRepository.existsById(dto.getLocationId());
    if (!isLocationValid) return SignUpResponseDto.locationNotFound();

    String rawPassword = dto.getPassword();
    String hashedPassword = bCryptPasswordEncoder.encode(rawPassword);
    dto.setPassword(hashedPassword);

    UserEntity userEntity = new UserEntity(dto);
    userRepository.save(userEntity);
    return SignUpResponseDto.ok();
  }

  @Override
  public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
    String email = dto.getEmail();
    Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(email);
    if (optionalUserEntity.isEmpty()) return SignInResponseDto.signInFailed();
    UserEntity userEntity = optionalUserEntity.get();

    String inputPassword = dto.getPassword();
    String storedPassword = userEntity.getPassword();
    boolean isPasswordValid = bCryptPasswordEncoder.matches(inputPassword, storedPassword);
    if (!isPasswordValid) return SignInResponseDto.signInFailed();

    String token = jwtProvider.create(email);
    return SignInResponseDto.ok(token);
  }

}
