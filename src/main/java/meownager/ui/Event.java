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

    /**
     * Constructs an Event object with no tag.
     *
     * @param description Task description.
     * @param from Task start date.
     * @param to Task end date.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Constructs an Event object with a tag.
     *
     * @param description Task description.
     * @param from Task start date.
     * @param to Task end date.
     * @param tagMsg Tag assigned to the task.
     */
    public Event(String description, String from, String to, String tagMsg) {
        super(description, tagMsg);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the basic content of the task in the specific format required
     * to be stored in the file (i.e. x | x | x ...).
     *
     * @return Basic Task Content.
     */
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
            fileContent = giveBasicFileCont() + " | " + this.tag.showTagMsg() + "\n";
        }
        return fileContent;
    }

    @Override
    public String getMessage() {
        return "[E]" + super.getMessage() + " (from: " + from + " to: " + to + ")";
    }
}
