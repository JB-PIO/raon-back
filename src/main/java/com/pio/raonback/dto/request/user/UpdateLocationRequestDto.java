package com.pio.raonback.dto.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateLocationRequestDto {

  @NotNull(message = "지역을 선택해주세요.")
  private Long locationId;

}
