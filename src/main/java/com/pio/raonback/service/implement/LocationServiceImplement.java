package com.pio.raonback.service.implement;

import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.location.GetLocationListResponseDto;
import com.pio.raonback.dto.response.location.GetSearchLocationListResponseDto;
import com.pio.raonback.entity.LocationEntity;
import com.pio.raonback.repository.LocationRepository;
import com.pio.raonback.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImplement implements LocationService {

  private final LocationRepository locationRepository;

  @Override
  public ResponseEntity<? super GetLocationListResponseDto> getLocationList(int page, int size) {
    Page<LocationEntity> locationEntitiesPage;
    try {
      Pageable pageable = PageRequest.of(page, size);
      locationEntitiesPage = locationRepository.findAll(pageable);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.serverError();
    }
    return GetLocationListResponseDto.ok(locationEntitiesPage);
  }

  @Override
  public ResponseEntity<? super GetSearchLocationListResponseDto> getSearchLocationList(String keyword, int page, int size) {
    Page<LocationEntity> locationEntitiesPage;
    try {
      Pageable pageable = PageRequest.of(page, size);
      locationEntitiesPage = locationRepository.findAllByAddressContains(keyword, pageable);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.serverError();
    }
    return GetSearchLocationListResponseDto.ok(locationEntitiesPage);
  }

}
