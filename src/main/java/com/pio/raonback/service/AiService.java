package com.pio.raonback.service;

import com.pio.raonback.dto.external.response.AnalyzeImagesExternalResponseDto;
import com.pio.raonback.dto.external.response.DetectFraudExternalResponseDto;
import com.pio.raonback.entity.Message;
import com.pio.raonback.entity.ProductImage;

import java.util.List;

public interface AiService {

  DetectFraudExternalResponseDto analyzeMessages(Long requesterId, List<Message> messages);

  AnalyzeImagesExternalResponseDto analyzeImages(List<ProductImage> productImages);

}
