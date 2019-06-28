package io.swagger.sample.resource;

import javax.ws.rs.core.Response;

public class DeviceResource {

    public Response getDeviceById() {
        return Response.ok().build();
    }

    public Response sendCommand() {
        return Response.ok().build();
    }
}
