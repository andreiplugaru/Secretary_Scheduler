package ro.uaic.info.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import ro.uaic.info.clients.AppointmentClient;
@ApplicationScoped
public class AppointmentService {
    private static Integer APPOINTMENT_DURATION;
    @Inject
    @RestClient
    AppointmentClient appointmentClient;
    public int getAppointmentDuration() {
        if (APPOINTMENT_DURATION == null) {
            APPOINTMENT_DURATION = appointmentClient.getAppointmentDuration();
        }
        return APPOINTMENT_DURATION;
    }
}
