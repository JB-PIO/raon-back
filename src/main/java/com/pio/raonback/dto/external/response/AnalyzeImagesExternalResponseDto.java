package com.pio.raonback.dto.external.response;

import com.pio.raonback.common.enums.RiskLevel;
import lombok.Getter;

import java.util.List;

@Getter
public class AnalyzeImagesExternalResponseDto {

  private List<ResultData> results;

  @Getter
  private static class ResultData {

    private Long imageId;
    private String imageUrl;
    private RiskLevel result;
    private Double maxScore;
    private List<String> similarImageUrls;

  }

}
