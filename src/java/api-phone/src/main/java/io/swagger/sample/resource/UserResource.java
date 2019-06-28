/**
 *  Copyright 2016 SmartBear Software
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.swagger.sample.resource;

import io.swagger.annotations.*;
import io.swagger.sample.data.UserData;
import io.swagger.sample.model.User;
import io.swagger.sample.exception.ApiException;
import io.swagger.sample.exception.NotFoundException;
import org.dmfs.httpessentials.client.HttpRequestExecutor;
import org.dmfs.httpessentials.httpurlconnection.HttpUrlConnectionExecutor;
import org.dmfs.oauth2.client.*;
import org.dmfs.oauth2.client.grants.AuthorizationCodeGrant;
import org.dmfs.oauth2.client.grants.ImplicitGrant;
import org.dmfs.oauth2.client.scope.BasicScope;
import org.dmfs.rfc3986.encoding.Precoded;
import org.dmfs.rfc3986.parameters.ParameterList;
import org.dmfs.rfc3986.parameters.ParameterType;
import org.dmfs.rfc3986.parameters.adapters.TextParameter;
import org.dmfs.rfc3986.parameters.parametersets.BasicParameterList;
import org.dmfs.rfc3986.parameters.parametertypes.BasicParameterType;
import org.dmfs.rfc3986.parameters.valuetypes.TextValueType;
import org.dmfs.rfc3986.uris.LazyUri;
import org.dmfs.rfc5545.Duration;


import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;
import javax.ws.rs.core.UriInfo;

@Path("/user")
@Api(value="/user", description = "Operations about user")
@Produces({"application/json", "application/xml"})
public class UserResource {
  static UserData userData = new UserData();

  @PUT
  @Path("/{username}")
  @ApiOperation(value = "Updated user",
    notes = "This can only be done by the logged in user.",
    position = 4)
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "Invalid user supplied"),
      @ApiResponse(code = 404, message = "User not found") })
  public Response updateUser(
      @ApiParam(value = "name that need to be updated", required = true) @PathParam("username") String username,
      @ApiParam(value = "Updated user object", required = true) User user) {
    userData.addUser(user);
    return Response.ok().entity("").build();
  }

  @DELETE
  @Path("/{username}")
  @ApiOperation(value = "Delete user",
    notes = "This can only be done by the logged in user.",
    position = 5)
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "Invalid username supplied"),
      @ApiResponse(code = 404, message = "User not found") })
  public Response deleteUser(
      @ApiParam(value = "The name that needs to be deleted", required = true) @PathParam("username") String username) {
    if (userData.removeUser(username)) {
          return Response.ok().entity("").build();
      } else {
          return Response.status(Response.Status.NOT_FOUND).build();
      }
  }

  @GET
  @Path("/{username}")
  @ApiOperation(value = "Get user by user name",
    response = User.class,
    position = 0)
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "Invalid username supplied"),
      @ApiResponse(code = 404, message = "User not found") })
  public Response getUserByName(
      @ApiParam(value = "The name that needs to be fetched. Use user1 for testing. ", required = true) @PathParam("username") String username)
    throws ApiException {
    User user = userData.findUserByName(username);
    if (null != user) {
      return Response.ok().entity(user).build();
    } else {
      throw new NotFoundException(404, "User not found");
    }
  }

  @GET
  @Path("/login")
  @ApiOperation(value = "Logs user into the system",
    response = String.class)
  @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid username/password supplied") })
  public Response loginUser(@Context UriInfo uriInfo) {


    System.out.println(uriInfo.toString());
//    OAuth2AccessToken token = grant.withRedirect(new LazyUri(new Precoded("http://localhost"))).accessToken(executor);
    return Response.ok()
//      .entity("state: " + state + "\ncode: " + code)
      .build();
  }

  @GET
  @Path("/logout")
  @ApiOperation(value = "Logs out current logged in user session",
    position = 7)
  public Response logoutUser() {
    return Response.ok().entity("").build();
  }
}
