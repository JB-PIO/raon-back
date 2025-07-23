package com.pio.raonback.dto.response.location;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.object.LocationListItem;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.Location;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetSearchLocationListResponseDto extends ResponseDto {

  private List<LocationListItem> searchLocationList;
  private int currentPage;
  private int totalPages;
  private long totalElements;

  private GetSearchLocationListResponseDto(Page<Location> locationPage) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.searchLocationList = LocationListItem.copyList(locationPage);
    this.currentPage = locationPage.getNumber();
    this.totalPages = locationPage.getTotalPages();
    this.totalElements = locationPage.getTotalElements();
  }

  public static ResponseEntity<GetSearchLocationListResponseDto> ok(Page<Location> locationPage) {
    GetSearchLocationListResponseDto responseBody = new GetSearchLocationListResponseDto(locationPage);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
