package com.pio.raonback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class RaonBackApplication {

  public static void main(String[] args) {
    SpringApplication.run(RaonBackApplication.class, args);
  }

}
