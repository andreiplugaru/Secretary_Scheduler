package ro.uaic.info.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.ToString;

import java.time.LocalTime;

@Data
@ToString
@RegisterForReflection
public class TimeIntervalForRemovalDto {
    private String secretaryId;
    private String day;
    private LocalTime startTime;
    private LocalTime endTime;
}
