package ro.uaic.info.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import ro.uaic.info.clients.ScheduleClient;
import ro.uaic.info.dtos.TimeEntryDto;
import ro.uaic.info.exceptions.UnavailableTimeException;
import ro.uaic.info.models.Appointment;
import ro.uaic.info.repositories.AppointmentRepository;
@ApplicationScoped
public class AppointmentService {
    @Inject
    @RestClient
    ScheduleClient scheduleClient;

    @Inject
    AppointmentRepository appointmentRepository;
    public void createAppointment(Appointment appointment) {
        synchronized (this) {
            if (!scheduleClient.validateTimeAvailability(appointment.getSecretaryId(), appointment.getDateTime().toString())) {
                throw new UnavailableTimeException();
            }
            appointmentRepository.persist(appointment);
        }
    }

    public void deleteAppointmentsInInterval(TimeEntryDto timeEntryDto) {
        appointmentRepository.deleteAppointmentsInInterval(timeEntryDto);
    }

    public 
}
