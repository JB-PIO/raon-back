package com.pio.raonback.service.implement;

import com.pio.raonback.dto.request.auth.SignInRequestDto;
import com.pio.raonback.dto.request.auth.SignUpRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.auth.RefreshTokenResponseDto;
import com.pio.raonback.dto.response.auth.SignInResponseDto;
import com.pio.raonback.dto.response.auth.SignUpResponseDto;
import com.pio.raonback.entity.Location;
import com.pio.raonback.entity.RefreshToken;
import com.pio.raonback.entity.User;
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

    Optional<Location> optionalLocation = locationRepository.findById(dto.getLocationId());
    if (optionalLocation.isEmpty()) return ResponseDto.locationNotFound();
    Location location = optionalLocation.get();

    String hashedPassword = passwordEncoder.encode(dto.getPassword());

    User user = new User(nickname, email, hashedPassword, location);
    userRepository.save(user);

    refreshTokenRepository.deleteByUser(user);
    refreshTokenRepository.flush();
    String accessToken = jwtUtil.generateAccessToken(email);
    String refreshToken = jwtUtil.generateRefreshToken(email);
    String refreshTokenCookie = buildRefreshTokenCookie(refreshToken);

    RefreshToken refreshTokenEntity =
        new RefreshToken(user, jwtUtil.generateTokenHash(refreshToken), jwtUtil.getExpirationFromToken(refreshToken));
    refreshTokenRepository.save(refreshTokenEntity);

    return SignUpResponseDto.ok(accessToken, refreshTokenCookie, jwtProperties.getAccessTokenExpirationTime());
  }

  @Override
  @Transactional
  public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
    String email = dto.getEmail();
    Optional<User> optionalUser = userRepository.findByIsDeletedFalseAndEmail(email);
    if (optionalUser.isEmpty()) return ResponseDto.signInFailed();
    User user = optionalUser.get();

    String inputPassword = dto.getPassword();
    String storedPassword = user.getPassword();
    boolean isPasswordValid = passwordEncoder.matches(inputPassword, storedPassword);
    if (!isPasswordValid) return ResponseDto.signInFailed();
    if (user.getIsSuspended()) return ResponseDto.suspendedUser();

    refreshTokenRepository.deleteByUser(user);
    refreshTokenRepository.flush();
    String accessToken = jwtUtil.generateAccessToken(email);
    String refreshToken = jwtUtil.generateRefreshToken(email);
    String refreshTokenCookie = buildRefreshTokenCookie(refreshToken);

    RefreshToken refreshTokenEntity =
        new RefreshToken(user, jwtUtil.generateTokenHash(refreshToken), jwtUtil.getExpirationFromToken(refreshToken));
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
    Optional<RefreshToken> optionalRefreshTokenEntity = refreshTokenRepository.findByToken(refreshTokenHash);
    if (optionalRefreshTokenEntity.isEmpty()) return ResponseDto.expiredToken();
    RefreshToken refreshTokenEntity = optionalRefreshTokenEntity.get();

    User user = refreshTokenEntity.getUser();
    refreshTokenRepository.delete(refreshTokenEntity);
    refreshTokenRepository.flush();
    String newAccessToken = jwtUtil.generateAccessToken(user.getEmail());
    String newRefreshToken = jwtUtil.generateRefreshToken(user.getEmail());
    String newRefreshTokenCookie = buildRefreshTokenCookie(newRefreshToken);

    RefreshToken newRefreshTokenEntity =
        new RefreshToken(user, jwtUtil.generateTokenHash(newRefreshToken), jwtUtil.getExpirationFromToken(newRefreshToken));
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
    return ResponseCookie.from("refreshToken", refreshToken)
                         .maxAge(jwtProperties.getRefreshTokenExpirationTime())
                         .path("/api/v1/auth")
                         .httpOnly(true)
                         .secure(true)
                         .build()
                         .toString();
  }

}
