package ro.uaic.info.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import ro.uaic.info.clients.ScheduleClient;
import ro.uaic.info.clients.UserClient;
import ro.uaic.info.dtos.*;
import ro.uaic.info.models.Appointment;
import ro.uaic.info.services.AppointmentService;
import ro.uaic.info.validators.ValidAppointmentDto;

import java.util.List;
import java.util.UUID;

@Path("/appointments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AppointmentResource {
    private static final Logger LOG = Logger.getLogger(AppointmentResource.class);
    @Inject
    AppointmentService appointmentService;


    @POST
    @RolesAllowed("STUDENT")
    @Transactional
    public void createAppointment(@Context JsonWebToken ctx, @RequestBody @ValidAppointmentDto AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();
        UUID studentId = UUID.fromString(ctx.getClaim("preferred_username"));
        appointment.setStudentId(studentId);
        appointment.setSecretaryId(appointmentDto.getSecretaryId());
        appointment.setDateTime(appointmentDto.getDateTime());
        appointmentService.createAppointment(appointment);

        LOG.info("Creating appointment");
    }

    @GET
    @RolesAllowed("STUDENT")
    public List<AppointmentDto> getAppointmentsByStudentId(@Context JsonWebToken ctx) {
        String studentId = ctx.getClaim("preferred_username");
        return appointmentService.getAppointmentsByStudentId(UUID.fromString(studentId)).stream().map((appointment) -> {
            AppointmentDto returnAppointmentDto = new AppointmentDto();
            returnAppointmentDto.setDateTime(appointment.getDateTime());
            returnAppointmentDto.setSecretaryId(appointment.getSecretaryId());
            return returnAppointmentDto;
        }).toList();
    }

    @GET
    @Path("/duration")
    public int getAppointmentDuration() {
        return Appointment.APPOINTMENT_DURATION;
    }

    @Incoming("removedIntervals")
    @Transactional
    public void processRemovals(io.vertx.core.json.JsonObject timeEntryDtoJson) {
        TimeIntervalForRemovalDto timeEntryDto = timeEntryDtoJson.mapTo(TimeIntervalForRemovalDto.class);
        appointmentService.deleteAppointmentsInInterval(timeEntryDto);
        LOG.info("Processing removed interval: " + timeEntryDto);
    }

    @GET
    @Path("/free-slots")
    public List<TimeSlotDto> getFreeSlots() {
        return appointmentService.getFreeSlots();
    }
}
