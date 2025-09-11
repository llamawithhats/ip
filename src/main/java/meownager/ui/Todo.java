package meownager.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a todo task.
 *
 * Extends parent Task class directly with no added support.
 *
 * @author Yu Tingan
 */
public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toFileString() {
        String status = this.getStatusNumber();
        assert (status.equals("0") || status.equals("1"));
        String desc = this.description;
        String fileContent = "T" + " | " + status + " | " + desc + "\n";
        return fileContent;
    }

    @Override
    public String getMessage() {
        return "[T]" + super.getMessage();
    }

}
