//package io.swagger.sample.resource;
////
////import com.sun.net.httpserver.Authenticator;
////import com.sun.net.httpserver.HttpExchange;
////import io.swagger.annotations.SecurityDefinition;
////import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
////import io.swagger.v3.oas.annotations.security.SecurityScheme;
////
////import javax.ws.rs.core;
////import org.restlet.security.Context;
////
////
////@SecurityScheme(name= "JWT", type= SecuritySchemeType.APIKEY)
//public class SecurityResource extends Authenticator {
//    public SecurityResource(Context ctx) {
//        super(ctx);
//    }
//
//    @Override
//    public Result authenticate(HttpExchange httpExchange) {
//        return null;
//    }
//}