package ro.uaic.info.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;
import ro.uaic.info.dtos.TimeEntryDto;
import ro.uaic.info.models.TimeEntry;
import ro.uaic.info.models.UserSchedule;
import ro.uaic.info.repositories.ScheduleRepository;
import ro.uaic.info.resources.ScheduleResource;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ScheduleService {
    private static final Logger LOG = Logger.getLogger(ScheduleService.class);

    @Inject
    ScheduleRepository scheduleRepository;

    @Inject
    AppointmentService appointmentService;
    @Channel("removedIntervals")
    Emitter<TimeEntryDto> removedIntervalsEmitter;

    public List<UserSchedule> getAllUserSchedules() {
        return scheduleRepository.findAll();
    }

    public UserSchedule getUserScheduleByUserId(UUID userId) {
        return scheduleRepository.getUserScheduleByUserId(userId);
    }

    public void updateSchedule(TimeEntry timeEntry, UUID userId) {
        UserSchedule userSchedule = getUserScheduleByUserId(userId);
        if (userSchedule == null) {
            userSchedule = new UserSchedule();
            userSchedule.setUserId(userId);
            userSchedule.addTimeEntry(timeEntry);
        }
        else {
            TimeEntry existingTimeEntry = userSchedule.getTimeEntryByDay(String.valueOf(timeEntry.getDay()));
            if (existingTimeEntry != null) {
                List<TimeEntryDto> difference = TimeEntry.differenceBetween(existingTimeEntry, timeEntry);
                difference.forEach((timeEntryDto) -> {
                    LOG.info("Sending removed interval: " + timeEntryDto);
                    removedIntervalsEmitter.send(timeEntryDto);

                });
            }
            userSchedule.replaceTimeEntryByDay(timeEntry);
        }
        timeEntry.setUserSchedule(userSchedule);
        scheduleRepository.merge(userSchedule);
    }

    public boolean validateTime(UUID userId, LocalDateTime dateTime) {
        UserSchedule userSchedule = getUserScheduleByUserId(userId);
        if (userSchedule == null) {
            return false;
        }

        TimeEntry timeEntry = userSchedule.getTimeEntryByDay(dateTime.getDayOfWeek().name());

        if (timeEntry == null) {
            return false;
        }
        int duration = appointmentService.getAppointmentDuration();
        return !timeEntry.getStartTime().isAfter(dateTime.toLocalTime()) && !timeEntry.getEndTime().isBefore(dateTime.toLocalTime().plusMinutes(duration));
    }
}
