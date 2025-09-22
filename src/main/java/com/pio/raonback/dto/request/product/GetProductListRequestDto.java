package com.pio.raonback.dto.request.product;

import com.pio.raonback.common.enums.Condition;
import com.pio.raonback.common.enums.TradeType;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetProductListRequestDto {

  private Long categoryId;

  private Long locationId;

  @Min(value = 0, message = "최소 가격은 0원 이상으로 입력해주세요.")
  private Long minPrice;

  @Min(value = 0, message = "최대 가격은 0원 이상으로 입력해주세요.")
  private Long maxPrice;

  private String keyword;

  private Condition condition;

  private TradeType tradeType;

}
