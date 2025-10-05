package com.pio.raonback.service;

import com.pio.raonback.dto.response.location.GetLocationsResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface LocationService {

  ResponseEntity<? super GetLocationsResponseDto> getLocations(String keyword, Pageable pageable);

}
