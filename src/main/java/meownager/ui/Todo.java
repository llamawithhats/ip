package meownager.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toFileString() {
        String fileContent = "";
        Pattern pattern = Pattern.compile("\\[(.)]\\[(.)] (.+)");
        Matcher m = pattern.matcher(this.getMessage());
        if (m.matches()) {
            String type = m.group(1);
            String status = m.group(2).equals("X") ? "1" : "0"; //1 if X else 0
            String desc = m.group(3);
            fileContent = type + " | " + status + " | " + desc + "\n";
        }
        return fileContent;
    }

    @Override
    public String getMessage() {
        return "[T]" + super.getMessage();
    }

}
