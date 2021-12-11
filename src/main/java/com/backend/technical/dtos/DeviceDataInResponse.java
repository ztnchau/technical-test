package com.backend.technical.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceDataInResponse extends DeviceData {
    private String timestamp;
}
