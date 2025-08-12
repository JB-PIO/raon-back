package com.pio.raonback.dto.request.product;

import com.pio.raonback.entity.enums.ProductStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateStatusRequestDto {

  @NotNull(message = "상품 상태를 선택해주세요.")
  private ProductStatus status;

}
