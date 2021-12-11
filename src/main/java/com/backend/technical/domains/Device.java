package com.backend.technical.domains;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = Device.TABLE_NAME)
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "tbl_device";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id", nullable = false, unique = true)
    private String deviceId;

    private Double latitude;

    private Double longitude;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "device_id", referencedColumnName = "device_id")
    private List<DeviceData> dataList;
}
