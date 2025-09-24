package com.pio.raonback.service.implement;

import com.pio.raonback.dto.external.request.DetectFraudExternalRequestDto;
import com.pio.raonback.dto.external.response.DetectFraudExternalResponseDto;
import com.pio.raonback.entity.Message;
import com.pio.raonback.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiServiceImplement implements AiService {

  private final RestClient aiRestClient;

  @Override
  public DetectFraudExternalResponseDto analyzeMessages(Long requesterId, List<Message> messages) {
    DetectFraudExternalRequestDto requestBody = new DetectFraudExternalRequestDto(requesterId, messages);
    DetectFraudExternalResponseDto responseBody =
        aiRestClient.post()
                    .uri("/check-fraud")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requestBody)
                    .retrieve()
                    .body(DetectFraudExternalResponseDto.class);
    return responseBody;
  }

}
