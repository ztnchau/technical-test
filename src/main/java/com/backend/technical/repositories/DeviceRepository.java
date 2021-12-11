package com.backend.technical.repositories;

import com.backend.technical.domains.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query("SELECT d FROM Device d WHERE d.deviceId=?1")
    Device findByDeviceId(String deviceId);

}
