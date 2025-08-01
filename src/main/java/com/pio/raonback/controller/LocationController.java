package com.pio.raonback.controller;

import com.pio.raonback.dto.response.location.GetLocationListResponseDto;
import com.pio.raonback.dto.response.location.GetSearchLocationListResponseDto;
import com.pio.raonback.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController {

  private final LocationService locationService;

  @GetMapping("")
  public ResponseEntity<? super GetLocationListResponseDto> getLocationList(@PageableDefault(size = 50) Pageable pageable) {
    return locationService.getLocationList(pageable);
  }

  @GetMapping("/search/{keyword}")
  public ResponseEntity<? super GetSearchLocationListResponseDto> getSearchLocationList(@PathVariable("keyword") String keyword,
                                                                                        @PageableDefault(size = 50) Pageable pageable) {
    return locationService.getSearchLocationList(keyword, pageable);
  }

}
