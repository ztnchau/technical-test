package com.backend.technical.validators;

import com.backend.technical.dtos.DeviceDataInRequest;
import com.backend.technical.dtos.DeviceRequest;
import com.backend.technical.exceptions.CustomErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeviceValidatorTest {

    private static final String DEVICE_ID = "deviceId";
    private static final String EMPTY = "";

    @InjectMocks
    private DeviceValidator deviceValidator;

    @Mock
    private DeviceRequest request;

    @Before
    public void setup() {
        when(request.getDeviceId()).thenReturn(DEVICE_ID);
        when(request.getData()).thenReturn(new DeviceDataInRequest());
    }

    @Test
    public void testValidDeviceInputRequest() {
        assertDoesNotThrow(() -> deviceValidator.validateRequest(request));

    }

    @Test
    public void testNullDeviceInputRequest() {
        assertThrows(CustomErrorException.class, () -> deviceValidator.validateRequest(null));
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
        when(request.getData()).thenReturn(null);
        assertThrows(CustomErrorException.class, () -> deviceValidator.validateRequest(request));
    }
}
