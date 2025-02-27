package ro.uaic.info.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Table(name = "appointments")
@Entity
@Data
@NamedQueries({
        @NamedQuery(name = "Appointment.selectByDayAndSecretary", query = "SELECT a FROM Appointment a " +
                "WHERE a.secretaryId = :secretaryId "),
        @NamedQuery(name = "Appointment.findAll", query = "SELECT a FROM Appointment a"),
        @NamedQuery(name = "Appointment.checkAvailability", query = "SELECT a FROM Appointment a WHERE a.secretaryId = :secretaryId AND a.dateTime = :dateTime"),
        @NamedQuery(name = "Appointment.findByStudentId", query = "SELECT a FROM Appointment a WHERE a.studentId = :studentId")
})
public class Appointment {
    public final static int APPOINTMENT_DURATION = 10;

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private UUID studentId;
    private UUID secretaryId;
    private LocalDateTime dateTime;
    private int duration = APPOINTMENT_DURATION;
}
