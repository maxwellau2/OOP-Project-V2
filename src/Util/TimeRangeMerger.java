package Util;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for merging a list of time slots into contiguous time ranges.
 * Assumes that the input time slots are sorted and have a consistent 30-minute interval.
 */
public class TimeRangeMerger {

    /**
     * Merges a list of consecutive 30-minute time slots into contiguous time ranges.
     *
     * @param timeslots A list of sorted `LocalDateTime` objects representing 30-minute time slots.
     * @return A list of formatted strings representing the merged time ranges.
     */
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

    /**
     * Formats a range of start and end times into a human-readable string.
     *
     * @param start The start of the time range.
     * @param end   The end of the time range.
     * @return A formatted string representing the time range.
     */
    private static String formatRange(LocalDateTime start, LocalDateTime end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a");
        return start.format(formatter) + " to " + end.format(formatter);
    }
}