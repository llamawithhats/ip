public class Event extends Task {
    TaskType type = TaskType.EVENT;
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getMessage() {
        return "[E]" + super.getMessage() + " (from: " + from + " to: " + to + ")";
    }
}
