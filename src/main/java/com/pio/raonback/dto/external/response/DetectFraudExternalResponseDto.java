package com.pio.raonback.dto.external.response;

import com.pio.raonback.common.enums.FraudRiskLevel;
import lombok.Getter;

@Getter
public class DetectFraudExternalResponseDto {

  private FraudRiskLevel result;
  private String message;

}
