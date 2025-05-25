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
public class GetProductListResponseDto extends ResponseDto {

  private List<ProductListItem> productList;
  private int currentPage;
  private int totalPages;
  private long totalElements;

  private GetProductListResponseDto(Page<ProductDetailViewEntity> productDetailViewEntitiesPage) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.productList = ProductListItem.copyList(productDetailViewEntitiesPage);
    this.currentPage = productDetailViewEntitiesPage.getNumber();
    this.totalPages = productDetailViewEntitiesPage.getTotalPages();
    this.totalElements = productDetailViewEntitiesPage.getTotalElements();
  }

  public static ResponseEntity<GetProductListResponseDto> ok(Page<ProductDetailViewEntity> productDetailViewEntitiesPage) {
    GetProductListResponseDto responseBody = new GetProductListResponseDto(productDetailViewEntitiesPage);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
