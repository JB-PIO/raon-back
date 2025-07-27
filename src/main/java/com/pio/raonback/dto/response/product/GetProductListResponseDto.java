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
public class GetProductListResponseDto extends ResponseDto {

  private Data data;

  @Getter
  private static class Data {

    private List<ProductListItem> productList;
    private int currentPage;
    private int totalPages;
    private long totalElements;

    private Data(Page<Product> productPage) {
      this.productList = ProductListItem.copyList(productPage);
      this.currentPage = productPage.getNumber();
      this.totalPages = productPage.getTotalPages();
      this.totalElements = productPage.getTotalElements();
    }

  }

  private GetProductListResponseDto(Page<Product> productPage) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.data = new Data(productPage);
  }

  public static ResponseEntity<GetProductListResponseDto> ok(Page<Product> productPage) {
    GetProductListResponseDto responseBody = new GetProductListResponseDto(productPage);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
