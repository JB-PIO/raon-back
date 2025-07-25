package com.pio.raonback.dto.response.product;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.object.ProductListItem;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.Product;
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

  private GetNearbyProductListResponseDto(Page<Product> productPage) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.nearbyProductList = ProductListItem.copyList(productPage);
    this.currentPage = productPage.getNumber();
    this.totalPages = productPage.getTotalPages();
    this.totalElements = productPage.getTotalElements();
  }

  public static ResponseEntity<GetNearbyProductListResponseDto> ok(Page<Product> productPage) {
    GetNearbyProductListResponseDto responseBody = new GetNearbyProductListResponseDto(productPage);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
