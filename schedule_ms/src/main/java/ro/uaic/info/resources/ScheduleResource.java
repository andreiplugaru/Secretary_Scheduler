package ro.uaic.info.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.ResponseStatus;
import ro.uaic.info.dtos.TimeEntryDto;
import ro.uaic.info.dtos.UserScheduleDto;
import ro.uaic.info.models.TimeEntry;
import ro.uaic.info.models.UserSchedule;
import ro.uaic.info.services.ScheduleService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;


@Path("/schedule")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ScheduleResource {
    private static final Logger LOG = Logger.getLogger(ScheduleResource.class);
    @Inject
    ScheduleService scheduleService;

    @GET
    @RolesAllowed("secretary")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/me")
    public UserSchedule createRequest(@Context JsonWebToken ctx) {
        return scheduleService.getUserScheduleByUserId(UUID.fromString(ctx.getClaim(Claims.preferred_username.name())));
    }

    @PUT
    @RolesAllowed("secretary")
    @ResponseStatus(204)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public void updateSchedule(@Context JsonWebToken ctx, @RequestBody TimeEntryDto timeEntryDto) {
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setDay(TimeEntry.Day.valueOf(timeEntryDto.getDay()));
        timeEntry.setStartTime(timeEntryDto.getStartTime());
        timeEntry.setEndTime(timeEntryDto.getEndTime());
        UUID userId = UUID.fromString(ctx.getClaim(Claims.preferred_username.name()));
        scheduleService.updateSchedule(timeEntry, userId);
//        LOG.info("Updating schedule for user: " + userSchedule.getUserId());
    }

    @GET
    @Path("/availability")
    public boolean validateTimeAvailability(@QueryParam("secretaryId") UUID secretaryId, @QueryParam("dateTime") String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime, formatter);

        return scheduleService.validateTime(secretaryId, parsedDateTime);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserScheduleDto> getAllUserSchedules() {
        return scheduleService.getAllUserSchedules().stream().map(UserSchedule::toDto).toList();
    }
//    @GET
//    @Path("roles-allowed")
//    @RolesAllowed("student")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String helloRolesAllowed(@Context JsonWebToken ctx) {
//        return "name: " + jwt.getName().toString();
//    }
}
