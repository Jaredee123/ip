package sofi.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    public Deadline(String description, String by) {
        super(description);
        this.by = parseDateTime(by);
    }

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            // Try yyyy-MM-dd HHmm format first
            return LocalDateTime.parse(dateTimeStr, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            try {
                // Try yyyy-MM-dd format (default to 23:59)
                return LocalDateTime.parse(dateTimeStr + " 2359", INPUT_FORMAT);
            } catch (DateTimeParseException e2) {
                try {
                    // Try M/d/yyyy HHmm format
                    DateTimeFormatter altFormat = DateTimeFormatter.ofPattern("M/d/yyyy HHmm");
                    return LocalDateTime.parse(dateTimeStr, altFormat);
                } catch (DateTimeParseException e3) {
                    try {
                        // Try M/d/yyyy format (default to 23:59)
                        DateTimeFormatter altFormat = DateTimeFormatter.ofPattern("M/d/yyyy HHmm");
                        return LocalDateTime.parse(dateTimeStr + " 2359", altFormat);
                    } catch (DateTimeParseException e4) {
                        // If all parsing fails, throw a more informative exception
                        throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd, yyyy-MM-dd HHmm, M/d/yyyy, or M/d/yyyy HHmm format.");
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "[D]" + getStatusIcon() + " " + description + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }

    public LocalDateTime getBy() {
        return by;
    }
}

