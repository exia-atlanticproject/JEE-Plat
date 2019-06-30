package io.swagger.sample.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.sample.model.Metrics;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/metrics")
@Api(value = "/metrics", tags = "Metrics")
@Produces({MediaType.APPLICATION_JSON})
public class MetricsResource {

    @GET
    @Path("/{idDevice}")
    @ApiOperation(value = "Retrieve raw metrics by ID device",
    notes = "Return a dataset of raw metrics",
    response = Metrics.class)
    public Response getRawMetricsByDeviceId(@ApiParam(value = "device id", required = true)@PathParam("idDevice") Long idDevice) {
        return Response.ok().build();
    }

    @POST
    @Path("/job")
    @ApiOperation(value = "Request a new computation job",
            response = String.class)
    public Response requestNewComputation() {
        return Response.ok().build();
    }

    @GET
    @Path("/jobs")
    @ApiOperation(value = "Retrieve the jobs list", response = List.class)
    public Response getUserComputation() {
        return Response.ok().build();
    }

    @GET
    @Path("/computed/{idJob}")
    @ApiOperation(value = "Retrieve computed metrics by job ID", response = Metrics.class)
    public Response getComputedMetricsByJobId(@ApiParam(value = "job id", required = true)@PathParam("idJob") Long idJob) {
        return Response.ok().build();
    }
}
