package com.backend.technical.validators;

import com.backend.technical.dtos.DeviceDataInRequest;
import com.backend.technical.dtos.DeviceRequest;
import com.backend.technical.exceptions.CustomErrorException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DeviceValidatorTest {

    private static final String DEVICE_ID = "deviceId";
    private static final String EMPTY = "";

    @InjectMocks
    private DeviceValidator deviceValidator;

    @Mock
    private DeviceRequest request;

    @Mock
    private DeviceDataInRequest deviceData;

    @Test
    public void testValidDeviceInputRequest() {
        when(request.getDeviceId()).thenReturn(DEVICE_ID);
        when(request.getData()).thenReturn(deviceData);
        assertDoesNotThrow(() -> deviceValidator.validateRequest(request));

    }

    @Test
    public void testEmptyDeviceIdDeviceInputRequest() {
        when(request.getDeviceId()).thenReturn(EMPTY);
        assertThrows(CustomErrorException.class, () -> deviceValidator.validateRequest(request));
    }

    @Test
    public void testNullDeviceIdDeviceInputRequest() {
        when(request.getDeviceId()).thenReturn(null);
        assertThrows(CustomErrorException.class, () -> deviceValidator.validateRequest(request));
    }

    @Test
    public void testNullDeviceDataDeviceInputRequest() {
        when(request.getDeviceId()).thenReturn(DEVICE_ID);
        when(request.getData()).thenReturn(null);
        assertThrows(CustomErrorException.class, () -> deviceValidator.validateRequest(request));
    }
}
