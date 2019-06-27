package io.swagger.sample.resource;

import javax.ws.rs.core.Response;

public class MetricsResource {

    public Response getRawMetricsByDeviceId() {
        return Response.ok().build();
    }

    public Response requestNewComputation() {
        return Response.ok().build();
    }

    public Response getUserComputation() {
        return Response.ok().build();
    }

    public Response findComputedMetrics() {
        return Response.ok().build();
    }

    public Response findComputedMetricsByJobId() {
        return Response.ok().build();
    }
}
