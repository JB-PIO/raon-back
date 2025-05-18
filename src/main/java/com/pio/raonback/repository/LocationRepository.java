package com.pio.raonback.repository;

import com.pio.raonback.entity.LocationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

  Page<LocationEntity> findAllByAddressContains(String keyword, Pageable pageable);

}
