package ro.uaic.info;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class ExampleResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @GET
    @Path("/authorized")
    @RolesAllowed("string")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloRolesAllowed() {
        return "Hello from Quarkus REST, admin!";
    }

}
