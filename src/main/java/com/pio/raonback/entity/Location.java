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
public class Location {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "location_id", nullable = false)
  private Long locationId;

  @Column(name = "code", nullable = false, unique = true)
  private Long code;

  @Column(name = "name", nullable = false, length = 10)
  private String name;

  @Column(name = "address", nullable = false, length = 50)
  private String address;

  @Column(name = "coordinates", nullable = false, columnDefinition = "POINT SRID 4326")
  private Point coordinates;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Location)) return false;
    final Location location = (Location) obj;
    return location.getLocationId().equals(getLocationId());
  }

  @Override
  public int hashCode() {
    return getLocationId().hashCode();
  }

}
