package com.backend.technical.mappers;

import com.backend.technical.domains.Device;
import com.backend.technical.domains.DeviceData;
import com.backend.technical.dtos.DeviceDataInRequest;
import com.backend.technical.dtos.DeviceDataInResponse;
import com.backend.technical.dtos.DeviceRequest;
import com.backend.technical.dtos.DeviceResponse;
import com.backend.technical.dtos.Temperature;
import com.backend.technical.utils.CommonUtils;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

import static com.backend.technical.utils.CommonUtils.TIMESTAMP_PATTERN;
import static com.backend.technical.utils.CommonUtils.resourceNotFoundException;
import static com.backend.technical.utils.CommonUtils.now;

@Component
public class DeviceMapper {

    public Device toEntity(final DeviceRequest deviceRequest) {
        if (deviceRequest == null) {
            throw resourceNotFoundException();
        }
        final Device entity = new Device();
        entity.setDeviceId(deviceRequest.getDeviceId());
        entity.setLatitude(deviceRequest.getLatitude());
        entity.setLongitude(deviceRequest.getLongitude());
        entity.setDataList(resolveDataListEntity(deviceRequest.getData(), deviceRequest.getDeviceId()));
        return entity;
    }


    public DeviceResponse toResponse(final Device entity) {
        if (entity == null) {
            throw resourceNotFoundException();
        }
        final DeviceResponse response = new DeviceResponse();
        response.setDeviceId(entity.getDeviceId());
        response.setLatitude(entity.getLatitude());
        response.setLongitude(entity.getLongitude());
        response.setDataList(resolveDataListResponse(entity.getDataList()));
        return response;
    }

    private List<DeviceDataInResponse> resolveDataListResponse(final List<DeviceData> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            throw resourceNotFoundException();
        }
        return dataList.stream().map(this::transformDeviceDataResponse).collect(Collectors.toList());
    }

    private DeviceDataInResponse transformDeviceDataResponse(final DeviceData deviceData) {
        final DeviceDataInResponse deviceDataInResponse = new DeviceDataInResponse();
        deviceDataInResponse.setHumidity(deviceData.getHumidity());
        deviceDataInResponse.setTimestamp(CommonUtils.dateToString(deviceData.getTimestamp(), TIMESTAMP_PATTERN));
        deviceDataInResponse.setTemperature(new Temperature(deviceData.getTemperatureUnit(),
                deviceData.getTemperatureValue()));
        return deviceDataInResponse;
    }

    private List<DeviceData> resolveDataListEntity(final DeviceDataInRequest deviceDataInRequest, final String deviceId) {
        final DeviceData deviceDataEntity = new DeviceData();
        deviceDataEntity.setHumidity(deviceDataInRequest.getHumidity());
        deviceDataEntity.setTimestamp(now());
        deviceDataEntity.setDeviceId(deviceId);
        if (deviceDataInRequest.getTemperature() != null) {
            deviceDataEntity.setTemperatureUnit(deviceDataInRequest.getTemperature().getUnit());
            deviceDataEntity.setTemperatureValue(deviceDataInRequest.getTemperature().getValue());
        }
        return Collections.singletonList(deviceDataEntity);
    }

}
