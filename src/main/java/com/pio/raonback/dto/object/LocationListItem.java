package com.pio.raonback.dto.object;

import com.pio.raonback.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocationListItem {

  private Long locationId;
  private Long code;
  private String address;

  public LocationListItem(Location location) {
    this.locationId = location.getLocationId();
    this.code = location.getCode();
    this.address = location.getAddress();
  }

  public static List<LocationListItem> copyList(Page<Location> locationPage) {
    List<LocationListItem> list = new ArrayList<>();
    for (Location location : locationPage.getContent()) {
      LocationListItem locationListItem = new LocationListItem(location);
      list.add(locationListItem);
    }
    return list;
  }

}
