package ro.uaic.info.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Table(name = "appointments")
@Entity
@Data
@NamedQueries({
        @NamedQuery(name = "Appointment.deleteInInterval", query = "DELETE FROM Appointment a WHERE a.dateTime >= :start AND a.dateTime < :end")
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
