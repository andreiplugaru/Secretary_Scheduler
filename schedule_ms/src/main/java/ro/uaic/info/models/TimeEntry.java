package ro.uaic.info.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import ro.uaic.info.dtos.TimeEntryDto;
import ro.uaic.info.dtos.TimeIntervalForRemovalDto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class TimeEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Day day;
    private LocalTime startTime;
    private LocalTime endTime;
    @ManyToOne
    @JsonIgnore
    private UserSchedule userSchedule;
    public TimeEntry() {
    }
   public enum
    Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY
    }

    public static List<TimeIntervalForRemovalDto> differenceBetween(TimeEntry oldTimeEntry, TimeEntry newTimeEntry) {
        List<TimeIntervalForRemovalDto> result = new ArrayList<>();
        TimeIntervalForRemovalDto difference;
        if (newTimeEntry.getStartTime().isAfter(oldTimeEntry.getStartTime())) {
            difference = new TimeIntervalForRemovalDto();
            difference.setSecretaryId(oldTimeEntry.getUserSchedule().getUserId().toString());
            difference.setDay(String.valueOf(oldTimeEntry.getDay()));
            difference.setStartTime(oldTimeEntry.getStartTime());
            difference.setEndTime(newTimeEntry.getStartTime());
            result.add(difference);
        }
        if (newTimeEntry.getEndTime().isBefore(oldTimeEntry.getEndTime())) {
            difference = new TimeIntervalForRemovalDto();
            difference.setSecretaryId(oldTimeEntry.getUserSchedule().getUserId().toString());
            difference.setDay(String.valueOf(oldTimeEntry.getDay()));
            difference.setStartTime(newTimeEntry.getEndTime());
            difference.setEndTime(oldTimeEntry.getEndTime());
        }
        return result;
    }

    public TimeEntryDto toDto() {
        TimeEntryDto timeEntryDto = new TimeEntryDto();
        timeEntryDto.setDay(String.valueOf(day));
        timeEntryDto.setStartTime(startTime);
        timeEntryDto.setEndTime(endTime);
        return timeEntryDto;
    }
}
