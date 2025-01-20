package ro.uaic.info.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TimeSlotDto {
    private UUID secretaryId;
    private String secretaryName;
    private LocalDateTime dateTime;

    public TimeSlotDto(UUID secretaryId, LocalDateTime dateTime) {
        this.secretaryId = secretaryId;
        this.dateTime = dateTime;
    }
}
