package com.pio.raonback.service;

import com.pio.raonback.dto.response.location.GetLocationListResponseDto;
import com.pio.raonback.dto.response.location.GetSearchLocationListResponseDto;
import org.springframework.http.ResponseEntity;

public interface LocationService {

  ResponseEntity<? super GetLocationListResponseDto> getLocationList(int page, int size);

  ResponseEntity<? super GetSearchLocationListResponseDto> getSearchLocationList(String keyword, int page, int size);

}
