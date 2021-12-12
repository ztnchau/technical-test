package com.backend.technical.services.impl;

import com.backend.technical.domains.Device;
import com.backend.technical.domains.DeviceData;
import com.backend.technical.dtos.DeviceDataInRequest;
import com.backend.technical.dtos.DeviceRequest;
import com.backend.technical.dtos.DeviceResponse;
import com.backend.technical.dtos.Temperature;
import com.backend.technical.enums.TemperatureUnit;
import com.backend.technical.mappers.DeviceMapper;
import com.backend.technical.repositories.DeviceDataRepository;
import com.backend.technical.repositories.DeviceRepository;
import com.backend.technical.validators.DeviceValidator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DeviceServiceImplTest {

    private static final String DEVICE_ID = "deviceId";

    private static final Double LATITUDE = 12.3;

    private static final Double LONGITUDE = -123.2312;

    private static final Long UIDPK = 1234L;

    private static final Long HUMIDITY = 123L;

    private static final String TEMPERATURE_VALUE = " 23.4";

    @InjectMocks
    private DeviceServiceImpl deviceService;

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private DeviceDataRepository deviceDataRepository;

    @Mock
    private DeviceMapper deviceMapper;

    @Mock
    private DeviceResponse deviceResponse;

    @Mock
    private Device deviceEntity;

    @Mock
    private DeviceValidator deviceValidator;

    @Mock
    private DeviceData deviceData;

    @Test
    public void testSaveNewDeviceSuccess() {
        final DeviceRequest deviceRequest = initDeviceRequest();
        when(deviceMapper.toEntity(any())).thenReturn(deviceEntity);
        when(deviceRepository.findByDeviceId(any())).thenReturn(null);
        when(deviceMapper.toResponse(any())).thenReturn(deviceResponse);

        deviceService.save(deviceRequest);

        verify(deviceDataRepository, times(0)).save(any());
        verify(deviceRepository, times(1)).save(any());

    }

    @Test
    public void testSaveExistedDeviceSuccess() {
        final DeviceRequest deviceRequest = initDeviceRequest();
        when(deviceMapper.toEntity(any())).thenReturn(deviceEntity);
        when(deviceRepository.findByDeviceId(any())).thenReturn(deviceEntity);
        when(deviceEntity.getDataList()).thenReturn(Collections.singletonList(deviceData));

        when(deviceMapper.toResponse(any())).thenReturn(deviceResponse);

        deviceService.save(deviceRequest);

        verify(deviceDataRepository, times(1)).save(any());
        verify(deviceRepository, times(1)).save(any());
    }

    @Test
    public void testGetDeviceByValidDeviceId() {
    }

    @Test
    public void testGetDeviceByInvalidDeviceId() {
    }

    @Test
    public void testGetDeviceByValidDeviceIdAndFilterTime() {
    }


    private Device initDeviceEntityResponse() {
        final Device device = new Device();
        device.setDeviceId(DEVICE_ID);
        device.setLongitude(LATITUDE);
        device.setLongitude(LONGITUDE);
        device.setId(UIDPK);
        // 5 items
        device.setDataList(initDeviceDataList());
        return device;
    }

    private List<DeviceData> initDeviceDataList() {
        final List<DeviceData> deviceDataList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            deviceDataList.add(initDeviceData((long) i, createDateWithIncreaseSecond(i)));
        }
        return deviceDataList;
    }

    private DeviceData initDeviceData(final Long id, final Date timestamp) {
        final DeviceData deviceData = new DeviceData();
        deviceData.setId(id);
        deviceData.setHumidity(HUMIDITY);
        deviceData.setTemperatureUnit(TemperatureUnit.C);
        deviceData.setTemperatureValue(TEMPERATURE_VALUE);
        deviceData.setTimestamp(timestamp);
        return deviceData;
    }

    private Date createDateWithIncreaseSecond(final int second) {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2021);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DATE, 12);
        cal.set(Calendar.HOUR_OF_DAY, 11);
        cal.set(Calendar.MINUTE, 11);
        cal.set(Calendar.SECOND, second);

        // 2021-12-12 11:11:i
        return cal.getTime();
    }

    private DeviceRequest initDeviceRequest() {
        final DeviceRequest deviceRequest = new DeviceRequest();
        deviceRequest.setDeviceId(DEVICE_ID);
        deviceRequest.setLatitude(LATITUDE);
        deviceRequest.setLongitude(LONGITUDE);
        final DeviceDataInRequest deviceDataInRequest = new DeviceDataInRequest();
        deviceDataInRequest.setHumidity(HUMIDITY);
        deviceDataInRequest.setTemperature(new Temperature(TemperatureUnit.C, TEMPERATURE_VALUE));
        deviceRequest.setData(deviceDataInRequest);
        return deviceRequest;
    }
 }
