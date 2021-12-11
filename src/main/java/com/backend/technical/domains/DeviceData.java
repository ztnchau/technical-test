package com.backend.technical.domains;

import com.backend.technical.enums.TemperatureUnit;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = DeviceData.TABLE_NAME)
public class DeviceData implements Serializable {

    private static final long serialVersionUID = 11L;

    public static final String TABLE_NAME = "tbl_devicedata";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long humidity;

    @Column(name = "temperature_unit")
    @Enumerated(EnumType.STRING)
    private TemperatureUnit temperatureUnit;

    @Column(name = "temperature_value")
    private String temperatureValue;

    private Date timestamp;

    @Column(name = "device_id", nullable = false)
    private String deviceId;
}
