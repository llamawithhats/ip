package meownager.ui;

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

    public Todo(String description, String tagMsg) {
        super(description, tagMsg);
    }

    String giveBasicFileCont() {
        return "T" + " | " + this.getStatusNumber() + " | " + this.description;
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
        return "[T]" + super.getMessage();
    }

}
