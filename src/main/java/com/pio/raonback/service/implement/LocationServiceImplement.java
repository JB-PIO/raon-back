package com.pio.raonback.service.implement;

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
    Pageable pageable = PageRequest.of(page, size);
    Page<LocationEntity> locationEntitiesPage = locationRepository.findAll(pageable);
    return GetLocationListResponseDto.ok(locationEntitiesPage);
  }

  @Override
  public ResponseEntity<? super GetSearchLocationListResponseDto> getSearchLocationList(String keyword, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<LocationEntity> locationEntitiesPage = locationRepository.findAllByAddressContains(keyword, pageable);
    return GetSearchLocationListResponseDto.ok(locationEntitiesPage);
  }

}
