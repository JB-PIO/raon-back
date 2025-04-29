package com.pio.raonback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "report")
@Table(name = "report")
public class ReportEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reportId;
  private Long reporterId;
  private Long productId;
  private Long reportedUserId;
  private String reason;
  private String createdAt;
  
}
