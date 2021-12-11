package com.backend.technical.dtos;

import com.backend.technical.enums.TemperatureUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Temperature {
    private TemperatureUnit unit;
    private String value;
}
