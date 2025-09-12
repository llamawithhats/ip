package meownager.ui;

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

    public Event(String description, String from, String to, String tagMsg) {
        super(description, tagMsg);
        this.from = from;
        this.to = to;
    }

    String giveBasicFileCont() {
        return "E" + " | " + this.getStatusNumber() + " | " + this.description
                + " | " + this.from + " | " + this.to;
    }

    @Override
    public String toFileString() {
        String fileContent;

        if (this.tag == null) {
            fileContent = giveBasicFileCont() + "\n";
        } else {
            fileContent = giveBasicFileCont() + " | " + this.tag.showTag() + "\n";
        }

        return fileContent;
    }

    @Override
    public String getMessage() {
        return "[E]" + super.getMessage() + " (from: " + from + " to: " + to + ")";
    }
}
