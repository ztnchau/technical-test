package com.backend.technical.controllers;

import com.backend.technical.dtos.DeviceDataInRequest;
import com.backend.technical.dtos.DeviceRequest;
import com.backend.technical.dtos.Temperature;
import com.backend.technical.enums.TemperatureUnit;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.backend.technical.utils.CommonUtils.DATA_BODY_KEY;
import static com.backend.technical.utils.CommonUtils.DEVICE_ID_KEY;
import static com.backend.technical.utils.CommonUtils.MANDATORY_BODY_PATTERN_MSG;
import static com.backend.technical.utils.CommonUtils.MANDATORY_PARAMETER_PATTERN_MSG;
import static com.backend.technical.utils.CommonUtils.RESOURCE_ENTITY_NOT_FOUND_MSG;
import static com.backend.technical.utils.CommonUtils.toJSON;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DeviceControllerTest {

    private static final String DEVICE_ID = "device-id-test-001";

    private static final String UNIT_DEVICE_ID_PREFIX = "device-id-";

    private static final Double LATITUDE = 41.25;

    private static final Double LONGITUDE = -120.9762;

    private static final Long HUMIDITY = 123L;

    private static final TemperatureUnit TEMPERATURE_UNIT = TemperatureUnit.C;

    private static final String TEMPERATURE_VALUE = "24.5";

    private static final String FOLLOW_KEY = "follow";

    private static final String BAD_REQUEST_CODE_MESSAGE = "BAD_REQUEST";

    private static final String RESOURCE_NOT_FOUND_CODE_MESSAGE = "NOT_FOUND";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGivenValidRequestWithoutFollowHeader_PostDevice_ThenReturnStatus201AndNobody() throws Exception {
        mockMvc.perform(post("/devices")
                .content(toJSON(initRequestBody()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void testGivenValidRequestWithFollowHeader_WhenPostDevice_ThenReturnStatus200AndBody() throws Exception {
        final DeviceRequest request = initRequestBody();
        mockMvc.perform(post("/devices")
                .content(toJSON(initRequestBody()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(FOLLOW_KEY, "true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deviceId", is(request.getDeviceId())))
                .andExpect(jsonPath("$.latitude", is(request.getLatitude())))
                .andExpect(jsonPath("$.longitude", is(request.getLongitude())))
                .andExpect(jsonPath("$.data", hasSize(greaterThan(0))));
    }

    @Test
    public void testGivenRequestBodyNull_WhenPostDevice_ThenReturnStatus400AndDetailErrorMessage() throws Exception {
        mockMvc.perform(post("/devices")
                .content("{}")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGivenDeviceIdNull_WhenPostDevice_ThenReturnStatus400AndDetailErrorMessage() throws Exception {
        final DeviceRequest deviceRequest = initRequestBody();
        deviceRequest.setDeviceId(null);
        mockMvc.perform(post("/devices")
                .content(toJSON(deviceRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.status", is(BAD_REQUEST_CODE_MESSAGE)))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].key", is(DEVICE_ID_KEY)))
                .andExpect(jsonPath("$.errors[0].value",
                        is(String.format(MANDATORY_PARAMETER_PATTERN_MSG, deviceRequest.getDeviceId()))));
    }

    @Test
    public void testGivenDeviceIdBlank_WhenPostDevice_ThenReturnStatus400AndDetailErrorMessage() throws Exception {
        final DeviceRequest deviceRequest = initRequestBody();
        deviceRequest.setDeviceId("");
        mockMvc.perform(post("/devices")
                .content(toJSON(deviceRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.status", is(BAD_REQUEST_CODE_MESSAGE)))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].key", is(DEVICE_ID_KEY)))
                .andExpect(jsonPath("$.errors[0].value",
                        is(String.format(MANDATORY_PARAMETER_PATTERN_MSG, deviceRequest.getDeviceId()))));

    }

    @Test
    public void testGivenDeviceDataNull_WhenPostDevice_ThenReturnStatus400AndDetailErrorMessage() throws Exception {
        final DeviceRequest deviceRequest = initRequestBody();
        deviceRequest.setData(null);
        mockMvc.perform(post("/devices")
                .content(toJSON(deviceRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.status", is(BAD_REQUEST_CODE_MESSAGE)))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].key", is(DATA_BODY_KEY)))
                .andExpect(jsonPath("$.errors[0].value",
                        is(MANDATORY_BODY_PATTERN_MSG)));
    }

    @Test
    public void testGivenDeviceIdNullAndDeviceDataNull_WhenPostDevice_ThenReturnStatus400AndDetailErrorMessage() throws Exception {
        final DeviceRequest deviceRequest = initRequestBody();
        deviceRequest.setData(null);
        deviceRequest.setDeviceId(null);
        mockMvc.perform(post("/devices")
                .content(toJSON(deviceRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.status", is(BAD_REQUEST_CODE_MESSAGE)))
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[0].key", is(DEVICE_ID_KEY)))
                .andExpect(jsonPath("$.errors[0].value",
                        is(String.format(MANDATORY_PARAMETER_PATTERN_MSG, deviceRequest.getDeviceId()))))
                .andExpect(jsonPath("$.errors[1].key", is(DATA_BODY_KEY)))
                .andExpect(jsonPath("$.errors[1].value",
                        is(MANDATORY_BODY_PATTERN_MSG)));
    }

    @Test
    public void testGivenValidRequestAndPostOneDevice_WhenGetDevice_ThenReturnStatus200AndBodyHasOneDeviceData() throws Exception {
        final DeviceRequest deviceRequest = initRequestBody();
        final String unitDeviceId = UNIT_DEVICE_ID_PREFIX + UUID.randomUUID();
        deviceRequest.setDeviceId(unitDeviceId);

        // create item
        mockMvc.perform(post("/devices")
                .content(toJSON(deviceRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

        // getItem
        mockMvc.perform(get("/devices/" + unitDeviceId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deviceId", is(unitDeviceId)))
                .andExpect(jsonPath("$.data", hasSize(1)));
    }

    @Test
    public void testGivenValidRequestAndPostTwoDevice_WhenGetDevice_ThenReturnStatus200AndBodyHasTwoDeviceData() throws Exception {
        final DeviceRequest deviceRequest = initRequestBody();
        final String unitDeviceId = UNIT_DEVICE_ID_PREFIX + UUID.randomUUID();
        deviceRequest.setDeviceId(unitDeviceId);

        // create item
        mockMvc.perform(post("/devices")
                .content(toJSON(deviceRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/devices")
                .content(toJSON(deviceRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

        // getItem
        mockMvc.perform(get("/devices/" + unitDeviceId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deviceId", is(unitDeviceId)))
                .andExpect(jsonPath("$.data", hasSize(2)));
    }

    @Test
    public void testGivenInvalidDeviceParam_WhenGetDevice_ThenReturnStatus404AndDetailErrorMessage() throws Exception {
        final String unitDeviceId = UNIT_DEVICE_ID_PREFIX + UUID.randomUUID();

        // getItem
        mockMvc.perform(get("/devices/" + unitDeviceId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(404)))
                .andExpect(jsonPath("$.status", is(RESOURCE_NOT_FOUND_CODE_MESSAGE)))
                .andExpect(jsonPath("$.message", is(RESOURCE_ENTITY_NOT_FOUND_MSG)));
    }

    private static DeviceRequest initRequestBody() {
        final DeviceRequest deviceRequest = new DeviceRequest();
        deviceRequest.setDeviceId(DEVICE_ID);
        deviceRequest.setLatitude(LATITUDE);
        deviceRequest.setLongitude(LONGITUDE);
        final DeviceDataInRequest deviceDataInRequest = new DeviceDataInRequest();
        deviceDataInRequest.setHumidity(HUMIDITY);
        deviceDataInRequest.setTemperature(new Temperature(TEMPERATURE_UNIT, TEMPERATURE_VALUE));
        deviceRequest.setData(deviceDataInRequest);

        return deviceRequest;
    }
}
