package ro.uaic.info.clients;

import io.quarkus.runtime.annotations.StaticInitSafe;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.UUID;

@Path("/users")
@RegisterRestClient
@StaticInitSafe
public interface UserClient {
    @GET
    @Path("/exists/{id}")
    boolean checkIfUserExists(@PathParam("id") UUID id);
}
