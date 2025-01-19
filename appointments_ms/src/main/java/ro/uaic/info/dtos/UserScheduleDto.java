package ro.uaic.info.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class UserScheduleDto {
    private UUID userId;
    private List<TimeEntryDto> timeEntries = new ArrayList<>();
}
