package com.pio.raonback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "location")
@Table(name = "location")
public class LocationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long locationId;
  private Long code;
  private String name;
  private String address;
  private Point coordinates;

}
