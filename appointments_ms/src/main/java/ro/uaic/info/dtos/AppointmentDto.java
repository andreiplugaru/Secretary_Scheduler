package ro.uaic.info.dtos;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@ToString
public class AppointmentDto {
    private LocalDateTime dateTime;
    private UUID secretaryId;
}
