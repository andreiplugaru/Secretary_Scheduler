package ro.uaic.info.resources;

import io.quarkus.logging.Log;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import ro.uaic.info.dtos.UserRegisterDto;
import ro.uaic.info.models.User;
import ro.uaic.info.services.TokenService;
import ro.uaic.info.services.UserService;

import java.util.UUID;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    TokenService tokenService;
    @Inject
    UserService userService;
    @Inject
    JsonWebToken jwt;
    @POST
    @Path("/register")
    @Transactional
    public Response register(UserRegisterDto user) {
        User newUser = new User(user.name(), user.role(), user.password());
        userService.registerUser(newUser);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/login")
//    @RolesAllowed("string")
//    @RolesAllowed("string")
    @Authenticated
    public String login(@Context SecurityContext context) {
//        User existingUser = User.find("login", login).firstResult();
//        if(existingUser == null || !existingUser.password.equals(password)) {
//            throw new WebApplicationException(Response.status(404).entity("No user found or password is incorrect").build());
//        }
//        return tokenService.generateUserToken(existingUser.email, password);
//        SecurityContext securityContext = context;
//        String userName =  context.getUserPrincipal().getName();
        User user = userService.getUserByName(context.getUserPrincipal().getName());
        return tokenService.generateToken(user.getName(), user.getId().toString(), user.getRole());
    }

    @GET
    @Path("roles-allowed")
    @RolesAllowed("string")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloRolesAllowed(@Context JsonWebToken  ctx) {
        return "name: " + jwt.getName().toString();
    }

    @GET
    @Path("/exists/{id}")
    public boolean checkIfUserExists(@PathParam("id") UUID id) {
        Log.info("Checking if user " + id + " exists");
        return userService.checkIfUserExists(id);
    }
    @GET
    @Path("/name/{id}")
    public String getUserNameById(@PathParam("id") UUID id) {
        return userService.getUserById(id).getName();
    }
}
