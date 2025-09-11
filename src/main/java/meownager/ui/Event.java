package meownager.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an event task with a due date.
 *
 * Extends parent Task class and adds support for storing and displaying
 * a specific to and from date (e.g. "project meeting (from: Monday 2pm to 4pm)")
 *
 * @author Yu Tingan
 */
public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toFileString() {
        String status = this.getStatusNumber();
        String desc = this.description;
        String from = this.from;
        String to = this.to;
        String fileContent = "E" + " | " + status + " | " + desc + " | "
                + from + " | " + to + "\n";
        return fileContent;
    }

    @Override
    public String getMessage() {
        return "[E]" + super.getMessage() + " (from: " + from + " to: " + to + ")";
    }
}
