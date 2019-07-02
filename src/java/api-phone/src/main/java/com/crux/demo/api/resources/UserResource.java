package com.crux.demo.api.resources;

import com.crux.demo.api.exception.NotFoundException;
import com.crux.demo.api.model.User;
import com.crux.demo.api.util.BrokerConnector;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.jms.JMSException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: aaron.stone
 * Date: 10/16/13
 * Time: 1:54 PM
 * To change this template use File | Settings | File Templates.
 */

@Path("/users")
@Api(value = "/users", description = "User operations")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger log = Logger.getLogger(UserResource.class);

    /**
     * Get all users in the system.
     *
     * @return
     * @throws Exception
     */
    @GET
    @Path("/")
    @ApiOperation(value = "Retrieve users", response = String.class)
    @Produces(MediaType.APPLICATION_JSON)
    public void getUsers(final @Suspended AsyncResponse ar) {

        String message = BrokerConnector.createMessage("GetUsers", new JSONObject());
        try {
            BrokerConnector.getConnector().emit(BrokerConnector.DataController, message, m -> {
                ar.resume(Response.ok().entity(m.getPayload().toString()).build());
            });
        } catch (JMSException e) {
            e.printStackTrace();
            ar.resume(Response.status(500).build());
        }

        return Response.ok().entity(User.getUsers()).build();
    }


    /**
     * Get a user by uniqueId.
     *
     * @return
     * @throws Exception
     */
    @GET
    @Path("/{userId}")
    @ApiOperation(
            value = "Get user by ID",
            notes = "Retrieves a user by their unique ID.",
            response = User.class)
    public Response getUserById(@PathParam("userId") String userId) throws NotFoundException {

        log.info("Retrieving user '" + userId + "'...");

        throw new NotFoundException(404, "User '" + userId + "' not found.");
    }
}
