package com.pio.raonback.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

  @Bean
  public RestClient aiRestClient(@Value("${ai.url}") String aiUrl) {
    return RestClient.builder()
                     .baseUrl(aiUrl)
                     .build();
  }

}
