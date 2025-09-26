package com.pio.raonback.dto.external.response;

import com.pio.raonback.common.enums.RiskLevel;
import lombok.Getter;

@Getter
public class DetectFraudExternalResponseDto {

  private RiskLevel result;
  private String message;

}
