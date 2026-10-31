package algorithm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.Appointment;

public class IntervalScheduling {

    private static final int DEFAULT_SLOT_MINUTES = 15;

    private static final DateTimeFormatter[] FORMATTERS = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
    };

    public ArrayList<Appointment> schedule(ArrayList<Appointment> list) {
        ArrayList<Appointment> result = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return result;
        }

        List<AppointmentInterval> intervals = new ArrayList<>();
        for (Appointment a : list) {
            LocalDateTime start = parseDateTime(a);
            LocalDateTime end = start.plusMinutes(DEFAULT_SLOT_MINUTES);
            intervals.add(new AppointmentInterval(a, start, end));
        }

        intervals.sort(Comparator
                .comparing(AppointmentInterval::getEnd)
                .thenComparing(AppointmentInterval::getStart)
                .thenComparingInt(i -> i.getAppointment().getAppointmentId()));

        LocalDateTime currentEnd = null;

        for (AppointmentInterval interval : intervals) {
            if (currentEnd == null || !interval.getStart().isBefore(currentEnd)) {
                result.add(interval.getAppointment());
                currentEnd = interval.getEnd();
            }
        }

        return result;
    }

    private LocalDateTime parseDateTime(Appointment appointment) {
        String combined = appointment.getDate().trim() + " " + appointment.getTime().trim();

        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                return LocalDateTime.parse(combined, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }

        throw new IllegalArgumentException(
                "Invalid appointment date/time format for Appointment ID " + appointment.getAppointmentId()
        );
    }

    private static class AppointmentInterval {
        private final Appointment appointment;
        private final LocalDateTime start;
        private final LocalDateTime end;

        private AppointmentInterval(Appointment appointment, LocalDateTime start, LocalDateTime end) {
            this.appointment = appointment;
            this.start = start;
            this.end = end;
        }

        private Appointment getAppointment() {
            return appointment;
        }

        private LocalDateTime getStart() {
            return start;
        }

        private LocalDateTime getEnd() {
            return end;
        }
    }
}