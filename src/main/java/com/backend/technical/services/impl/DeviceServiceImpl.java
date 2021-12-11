package com.backend.technical.services.impl;

import com.backend.technical.domains.Device;
import com.backend.technical.dtos.DeviceRequest;
import com.backend.technical.dtos.DeviceResponse;
import com.backend.technical.exceptions.CustomErrorException;
import com.backend.technical.mappers.DeviceMapper;
import com.backend.technical.repositories.DeviceDataRepository;
import com.backend.technical.repositories.DeviceRepository;
import com.backend.technical.services.DeviceService;
import com.backend.technical.validators.DeviceValidator;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceDataRepository deviceDataRepository;

    @Autowired
    private DeviceMapper mapper;

    @Autowired
    private DeviceValidator validator;

    @Override
    public Optional<DeviceResponse> save(final DeviceRequest deviceRequest) throws CustomErrorException {
        validator.validateRequest(deviceRequest);
        final Device deviceEntity = mapper.toEntity(deviceRequest);
        final Device existedDevice = deviceRepository.findByDeviceId(deviceEntity.getDeviceId());
        if (existedDevice != null) {
            // just 1 device data in POST request
            deviceDataRepository.save(deviceEntity.getDataList().get(0));
            return Optional.of(mapper.toResponse(deviceRepository.getById(existedDevice.getId())));
        }
        return Optional.of(mapper.toResponse(deviceRepository.save(deviceEntity)));
    }

    @Override
    public Optional<DeviceResponse> findByDeviceId(final String deviceId, final Map<String, String> queryParams)
            throws CustomErrorException {
        if (queryParams.isEmpty()) {
            return Optional.of(mapper.toResponse(deviceRepository.findByDeviceId(deviceId)));
        }
        throw new CustomErrorException("No such logic handled.");
    }
}
