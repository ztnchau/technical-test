package com.backend.technical.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceData {
    private Long humidity;
    private Temperature temperature;
}
