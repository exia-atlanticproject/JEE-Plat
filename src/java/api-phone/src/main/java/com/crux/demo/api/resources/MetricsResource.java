package com.crux.demo.api.resources;

import com.crux.demo.api.model.Metrics;
import com.crux.demo.api.util.BrokerConnector;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.json.JSONObject;

import javax.jms.JMSException;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/metrics")
@Api(value = "/metrics", tags = "Metrics")
@Produces({MediaType.APPLICATION_JSON})
public class MetricsResource {

    @GET
    @Path("/last/{idDevice}")
    @ApiOperation(value = "Retrieve raw metrics by ID device",
            notes = "Return a dataset of raw metrics",
            response = Metrics.class)
    public void getLastMetrics(
            @ApiParam(value = "device id", required = true)@PathParam("idDevice") Long idDevice,
            final @Suspended AsyncResponse ar) {

        String message = BrokerConnector.createMessage("GetLastDeviceMetric", new JSONObject().put("id", idDevice));
        try {
            BrokerConnector.getConnector().emit(BrokerConnector.DataController, message, m -> {
                ar.resume(Response.ok().entity(m.getPayload().toString()).build());
            });
        } catch (JMSException e) {
            e.printStackTrace();
            ar.resume(Response.status(500).build());
        }

    }

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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
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
