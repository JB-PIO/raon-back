package com.pio.raonback.controller;

import com.pio.raonback.dto.response.location.GetLocationsResponseDto;
import com.pio.raonback.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController {

  private final LocationService locationService;

  @GetMapping("")
  public ResponseEntity<? super GetLocationsResponseDto> getLocations(@RequestParam(required = false) String keyword,
                                                                      @PageableDefault(size = 50) Pageable pageable) {
    return locationService.getLocations(keyword, pageable);
  }

}
