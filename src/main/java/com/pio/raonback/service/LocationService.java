package com.pio.raonback.service;

import com.pio.raonback.dto.response.location.GetLocationListResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface LocationService {

  ResponseEntity<? super GetLocationListResponseDto> getLocations(String keyword, Pageable pageable);

}
