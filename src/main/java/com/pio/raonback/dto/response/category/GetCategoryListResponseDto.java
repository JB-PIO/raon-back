package com.pio.raonback.dto.response.category;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.object.CategoryListItem;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.Category;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetCategoryListResponseDto extends ResponseDto {

  private Data data;

  @Getter
  private static class Data {

    private List<CategoryListItem> categories;

    private Data(List<Category> parentCategories) {
      this.categories = CategoryListItem.fromParentCategories(parentCategories);
    }

  }

  private GetCategoryListResponseDto(List<Category> parentCategories) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.data = new Data(parentCategories);
  }

  public static ResponseEntity<GetCategoryListResponseDto> ok(List<Category> parentCategories) {
    GetCategoryListResponseDto responseBody = new GetCategoryListResponseDto(parentCategories);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
