package com.pio.raonback.dto.object;

import com.pio.raonback.entity.LocationEntity;
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

  public LocationListItem(LocationEntity locationEntity) {
    this.locationId = locationEntity.getLocationId();
    this.code = locationEntity.getCode();
    this.address = locationEntity.getAddress();
  }

  public static List<LocationListItem> copyList(Page<LocationEntity> locationEntitiesPage) {
    List<LocationListItem> list = new ArrayList<>();
    for (LocationEntity locationEntity : locationEntitiesPage.getContent()) {
      LocationListItem locationListItem = new LocationListItem(locationEntity);
      list.add(locationListItem);
    }
    return list;
  }

}
