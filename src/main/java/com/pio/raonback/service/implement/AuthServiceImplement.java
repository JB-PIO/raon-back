package com.pio.raonback.service.implement;

import com.pio.raonback.dto.request.auth.SignInRequestDto;
import com.pio.raonback.dto.request.auth.SignUpRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.auth.RefreshTokenResponseDto;
import com.pio.raonback.dto.response.auth.SignInResponseDto;
import com.pio.raonback.dto.response.auth.SignUpResponseDto;
import com.pio.raonback.entity.RefreshTokenEntity;
import com.pio.raonback.entity.UserEntity;
import com.pio.raonback.repository.LocationRepository;
import com.pio.raonback.repository.RefreshTokenRepository;
import com.pio.raonback.repository.UserRepository;
import com.pio.raonback.security.jwt.JwtProperties;
import com.pio.raonback.security.jwt.JwtUtil;
import com.pio.raonback.service.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final LocationRepository locationRepository;

  private final JwtProperties jwtProperties;
  private final JwtUtil jwtUtil;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
    String email = dto.getEmail();
    boolean isEmailTaken = userRepository.existsByEmail(email);
    if (isEmailTaken) return ResponseDto.emailExists();

    String nickname = dto.getNickname();
    boolean isNicknameTaken = userRepository.existsByNickname(nickname);
    if (isNicknameTaken) return ResponseDto.nicknameExists();

    boolean isLocationValid = locationRepository.existsById(dto.getLocationId());
    if (!isLocationValid) return ResponseDto.locationNotFound();

    String rawPassword = dto.getPassword();
    String hashedPassword = passwordEncoder.encode(rawPassword);
    dto.setPassword(hashedPassword);

    UserEntity userEntity = new UserEntity(dto);
    userRepository.save(userEntity);

    refreshTokenRepository.deleteByEmail(email);
    refreshTokenRepository.flush();
    String accessToken = jwtUtil.generateAccessToken(email);
    String refreshToken = jwtUtil.generateRefreshToken(email);
    String refreshTokenCookie = buildRefreshTokenCookie(refreshToken);
    RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(email, jwtUtil.generateTokenHash(refreshToken), jwtUtil.getExpirationFromToken(refreshToken));
    refreshTokenRepository.save(refreshTokenEntity);

    return SignUpResponseDto.ok(accessToken, refreshTokenCookie, jwtProperties.getAccessTokenExpirationTime());
  }

  @Override
  @Transactional
  public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
    String email = dto.getEmail();
    Optional<UserEntity> optionalUserEntity = userRepository.findByIsDeletedFalseAndEmail(email);
    if (optionalUserEntity.isEmpty()) return ResponseDto.signInFailed();
    UserEntity userEntity = optionalUserEntity.get();

    String inputPassword = dto.getPassword();
    String storedPassword = userEntity.getPassword();
    boolean isPasswordValid = passwordEncoder.matches(inputPassword, storedPassword);
    if (!isPasswordValid) return ResponseDto.signInFailed();
    if (userEntity.getIsSuspended()) return ResponseDto.suspendedUser();

    refreshTokenRepository.deleteByEmail(email);
    refreshTokenRepository.flush();
    String accessToken = jwtUtil.generateAccessToken(email);
    String refreshToken = jwtUtil.generateRefreshToken(email);
    String refreshTokenCookie = buildRefreshTokenCookie(refreshToken);
    RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(email, jwtUtil.generateTokenHash(refreshToken), jwtUtil.getExpirationFromToken(refreshToken));
    refreshTokenRepository.save(refreshTokenEntity);

    return SignInResponseDto.ok(accessToken, refreshTokenCookie, jwtProperties.getAccessTokenExpirationTime());
  }

  @Override
  @Transactional
  public ResponseEntity<? super RefreshTokenResponseDto> refreshToken(String refreshToken) {
    try {
      jwtUtil.validateRefreshToken(refreshToken);
    } catch (ExpiredJwtException exception) {
      if (exception.getClaims().get("type", String.class).equals("refresh")) {
        return ResponseDto.expiredToken();
      } else {
        return ResponseDto.authFailed();
      }
    } catch (JwtException exception) {
      return ResponseDto.authFailed();
    }
    String refreshTokenHash = jwtUtil.generateTokenHash(refreshToken);
    Optional<RefreshTokenEntity> optionalRefreshTokenEntity = refreshTokenRepository.findByToken(refreshTokenHash);
    if (optionalRefreshTokenEntity.isEmpty()) return ResponseDto.expiredToken();
    RefreshTokenEntity refreshTokenEntity = optionalRefreshTokenEntity.get();

    String email = refreshTokenEntity.getEmail();
    refreshTokenRepository.deleteByEmail(email);
    refreshTokenRepository.flush();
    String newAccessToken = jwtUtil.generateAccessToken(email);
    String newRefreshToken = jwtUtil.generateRefreshToken(email);
    String newRefreshTokenCookie = buildRefreshTokenCookie(newRefreshToken);
    RefreshTokenEntity newRefreshTokenEntity = new RefreshTokenEntity(email, jwtUtil.generateTokenHash(newRefreshToken), jwtUtil.getExpirationFromToken(newRefreshToken));
    refreshTokenRepository.save(newRefreshTokenEntity);

    return RefreshTokenResponseDto.ok(newAccessToken, newRefreshTokenCookie, jwtProperties.getAccessTokenExpirationTime());
  }

  @Override
  public ResponseEntity<ResponseDto> signOut(String refreshToken) {
    String refreshTokenHash = jwtUtil.generateTokenHash(refreshToken);
    refreshTokenRepository.deleteByToken(refreshTokenHash);
    return ResponseDto.ok();
  }

  private String buildRefreshTokenCookie(String refreshToken) {
    return ResponseCookie.from("refreshToken", refreshToken).maxAge(jwtProperties.getRefreshTokenExpirationTime())
                         .path("/api/v1/auth").httpOnly(true).secure(true).build().toString();
  }

}
