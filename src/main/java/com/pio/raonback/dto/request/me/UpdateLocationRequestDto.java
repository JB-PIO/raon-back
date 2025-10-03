package com.pio.raonback.dto.request.me;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateLocationRequestDto {

  @NotNull(message = "지역을 선택해주세요.")
  private Long locationId;

}
