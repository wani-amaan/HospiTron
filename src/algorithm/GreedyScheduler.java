package algorithm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.Appointment;

public class GreedyScheduler {

    private static final DateTimeFormatter[] FORMATTERS = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
    };

    public ArrayList<Appointment> schedule(ArrayList<Appointment> list) {
        ArrayList<Appointment> result = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return result;
        }

        List<AppointmentWrapper> wrapped = new ArrayList<>();
        for (Appointment a : list) {
            wrapped.add(new AppointmentWrapper(a, parseDateTime(a)));
        }

        Collections.sort(wrapped, Comparator
                .comparing(AppointmentWrapper::getDateTime)
                .thenComparingInt(w -> w.getAppointment().getAppointmentId()));

        Set<String> usedSlots = new HashSet<>();

        for (AppointmentWrapper wrapper : wrapped) {
            String slotKey = wrapper.getDateTime().toString();
            if (usedSlots.add(slotKey)) {
                result.add(wrapper.getAppointment());
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

    private static class AppointmentWrapper {
        private final Appointment appointment;
        private final LocalDateTime dateTime;

        private AppointmentWrapper(Appointment appointment, LocalDateTime dateTime) {
            this.appointment = appointment;
            this.dateTime = dateTime;
        }

        private Appointment getAppointment() {
            return appointment;
        }

        private LocalDateTime getDateTime() {
            return dateTime;
        }
    }
}