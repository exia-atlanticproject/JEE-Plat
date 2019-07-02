package com.crux.demo.api.resources;

import Broker.Connector;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.crux.demo.api.model.User;
import com.crux.demo.api.util.BrokerConnector;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.jms.JMSException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.List;

@Path("/")
@Api(value="/", description = "Operations about user")
@Produces({"application/json", "application/xml"})
public class AuthResource {

    static String authorization = "https://atlantisproject.b2clogin.com/atlantisproject.onmicrosoft.com/oauth2/v2.0/authorize?p=b2c_1_signuporsignin";
    static String token = "https://atlantisproject.b2clogin.com/atlantisproject.onmicrosoft.com/oauth2/v2.0/token?p=b2c_1_signuporsignin";
    static String logout = "https://atlantisproject.b2clogin.com/atlantisproject.onmicrosoft.com/oauth2/v2.0/logout?p=b2c_1_signuporsignin";
    static String clientId = "27fb84fe-4baf-4b6b-bfe7-f2d0638f2790";
    static String secret = "Zg2^04#WjA#h%6Q{]eK53J&`";
    static String redirectUri = "http://localhost:8090/login";
    public static JWTVerifier verifier;

    static {
        BigInteger modulus = new BigInteger( 1, Base64.decodeBase64("tVKUtcx_n9rt5afY_2WFNvU6PlFMggCatsZ3l4RjKxH0jgdLq6CScb0P3ZGXYbPzXvmmLiWZizpb-h0qup5jznOvOr-Dhw9908584BSgC83YacjWNqEK3urxhyE2jWjwRm2N95WGgb5mzE5XmZIvkvyXnn7X8dvgFPF5QwIngGsDG8LyHuJWlaDhr_EPLMW4wHvH0zZCuRMARIJmmqiMy3VD4ftq4nS5s8vJL0pVSrkuNojtokp84AtkADCDU_BUhrc2sIgfnvZ03koCQRoZmWiHu86SuJZYkDFstVTVSR0hiXudFlfQ2rOhPlpObmku68lXw-7V-P7jwrQRFfQVXw"));
        BigInteger exponent = new BigInteger( 1, Base64.decodeBase64("AQAB"));

        RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);

        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey pubKey = kf.generatePublic(spec);

            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) pubKey, null);
            verifier = JWT.require(algorithm)
                    .withIssuer("https://atlantisproject.b2clogin.com/41dd4f0b-7a80-473f-82fa-d518398d6a7f/v2.0/")
                    .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }


    }

    @GET
    @Path("/auth")
    @ApiOperation(value = "Get user by user name",
            response = User.class)
    @Produces(MediaType.TEXT_PLAIN)
    public Response auth() {
        String auth = authorization
                + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&scope=openid&response_type=code"
                + "&response_mode=query&nonce=1234&prompt=login";
        return Response.ok().entity("\"" + auth + "\"").build();
    }

    @POST
    @Path("/logout")
    @ApiOperation(value = "Logout a user", response = String.class)
    public Response logout(
            @Context UriInfo uriInfo) {
        return Response.ok().entity("success").build();
    }

    @GET
    @Path("/login")
    @ApiOperation(value = "Logs user into the system",
            response = String.class)
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid username/password supplied") })
    public Response loginUser(
            @Context UriInfo uriInfo
//            final @Suspended AsyncResponse ar
            ) throws IOException {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(token);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("code", uriInfo.getQueryParameters().getFirst("code")));
        nvps.add(new BasicNameValuePair("client_id", clientId));
        nvps.add(new BasicNameValuePair("client_secret", secret));
        nvps.add(new BasicNameValuePair("redirect_uri", "urn:ietf:wg:oauth:2.0:oo"));
        nvps.add(new BasicNameValuePair("grant_type", "authorization_code"));
        nvps.add(new BasicNameValuePair("scope", "openid"));

        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        try {
            CloseableHttpResponse httpResponse = client.execute(httpPost);
            String response = EntityUtils.toString(httpResponse.getEntity());

            JSONObject res = new JSONObject(response);

            try {
                String error = res.getString("error_description");
                return Response.status(401).entity("\"" + error + "\"").build();
            } catch (Exception e) {
                System.out.println("No error");
            }

            DecodedJWT token = verifier.verify(res.getString("id_token"));
            JSONObject userPayload= new JSONObject();
            JSONObject userMessage = new JSONObject();

            userMessage.put("action", "login");

            userPayload.put("name", token.getClaim("given_name").asString());
            userPayload.put("surname", token.getClaim("family_name").asString());
            userPayload.put("uid", token.getClaim("oid").asString());
            userPayload.put("email", "");
            userMessage.put("payload", userPayload);

            BrokerConnector.getConnector().emit("Data-Controller", userMessage.toString());
            return Response.ok().build();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        return Response.status(401).build();
    }
}