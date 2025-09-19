package meownager.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a deadline task with a due date.
 *
 * Extends parent Task class and adds support for storing and displaying
 * a specific deadline (e.g. "return book (by: Monday 2pm)")
 *
 * @author Yu Tingan
 */
public class Deadline extends Task {
    protected String date;

    /**
     * Constructs a Deadline object with no tag.
     *
     * @param description Task description.
     * @param date Task due date.
     */
    public Deadline(String description, String date) {
        super(description);
        // checking if input is in specific date format
        Pattern patternWithTime = Pattern.compile("(\\d{1,2})/(\\d{1,2})/(\\d{4}) (\\d{4})");
        Matcher mTime = patternWithTime.matcher(date);
        Pattern patternNoTime = Pattern.compile("(\\d{1,2})/(\\d{1,2})/(\\d{4})");
        Matcher mNoTime = patternNoTime.matcher(date);
        if (mTime.matches() || mNoTime.matches()) { // if input date is in the specific format
            this.date = parseAndFormatDate(date);
        } else {
            this.date = date; // fallback to raw input (e.g. Monday 4pm)
        }
    }

    /**
     * Constructs a Deadline object with a tag.
     *
     * @param description Task description.
     * @param date Task due date.
     * @param tagMsg Tag assigned to the task.
     */
    public Deadline(String description, String date, String tagMsg) {
        super(description, tagMsg);
        // checking if input is in specific date format
        Pattern patternWithTime = Pattern.compile("(\\d{1,2})/(\\d{1,2})/(\\d{4}) (\\d{4})");
        Matcher mTime = patternWithTime.matcher(date);
        Pattern patternNoTime = Pattern.compile("(\\d{1,2})/(\\d{1,2})/(\\d{4})");
        Matcher mNoTime = patternNoTime.matcher(date);
        if (mTime.matches() || mNoTime.matches()) { // if input date is in the specific format
            this.date = parseAndFormatDate(date);
        } else {
            this.date = date; // fallback to raw input (e.g. Monday 4pm)
        }
    }

    /**
     * Returns formatted date when user inputs date in the specific
     * format with time (i.e. d/M/yyyy HHmm).
     * e.g. 12/6/2025 0600 will become Jun 12 2025, 06:00pm
     *
     * @param date Date from input.
     * @return Formatted date.
     */
    private String getDateWithTime(String date) {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        LocalDateTime dateTime = LocalDateTime.parse(date, inputFormat);
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");
        return dateTime.format(outputFormat);
    }

    /**
     * Returns formatted date when user inputs date in the specific
     * format with no time (i.e. d/M/yyyy).
     * e.g. 12/6/2025 will become Jun 12 2025
     *
     * @param date Date from input.
     * @return Formatted date.
     */
    private String getDateNoTime(String date) {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate dateOnly = LocalDate.parse(date, inputFormat);
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM d yyyy");
        return dateOnly.format(outputFormat);
    }

    /**
     * Returns formatted date when user inputs date in a specific
     * format (i.e. d/M/yyyy HHmm or d/M/yyyy).
     * e.g. 12/6/2025 0600 will become Jun 12 2025, 06:00pm
     *
     * @param date Date from input.
     * @return Formatted date.
     */
    public String parseAndFormatDate(String date) {
        try {
            return getDateWithTime(date);
        } catch (DateTimeParseException ignored) {
        }
        try {
            return getDateNoTime(date);
        } catch (DateTimeParseException ignored) {
        }
        return null; // won't happen
    }

    /**
     * Returns the basic content of the task in the specific format required
     * to be stored in the file (i.e. x | x | x ...).
     *
     * @return Basic Task Content.
     */
    String giveBasicFileCont() {
        return "D" + " | " + this.getStatusNumber() + " | " + this.description
                + " | " + this.date;
    }

    @Override
    public String toFileString() {
        String fileContent;

        if (tag == null) {
            fileContent = giveBasicFileCont() + "\n";
        } else {
            fileContent = giveBasicFileCont() + " | " + this.tag.showTagMsg() + "\n";
        }

        return fileContent;
    }

    @Override
    public String getMessage() {
        return "[D]" + super.getMessage() + " (by: " + date + ")";
    }
}
