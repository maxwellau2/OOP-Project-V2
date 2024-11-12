package Util;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TimeRangeMerger {

    public static List<String> mergeTimeslotsIntoRanges(List<LocalDateTime> timeslots) {
        List<String> timeRanges = new ArrayList<>();

        if (timeslots.isEmpty()) return timeRanges;

        LocalDateTime start = timeslots.get(0);
        LocalDateTime end = start;

        for (int i = 1; i < timeslots.size(); i++) {
            LocalDateTime current = timeslots.get(i);

            // Check if current timeslot is consecutive to the end of the current range
            if (current.equals(end.plusMinutes(30))) {
                end = current;
            } else {
                // Add the current range to the list and start a new range
                timeRanges.add(formatRange(start, end));
                start = current;
                end = current;
            }
        }

        // Add the last range
        timeRanges.add(formatRange(start, end));

        return timeRanges;
    }

    private static String formatRange(LocalDateTime start, LocalDateTime end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a");
        return start.format(formatter) + " to " + end.format(formatter);
    }
}