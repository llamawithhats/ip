package meownager.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String fileContent = "";
        Pattern pattern = Pattern.compile("\\[(.)]\\[(.)] (.+) \\(from: (.+) to: (.+)\\)");
        Matcher m = pattern.matcher(this.getMessage());
        if (m.matches()) {
            String type = m.group(1);
            String status = m.group(2).equals("X") ? "1" : "0";
            String desc = m.group(3);
            String from = m.group(4);
            String to = m.group(5);
            fileContent += type + " | " + status + " | " + desc + " | "
                    + from + " | " + to + "\n";
        }
        return fileContent;
    }

    @Override
    public String getMessage() {
        return "[E]" + super.getMessage() + " (from: " + from + " to: " + to + ")";
    }
}
