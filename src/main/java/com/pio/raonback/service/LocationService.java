package com.pio.raonback.service;

import com.pio.raonback.dto.response.location.GetLocationListResponseDto;
import com.pio.raonback.dto.response.location.GetSearchLocationListResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface LocationService {

  ResponseEntity<? super GetLocationListResponseDto> getLocationList(Pageable pageable);

  ResponseEntity<? super GetSearchLocationListResponseDto> getSearchLocationList(String keyword, Pageable pageable);

}
