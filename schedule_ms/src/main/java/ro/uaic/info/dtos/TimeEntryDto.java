package ro.uaic.info.dtos;

import lombok.Data;
import lombok.ToString;

import java.time.LocalTime;

@Data
@ToString
public class TimeEntryDto {
    private String day;
    private LocalTime startTime;
    private LocalTime endTime;
}
