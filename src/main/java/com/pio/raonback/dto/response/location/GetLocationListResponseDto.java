package com.pio.raonback.dto.response.location;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.object.LocationListItem;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.LocationEntity;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetLocationListResponseDto extends ResponseDto {

  private List<LocationListItem> locationList;
  private int currentPage;
  private int totalPages;
  private long totalElements;

  private GetLocationListResponseDto(Page<LocationEntity> locationEntitiesPage) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.locationList = LocationListItem.copyList(locationEntitiesPage);
    this.currentPage = locationEntitiesPage.getNumber();
    this.totalPages = locationEntitiesPage.getTotalPages();
    this.totalElements = locationEntitiesPage.getTotalElements();
  }

  public static ResponseEntity<GetLocationListResponseDto> ok(Page<LocationEntity> locationEntitiesPage) {
    GetLocationListResponseDto responseBody = new GetLocationListResponseDto(locationEntitiesPage);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
