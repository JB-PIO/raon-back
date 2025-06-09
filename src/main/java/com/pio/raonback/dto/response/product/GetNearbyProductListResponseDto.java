package com.pio.raonback.dto.response.product;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.object.ProductListItem;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.ProductDetailViewEntity;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetNearbyProductListResponseDto extends ResponseDto {

  private List<ProductListItem> nearbyProductList;
  private int currentPage;
  private int totalPages;
  private long totalElements;

  private GetNearbyProductListResponseDto(Page<ProductDetailViewEntity> productDetailViewEntitiesPage) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.nearbyProductList = ProductListItem.copyList(productDetailViewEntitiesPage);
    this.currentPage = productDetailViewEntitiesPage.getNumber();
    this.totalPages = productDetailViewEntitiesPage.getTotalPages();
    this.totalElements = productDetailViewEntitiesPage.getTotalElements();
  }

  public static ResponseEntity<GetNearbyProductListResponseDto> ok(Page<ProductDetailViewEntity> productDetailViewEntitiesPage) {
    GetNearbyProductListResponseDto responseBody = new GetNearbyProductListResponseDto(productDetailViewEntitiesPage);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> locationNotFound() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.LOCATION_NOT_FOUND, ResponseMessage.LOCATION_NOT_FOUND);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
  }

}
