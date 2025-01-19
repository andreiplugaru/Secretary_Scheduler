package ro.uaic.info.clients;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import ro.uaic.info.dtos.UserScheduleDto;

import java.util.List;
import java.util.UUID;

@Path("/schedule")
@RegisterRestClient
public interface ScheduleClient {
    @GET
    @Path("/availability")
    boolean validateTimeAvailability(@QueryParam("secretaryId") UUID secretaryId, @QueryParam("dateTime") String dateTime);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<UserScheduleDto> getAllUserSchedules();
}

