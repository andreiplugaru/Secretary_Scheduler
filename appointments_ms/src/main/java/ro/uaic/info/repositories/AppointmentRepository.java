package ro.uaic.info.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import ro.uaic.info.dtos.TimeEntryDto;
import ro.uaic.info.models.Appointment;

import java.util.UUID;

@ApplicationScoped
public class AppointmentRepository extends DataRepository<Appointment, UUID> {
    protected AppointmentRepository() {
        super(Appointment.class);
    }

    public void deleteAppointmentsInInterval(TimeEntryDto timeEntryDto) {
        em.createNamedQuery("Appointment.deleteInInterval")
                .setParameter("start", timeEntryDto.getStartTime().minusMinutes(Appointment.APPOINTMENT_DURATION))
                .setParameter("end", timeEntryDto.getEndTime())
                .executeUpdate();
    }
}
