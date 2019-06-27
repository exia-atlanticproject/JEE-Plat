package io.swagger.sample.resource;

import io.swagger.sample.util.Security;

import javax.ws.rs.core.Response;

public class DeviceResource {

    @Security
    public Response getDeviceById() {
        return Response.ok().build();
    }

    public Response sendCommand() {
        return Response.ok().build();
    }
}
