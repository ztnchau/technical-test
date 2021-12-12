package com.backend.technical.services.impl;

import com.backend.technical.domains.Device;
import com.backend.technical.domains.DeviceData;
import com.backend.technical.dtos.DeviceRequest;
import com.backend.technical.dtos.DeviceResponse;
import com.backend.technical.dtos.SearchCriteria;
import com.backend.technical.errors.ErrorItem;
import com.backend.technical.exceptions.CustomErrorException;
import com.backend.technical.mappers.DeviceMapper;
import com.backend.technical.repositories.DeviceDataRepository;
import com.backend.technical.repositories.DeviceRepository;
import com.backend.technical.services.DeviceService;
import com.backend.technical.utils.CommonUtils;
import com.backend.technical.validators.DeviceValidator;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.backend.technical.utils.CommonUtils.RESOURCE_ENTITY_NOT_FOUND_MSG;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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
            existedDevice.setLatitude(deviceEntity.getLatitude());
            existedDevice.setLongitude(deviceEntity.getLongitude());
            deviceRepository.save(existedDevice);
            // just 1 device data in POST request
            deviceDataRepository.save(deviceEntity.getDataList().get(0));
            return Optional.of(mapper.toResponse(deviceRepository.getById(existedDevice.getId())));
        }
        return Optional.of(mapper.toResponse(deviceRepository.save(deviceEntity)));
    }

    @Override
    public Optional<DeviceResponse> findByDeviceId(final String deviceId, final SearchCriteria criteria)
            throws CustomErrorException {
        final Device deviceEntity = deviceRepository.findByDeviceId(deviceId);
        if (deviceEntity == null) {
            throw new CustomErrorException(NOT_FOUND, RESOURCE_ENTITY_NOT_FOUND_MSG,
                    Collections.singletonList(new ErrorItem(deviceId, RESOURCE_ENTITY_NOT_FOUND_MSG)));
        }
        return (criteria != null && deviceEntity.getDataList() != null)
                ? doFilter(deviceEntity, criteria) : Optional.of(mapper.toResponse(deviceEntity));
    }

    private Optional<DeviceResponse> doFilter(final Device device, final SearchCriteria criteria) {
        final List<DeviceData> deviceDataListFiltered = device.getDataList().stream()
                .filter(item -> isBetweenDate(item.getTimestamp(), criteria.getFromDate(), criteria.getToDate()))
                .collect(Collectors.toList());
        device.setDataList(deviceDataListFiltered);
        return Optional.of(mapper.toResponse(device));
    }

    private boolean isBetweenDate(final Date timestamp, final Date from, final Date to) {
        return timestamp != null && timestamp.after(from) && timestamp.before(to);
    }
}
