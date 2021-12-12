package com.backend.technical.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceResponse {
    private String deviceId;
    private Double latitude;
    private Double longitude;
    @JsonProperty("data")
    private List<DeviceDataInResponse> dataList;
}
