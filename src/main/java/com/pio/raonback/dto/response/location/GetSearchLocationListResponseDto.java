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
public class GetSearchLocationListResponseDto extends ResponseDto {

  private List<LocationListItem> searchLocationList;
  private int currentPage;
  private int totalPages;
  private long totalElements;

  private GetSearchLocationListResponseDto(Page<LocationEntity> locationEntitiesPage) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.searchLocationList = LocationListItem.copyList(locationEntitiesPage);
    this.currentPage = locationEntitiesPage.getNumber();
    this.totalPages = locationEntitiesPage.getTotalPages();
    this.totalElements = locationEntitiesPage.getTotalElements();
  }

  public static ResponseEntity<GetSearchLocationListResponseDto> success(Page<LocationEntity> locationEntitiesPage) {
    GetSearchLocationListResponseDto result = new GetSearchLocationListResponseDto(locationEntitiesPage);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
