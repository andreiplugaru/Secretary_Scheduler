package ro.uaic.info.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import ro.uaic.info.dtos.TimeEntryDto;
import ro.uaic.info.dtos.TimeIntervalForRemovalDto;
import ro.uaic.info.models.Appointment;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AppointmentRepository extends DataRepository<Appointment, UUID> {
    protected AppointmentRepository() {
        super(Appointment.class);
    }
    @SuppressWarnings({"unchecked"})
    public void deleteAppointmentsInInterval(TimeIntervalForRemovalDto timeEntryDto) {
       List<Appointment> appointments = (List<Appointment>) em.createNamedQuery("Appointment.selectByDayAndSecretary")
                .setParameter("secretaryId", UUID.fromString(timeEntryDto.getSecretaryId())).getResultList();

        appointments.stream().filter(appointment ->
                appointment.getDateTime().getDayOfWeek().name().equalsIgnoreCase(timeEntryDto.getDay()) &&
                (!appointment.getDateTime().toLocalTime().isBefore(timeEntryDto.getStartTime())
                    || !appointment.getDateTime().toLocalTime().plusMinutes(Appointment.APPOINTMENT_DURATION).isBefore(timeEntryDto.getStartTime()))
                && (!appointment.getDateTime().toLocalTime().isAfter(timeEntryDto.getEndTime())))
        .forEach((appointment) -> em.remove(appointment));
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

    public List<Appointment> getAppointmentsByStudentId(UUID studentId) {
        return em.createNamedQuery("Appointment.findByStudentId", Appointment.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }
}
