package com.backend.technical.services;

import com.backend.technical.dtos.DeviceRequest;
import com.backend.technical.dtos.DeviceResponse;

import com.backend.technical.exceptions.CustomErrorException;
import java.util.Map;
import java.util.Optional;

public interface DeviceService {

    Optional<DeviceResponse> save(DeviceRequest deviceRequest) throws CustomErrorException;

    Optional<DeviceResponse> findByDeviceId(String deviceId, Map<String, String> queryParams) throws CustomErrorException;

}
