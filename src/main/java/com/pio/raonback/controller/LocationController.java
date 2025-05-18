package com.pio.raonback.controller;

import com.pio.raonback.dto.response.location.GetLocationListResponseDto;
import com.pio.raonback.dto.response.location.GetSearchLocationListResponseDto;
import com.pio.raonback.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController {

  private final LocationService locationService;

  @GetMapping("")
  public ResponseEntity<? super GetLocationListResponseDto> getLocationList(@RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "100") int size) {
    ResponseEntity<? super GetLocationListResponseDto> response = locationService.getLocationList(page, size);
    return response;
  }

  @GetMapping("/search/{keyword}")
  public ResponseEntity<? super GetSearchLocationListResponseDto> getSearchLocationList(@PathVariable("keyword") String keyword,
                                                                                        @RequestParam(defaultValue = "0") int page,
                                                                                        @RequestParam(defaultValue = "100") int size) {
    ResponseEntity<? super GetSearchLocationListResponseDto> response = locationService.getSearchLocationList(keyword, page, size);
    return response;
  }

}
