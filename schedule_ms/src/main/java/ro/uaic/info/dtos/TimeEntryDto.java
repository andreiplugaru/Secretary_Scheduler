package ro.uaic.info.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.ToString;

import java.time.LocalTime;

@Data
@ToString
@RegisterForReflection
public class TimeEntryDto {
    private String day;
    private LocalTime startTime;
    private LocalTime endTime;
}
