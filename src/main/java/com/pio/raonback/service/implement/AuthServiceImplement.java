package com.pio.raonback.service.implement;

import com.pio.raonback.dto.request.auth.SignInRequestDto;
import com.pio.raonback.dto.request.auth.SignUpRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.auth.SignInResponseDto;
import com.pio.raonback.dto.response.auth.SignUpResponseDto;
import com.pio.raonback.entity.UserEntity;
import com.pio.raonback.provider.JwtProvider;
import com.pio.raonback.repository.UserRepository;
import com.pio.raonback.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;


  @Override
  public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
    try {
      String email = dto.getEmail();
      boolean existedEmail = userRepository.existsByEmail(email);
      if (existedEmail) return SignUpResponseDto.duplicateEmail();

      String nickname = dto.getNickname();
      boolean existedNickname = userRepository.existsByNickname(nickname);
      if (existedNickname) return SignUpResponseDto.duplicateNickname();

      String password = dto.getPassword();
      String encodedPassword = bCryptPasswordEncoder.encode(password);
      dto.setPassword(encodedPassword);

      UserEntity userEntity = new UserEntity(dto);
      userRepository.save(userEntity);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return SignUpResponseDto.success();
  }

  @Override
  public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
    String token = null;

    try {
      String email = dto.getEmail();
      UserEntity userEntity = userRepository.findByEmail(email);
      if (userEntity == null) return SignInResponseDto.signInFailed();

      String password = dto.getPassword();
      String encodedPassword = userEntity.getPassword();
      boolean isMatched = bCryptPasswordEncoder.matches(password, encodedPassword);
      if (!isMatched) return SignInResponseDto.signInFailed();

      token = jwtProvider.create(email);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return SignInResponseDto.success(token);
  }

}
