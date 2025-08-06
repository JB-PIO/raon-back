package com.pio.raonback.dto.response.me;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.object.ProductListItem;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.Favorite;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetFavoriteListResponseDto extends ResponseDto {

  private Data data;

  @Getter
  private static class Data {

    private List<ProductListItem> productList;
    private int currentPage;
    private int totalPages;
    private long totalElements;

    private Data(Page<Favorite> favoritePage) {
      this.productList = ProductListItem.fromFavoritePage(favoritePage);
      this.currentPage = favoritePage.getNumber();
      this.totalPages = favoritePage.getTotalPages();
      this.totalElements = favoritePage.getTotalElements();
    }

  }

  private GetFavoriteListResponseDto(Page<Favorite> favoritePage) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.data = new Data(favoritePage);
  }

  public static ResponseEntity<GetFavoriteListResponseDto> ok(Page<Favorite> favoritePage) {
    GetFavoriteListResponseDto responseBody = new GetFavoriteListResponseDto(favoritePage);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
