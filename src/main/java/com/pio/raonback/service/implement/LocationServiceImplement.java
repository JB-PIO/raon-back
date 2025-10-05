package com.pio.raonback.service.implement;

import com.pio.raonback.dto.response.location.GetLocationListResponseDto;
import com.pio.raonback.entity.Location;
import com.pio.raonback.repository.LocationRepository;
import com.pio.raonback.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImplement implements LocationService {

  private final LocationRepository locationRepository;

  @Override
  public ResponseEntity<? super GetLocationListResponseDto> getLocations(String keyword, Pageable pageable) {
    Page<Location> locationPage;
    if (keyword != null) locationPage = locationRepository.findAllByAddressContains(keyword, pageable);
    else locationPage = locationRepository.findAll(pageable);
    return GetLocationListResponseDto.ok(locationPage);
  }

}
