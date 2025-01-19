package ro.uaic.info.clients;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/appointments")
@RegisterRestClient
public interface AppointmentClient {
    @GET
    @Path("/duration")
    int getAppointmentDuration();
}
