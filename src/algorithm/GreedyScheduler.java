package algorithm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import model.Appointment;

/**
 * Modern Java Greedy Scheduler for conflict-free doctor-patient slots.
 */
public final class GreedyScheduler {

    private static final List<DateTimeFormatter> FORMATTERS = List.of(
            DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
    );

    public record AppointmentWrapper(Appointment appointment, LocalDateTime dateTime) {}

    public ArrayList<Appointment> schedule(ArrayList<Appointment> list) {
        var result = new ArrayList<Appointment>();
        if (list == null || list.isEmpty()) {
            return result;
        }

        var wrapped = new ArrayList<AppointmentWrapper>();
        for (var a : list) {
            wrapped.add(new AppointmentWrapper(a, parseDateTime(a)));
        }

        wrapped.sort(Comparator
                .comparing(AppointmentWrapper::dateTime)
                .thenComparingInt(w -> w.appointment().appointmentId()));

        var usedSlots = new HashSet<String>();

        for (var wrapper : wrapped) {
            var slotKey = wrapper.dateTime().toString();
            if (usedSlots.add(slotKey)) {
                result.add(wrapper.appointment());
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