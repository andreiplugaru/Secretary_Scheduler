package ro.uaic.info.dtos;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import ro.uaic.info.models.TimeEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class UserScheduleDto {
    private UUID userId;
    private List<TimeEntryDto> timeEntries = new ArrayList<>();
}
