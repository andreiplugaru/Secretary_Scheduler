package ro.uaic.info.dtos;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString
public class ReturnAppointmentDto {
    private LocalDateTime dateTime;
    private UUID secretaryName;
}
