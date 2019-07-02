package com.crux.demo.api.listeners;

import com.wordnik.swagger.models.Contact;
import com.wordnik.swagger.models.Info;
import com.wordnik.swagger.models.License;
import com.wordnik.swagger.models.Swagger;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created with IntelliJ IDEA.
 * User: aaron.stone
 * Date: 9/11/13
 * Time: 6:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class Bootstrapper implements ServletContextListener {
    private static final Logger log = Logger.getLogger(Bootstrapper.class);

    public void contextInitialized(ServletContextEvent servletContextEvent) {

        servletContextEvent.getServletContext();

        try {

            buildMockData();

            buildSwaggerInfo(servletContextEvent.getServletContext());

        } catch(Exception e) {
            log.error("Failed to load mock data.", e);
        }
    }



    /**
     * Build the mock data for the system from JSON.
     *
     * @throws Exception
     */
    private void buildMockData() throws Exception {
        log.info("Building in-memory database...");

//        // Build a fake user and add them to the in-memory database of users.
//        User fakeUser = new User("Aaron", "Stone", "aaron.stone@effectiveui.com");
//        User.addUser(fakeUser);
//
//        fakeUser = new User("Zach", "Hendershot", "zach.hendershot@effectiveui.com");
//        User.addUser(fakeUser);
//
//        fakeUser = new User("Nathan", "Ameye", "nathan.ameye@effectiveui.com");
//        User.addUser(fakeUser);

        log.info("Successfully loaded mock data.");
    }


    private void buildSwaggerInfo(ServletContext context) throws Exception {

        // TODO: Update Swagger details from demo.
        Info info = new Info()
                .title("Sample Swagger/Jersey2 App")
                .description("Boilerplate Jersey 2 Swagger documentation.")
                //.termsOfService("http://helloreverb.com/terms/")
                .contact(new Contact()
                        .email("aaronastone@gmail.com"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0.html"));

        Swagger swagger = new Swagger().info(info);

        // TODO: Adjust the security for this.
//        swagger.securityDefinition("api_key", new ApiKeyAuthDefinition("api_key", In.HEADER));
//        swagger.securityDefinition("petstore_auth",
//                new OAuth2Definition()
//                        .implicit("http://petstore.swagger.io/api/oauth/dialog")
//                        .scope("read:pets", "read your pets")
//                        .scope("write:pets", "modify pets in your account"));
        context.setAttribute("swagger", swagger);
    }


    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
