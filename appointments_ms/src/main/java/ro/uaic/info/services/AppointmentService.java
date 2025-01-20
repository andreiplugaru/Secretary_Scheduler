package ro.uaic.info.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import ro.uaic.info.clients.ScheduleClient;
import ro.uaic.info.dtos.TimeEntryDto;
import ro.uaic.info.dtos.TimeSlotDto;
import ro.uaic.info.dtos.UserScheduleDto;
import ro.uaic.info.exceptions.UnavailableTimeException;
import ro.uaic.info.models.Appointment;
import ro.uaic.info.repositories.AppointmentRepository;

import java.util.List;

@ApplicationScoped
public class AppointmentService {
    @Inject
    @RestClient
    ScheduleClient scheduleClient;

    @Inject
    AppointmentRepository appointmentRepository;

    @Inject
    UserService userService;
    public void createAppointment(Appointment appointment) {
        synchronized (this) {
            if (!scheduleClient.validateTimeAvailability(appointment.getSecretaryId(), appointment.getDateTime().toString())
                    || checkAppointmentInterval(appointment)) {
                throw new UnavailableTimeException();
            }
            appointmentRepository.persist(appointment);
        }
    }

    private boolean checkAppointmentInterval(Appointment appointment) {
        return appointmentRepository.checkAppointmentExists(appointment);
    }

    public void deleteAppointmentsInInterval(TimeEntryDto timeEntryDto) {
        appointmentRepository.deleteAppointmentsInInterval(timeEntryDto);
    }

    public List<TimeSlotDto> getFreeSlots() {
        List<UserScheduleDto> secretarySchedules = scheduleClient.getAllUserSchedules();
        FreeSlotFinder freeSlotFinder = new FreeSlotFinder(secretarySchedules, appointmentRepository.getAllAppointments());
        List<TimeSlotDto> freeTimeSlots =  freeSlotFinder.findFreeSlots();
        freeTimeSlots.forEach((timeSlotDto) -> {
            timeSlotDto.setSecretaryName(userService.getUserNameById(timeSlotDto.getSecretaryId()));
        });
        return freeTimeSlots;
    }
}
