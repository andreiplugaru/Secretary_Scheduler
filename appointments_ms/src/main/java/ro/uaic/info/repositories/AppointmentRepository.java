package ro.uaic.info.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import ro.uaic.info.dtos.TimeEntryDto;
import ro.uaic.info.models.Appointment;

import java.util.List;
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

    public List<Appointment> getAllAppointments() {
        return em.createNamedQuery("Appointment.findAll", Appointment.class).getResultList();
    }

    public boolean checkAppointmentExists(Appointment appointment) {
        return em.createNamedQuery("Appointment.checkAvailability", Appointment.class)
                .setParameter("secretaryId", appointment.getSecretaryId())
                .setParameter("dateTime", appointment.getDateTime())
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst()
                .isPresent();
    }
}
