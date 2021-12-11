package com.backend.technical.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceRequest {
    private String deviceId;
    private Double latitude;
    private Double longitude;
    private DeviceDataInRequest data;
}
