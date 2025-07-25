package com.pio.raonback.dto.request.product;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PutFavoriteRequestDto {

  @NotNull(message = "찜 여부를 입력해주세요")
  private Boolean isFavorite;

}
