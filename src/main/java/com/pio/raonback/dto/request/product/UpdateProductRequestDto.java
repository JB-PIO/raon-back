package com.pio.raonback.dto.request.product;

import com.pio.raonback.common.enums.Condition;
import com.pio.raonback.common.enums.TradeType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Getter
public class UpdateProductRequestDto {

  @NotNull(message = "카테고리를 선택해주세요.")
  private Long categoryId;

  @NotNull(message = "거래 희망지역을 선택해주세요.")
  private Long locationId;

  @NotBlank(message = "제목을 입력해주세요.")
  @Size(max = 100, message = "제목은 100자 이하로 입력해주세요.")
  private String title;

  @Size(max = 5000, message = "상품 설명은 5000자 이하로 작성해주세요.")
  private String description;

  @NotNull(message = "가격을 입력해주세요.")
  @Min(value = 0, message = "가격은 0원 이상으로 입력해주세요.")
  private Long price;

  @NotNull(message = "상품 상태를 선택해주세요.")
  private Condition condition;

  @NotNull(message = "거래 방식을 선택해주세요.")
  private TradeType tradeType;

  @NotEmpty(message = "한 개 이상의 이미지 주소를 입력해주세요.")
  private List<@NotBlank(message = "이미지 주소를 입력해주세요.") @URL(message = "올바른 URL 형식이 아닙니다.") String> imageUrls;

}
