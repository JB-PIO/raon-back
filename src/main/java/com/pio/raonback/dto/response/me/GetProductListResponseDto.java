package com.pio.raonback.dto.response.me;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.object.ProductListItem;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.ProductDetail;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetProductListResponseDto extends ResponseDto {

  private Data data;

  @Getter
  private static class Data {

    private List<ProductListItem> productList;
    private int currentPage;
    private int totalPages;
    private long totalElements;

    private Data(Page<ProductDetail> productDetailPage) {
      this.productList = ProductListItem.fromProductDetailPage(productDetailPage);
      this.currentPage = productDetailPage.getNumber();
      this.totalPages = productDetailPage.getTotalPages();
      this.totalElements = productDetailPage.getTotalElements();
    }

  }

  private GetProductListResponseDto(Page<ProductDetail> productDetailPage) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.data = new Data(productDetailPage);
  }

  public static ResponseEntity<GetProductListResponseDto> ok(Page<ProductDetail> productDetailPage) {
    GetProductListResponseDto responseBody = new GetProductListResponseDto(productDetailPage);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
