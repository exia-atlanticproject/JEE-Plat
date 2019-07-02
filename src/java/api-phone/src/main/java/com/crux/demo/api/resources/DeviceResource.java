package com.crux.demo.api.resources;

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

@Path("/device")
@Api(value = "/device", tags = "Device")
@Produces({MediaType.APPLICATION_JSON})
public class DeviceResource {
//    private static final BlockingQueue<AsyncResponse> suspended =
//            new ArrayBlockingQueue<AsyncResponse>(5);
//    static {
//        connector = BrokerConnector.getInstance();
//    }

    @GET
    @Path("/")
    @ApiOperation(value = "Retrieve device status", response = String.class)
    @Produces(MediaType.APPLICATION_JSON)
    public void getDevices(final @Suspended AsyncResponse ar) {

        String message = BrokerConnector.createMessage("GetDevices", new JSONObject());
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
    @Path("/user/{idUser}")
    @ApiOperation(value = "Retrieve device status", response = String.class)
    @Produces(MediaType.APPLICATION_JSON)
    public void getDevices(@ApiParam(value = "User id", required = true)@PathParam("idUser") Long idUser,
                           final @Suspended AsyncResponse ar) {

        String message = BrokerConnector.createMessage("GetUserDevices", new JSONObject().put("id", idUser));
        try {
            BrokerConnector.getConnector().emit(BrokerConnector.DataController, message, m -> {
                ar.resume(Response.ok().entity(m.getPayload().toString()).build());
            });
        } catch (JMSException e) {
            e.printStackTrace();
            ar.resume(Response.status(500).build());
        }

    }

//    private void lol(MessageModel message) {
//        System.out.println(message);
//    }


    @POST
    @Path("/associate")
    @ApiOperation(value = "Send a command to a device", response = String.class)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void linkUserDevice(
            @ApiParam(value = "Device id", required = true)@FormParam("idDevice") String idDevice,
            @ApiParam(value = "User id", required = true)@FormParam("idUser") String idUser,
            @Suspended final AsyncResponse ar
    ) {
        String message = BrokerConnector.createMessage("LinkDevUser",
                new JSONObject().put("userId", idUser).put("deviceId", idDevice));
        try {
            BrokerConnector.getConnector().emit(BrokerConnector.DataController, message, m -> {
                ar.resume(Response.ok().entity(m.getPayload().toString()).build());
            });
        } catch (JMSException e) {
            e.printStackTrace();
            ar.resume(Response.status(500).build());
        }

    }

    @POST
    @Path("/command/{idDevice}")
    @ApiOperation(value = "Send a command to a device", response = String.class)
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendCommand(
//            @ApiParam(value = "Device id", required = true)@PathParam("idDevice") Long idDevice
            @Suspended final AsyncResponse ar
    ) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ar.resume(Response.ok().entity("coucou").build());
            }
        }).start();

//        final AsyncResponse ar = suspended.take();
//        ar.resume("lol"); // resumes the processing of one GET request
//        return "Message sent";
//
//        JSONObject command = new JSONObject();
//        command.put("idDevice", idDevice);
//        command.put("idCommand", 1);
//
//        try {
//            connector.emit("Data-Controller", command.toString(), new Consumer<MessageModel>() {
//                @Override
//                public void accept(MessageModel messageModel) {
//                    System.out.println("coucou");
//                }
//            });
////            return Response.ok().entity("Command successfully sent").build();
//        } catch (JMSException e) {
//            e.printStackTrace();
////            return Response.status(500).entity("Error").build();
//        }
//        return Response.ok().build();
    }
}
