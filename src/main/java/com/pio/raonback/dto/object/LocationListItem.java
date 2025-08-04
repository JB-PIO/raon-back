package com.pio.raonback.dto.object;

import com.pio.raonback.entity.Location;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LocationListItem {

  private Long locationId;
  private String address;

  public LocationListItem(Location location) {
    this.locationId = location.getLocationId();
    this.address = location.getAddress();
  }

  public static List<LocationListItem> fromLocationPage(Page<Location> locationPage) {
    List<LocationListItem> list = new ArrayList<>();
    for (Location location : locationPage.getContent()) {
      LocationListItem locationListItem = new LocationListItem(location);
      list.add(locationListItem);
    }
    return list;
  }

}
