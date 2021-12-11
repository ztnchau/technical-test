package com.backend.technical.repositories;

import com.backend.technical.domains.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceDataRepository extends JpaRepository<DeviceData, Long> {
}
