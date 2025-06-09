package com.pio.raonback.repository;

import com.pio.raonback.entity.LocationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

  Page<LocationEntity> findAllByAddressContains(String keyword, Pageable pageable);

  @Query(value = "SELECT *, ST_Distance_Sphere(coordinates, (SELECT coordinates FROM location WHERE location_id = ?1)) AS distance " +
      "FROM location " +
      "WHERE ST_Distance_Sphere(coordinates, (SELECT coordinates FROM location WHERE location_id = ?1)) <= ?2 " +
      "ORDER BY distance",
      nativeQuery = true)
  List<LocationEntity> findAllByWithinRadius(Long locationId, Long radius);

}
