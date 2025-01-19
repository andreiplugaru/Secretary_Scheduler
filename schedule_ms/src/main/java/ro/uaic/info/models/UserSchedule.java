package ro.uaic.info.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.uaic.info.dtos.UserScheduleDto;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Table(name = "user_schedule")
@Entity
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "UserSchedule.findByUserId", query = "SELECT u FROM UserSchedule u WHERE u.userId = :userId"),
        @NamedQuery(name = "UserSchedule.findAll", query = "SELECT u FROM UserSchedule u")
})
public class UserSchedule {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Getter
    @Setter
    private UUID userId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userSchedule")
    @JsonManagedReference
    private List<TimeEntry> timeEntries = new ArrayList<>();

    public void addTimeEntry(TimeEntry timeEntry) {
        timeEntries.add(timeEntry);
    }

    public void replaceTimeEntryByDay(TimeEntry timeEntry) {
        List<TimeEntry> entriesToRemove = timeEntries.stream()
                .filter(entry -> entry.getDay().equals(timeEntry.getDay()))
                .toList();

        // Remove collected entries and update them
        entriesToRemove.forEach(entry -> {
            timeEntries.remove(entry);
            entry.setUserSchedule(null);
        });
        timeEntries.add(timeEntry);
    }

    public TimeEntry getTimeEntryByDay(String day) {
        return timeEntries.stream().filter(entry -> entry.getDay().name().equals(day)).findFirst().orElse(null);
    }

    public UserScheduleDto toDto() {
        UserScheduleDto userScheduleDto = new UserScheduleDto();
        userScheduleDto.setUserId(userId);
        userScheduleDto.setTimeEntries(timeEntries.stream().map(TimeEntry::toDto).collect(Collectors.toList()));
        return userScheduleDto;
    }

}


