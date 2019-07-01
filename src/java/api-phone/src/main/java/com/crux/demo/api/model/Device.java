package com.crux.demo.api.model;

import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Device")
public class Device {
    private Long idDevice;
    private String status;

    @XmlElement(name = "id")
    public Long getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(Long idDevice) {
        this.idDevice = idDevice;
    }

    @XmlElement(name = "status")
    @ApiModelProperty(value = "Device Status", allowableValues = "online, offline")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
