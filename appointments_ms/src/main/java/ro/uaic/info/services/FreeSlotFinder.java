package ro.uaic.info.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import ro.uaic.info.dtos.TimeEntryDto;
import ro.uaic.info.dtos.TimeSlotDto;
import ro.uaic.info.dtos.UserScheduleDto;
import ro.uaic.info.models.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
public class FreeSlotFinder {
    private List<UserScheduleDto> schedules;
    private List<Appointment> appointments;

    public List<TimeSlotDto> findFreeSlots() {
        List<TimeSlotDto> freeSlots = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusWeeks(1);

        // Iterate through each day of the next week
        for (LocalDate date = today; date.isBefore(endDate); date = date.plusDays(1)) {
            // Check each user's schedule for the day
            for (UserScheduleDto schedule : schedules) {
                for (TimeEntryDto entry : schedule.getTimeEntries()) {
                    if (!entry.getDay().equalsIgnoreCase(date.getDayOfWeek().toString())) {
                        continue;
                    }
                    LocalTime time = entry.getStartTime();
                    while (time.isBefore(entry.getEndTime())) {
                        LocalDateTime slot = LocalDateTime.of(date, time);
                        if (isSlotFree(slot)) {
                            freeSlots.add(new TimeSlotDto(schedule.getUserId(), slot));
                        }
                        time = time.plusMinutes(Appointment.APPOINTMENT_DURATION);
                    }
                }
            }
        }

        return freeSlots;
    }

    private boolean isSlotFree(LocalDateTime slot) {
        for (Appointment appointment : appointments) {
            LocalDateTime appointmentStart = appointment.getDateTime();
            LocalDateTime appointmentEnd = appointmentStart.plusMinutes(appointment.getDuration());

            if (!slot.isBefore(appointmentStart) && !slot.isAfter(appointmentEnd.minusMinutes(Appointment.APPOINTMENT_DURATION))) {
                return false; // Slot is not free if it overlaps with any appointment
            }
        }
        return true;
    }
}
