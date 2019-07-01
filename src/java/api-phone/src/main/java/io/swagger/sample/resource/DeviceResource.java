package io.swagger.sample.resource;

import Broker.Connector;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.sample.model.Device;
import io.swagger.sample.util.BrokerConnector;
import model.MessageModel;
import org.json.JSONObject;

import javax.jms.JMSException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/device")
@Api(value = "/device", tags = "Device")
@Produces({MediaType.APPLICATION_JSON})
public class DeviceResource {

    private static Connector connector;

    static {
        connector = BrokerConnector.getInstance();
    }

    @GET
    @Path("/{idDevice}")
    @ApiOperation(value = "Retrieve device status", response = Device.class)
    public Response getDeviceById(
            @ApiParam(value = "Device id", required = true)@PathParam("idDevice") Long idDevice) {
        return Response.ok().build();
    }

    private void lol(MessageModel message) {
        System.out.println(message);
    }

    @POST
    @Path("/command/{idDevice}")
    @ApiOperation(value = "Send a command to a device", response = String.class)
    public Response sendCommand(
            @ApiParam(value = "Device id", required = true)@PathParam("idDevice") Long idDevice) {
        JSONObject command = new JSONObject();

        command.put("idDevice", idDevice);
        command.put("idCommand", 1);

        try {
            connector.emit("Data-Controller", command.toString(), this::lol);
            return Response.ok().entity("Command successfully sent").build();
        } catch (JMSException e) {
            e.printStackTrace();
            return Response.status(500).entity("Error").build();
        }
    }
}
