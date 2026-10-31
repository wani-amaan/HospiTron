package algorithm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.Appointment;

/**
 * Modern Java Interval Scheduling (Greedy Algorithm) for conflict-free appointments.
 */
public final class IntervalScheduling {

    private static final int DEFAULT_SLOT_MINUTES = 15;

    private static final List<DateTimeFormatter> FORMATTERS = List.of(
            DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
    );

    public record AppointmentInterval(Appointment appointment, LocalDateTime start, LocalDateTime end) {}

    public ArrayList<Appointment> schedule(ArrayList<Appointment> list) {
        var result = new ArrayList<Appointment>();
        if (list == null || list.isEmpty()) {
            return result;
        }

        var intervals = new ArrayList<AppointmentInterval>();
        for (var a : list) {
            var start = parseDateTime(a);
            var end = start.plusMinutes(DEFAULT_SLOT_MINUTES);
            intervals.add(new AppointmentInterval(a, start, end));
        }

        intervals.sort(Comparator
                .comparing(AppointmentInterval::end)
                .thenComparing(AppointmentInterval::start)
                .thenComparingInt(i -> i.appointment().appointmentId()));

        LocalDateTime currentEnd = null;

        for (var interval : intervals) {
            if (currentEnd == null || !interval.start().isBefore(currentEnd)) {
                result.add(interval.appointment());
                currentEnd = interval.end();
            }
        }

        return result;
    }

    private LocalDateTime parseDateTime(Appointment appointment) {
        var combined = appointment.date().strip() + " " + appointment.time().strip();

        for (var formatter : FORMATTERS) {
            try {
                return LocalDateTime.parse(combined, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }

        throw new IllegalArgumentException(
                "Invalid appointment date/time format for Appointment ID " + appointment.appointmentId()
        );
    }
}