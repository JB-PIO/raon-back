package com.pio.raonback.service;

import com.pio.raonback.dto.external.response.DetectFraudExternalResponseDto;
import com.pio.raonback.entity.Message;

import java.util.List;

public interface AiService {

  DetectFraudExternalResponseDto analyzeMessages(Long requesterId, List<Message> messages);

}
