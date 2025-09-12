package meownager.ui;

/**
 * Represents a task with description and completion status.
 *
 * @author Yu Tingan
 */
public class Task {
    String description;
    protected boolean isDone;
    private Ui ui = new Ui();
    Tag tag;

    /**
     * Constructs a Task object with no tag.
     *
     * @param description Task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false; // start with false
        this.tag = null;
    }

    /**
     * Constructs a Task object with a tag.
     *
     * @param description Task description.
     * @param tagMsg Tag assigned to the task.
     */
    public Task(String description, String tagMsg) {
        this.description = description;
        this.isDone = false; // start with false
        this.tag = new Tag(tagMsg);
    }

    public void mark() {
        this.isDone = true;
    }
    public void unmark() {
        this.isDone = false;
    }

    public String getStatus() {
        return (isDone ? "[X] " : "[ ] ");
    }

    public String getStatusNumber() {
        return isDone ? "1" : "0";
    }

    private String mark(Task t) throws MeownagerException {
        if (t.isDone) {
            throw MeownagerException.alreadyCompleted();
        } else {
            t.mark();
            return ui.showMarkedMessage(t);
        }
    }

    private String unmark(Task t) throws MeownagerException {
        if (!t.isDone) {
            throw MeownagerException.stillUncompleted();
        } else {
            t.unmark();
            return ui.showUnmarkedMessage(t);
        }
    }

    /**
     * Marks or unmarks the task according to input.
     * If attempt to mark the task when already marked or
     * unmark the task when not yet marked, a MeownagerException is thrown.
     *
     * @param t Task.
     * @param input Input from user.
     */
    public String markMessage(Task t, String input) {
        boolean isMark = input.startsWith("mark ");
        boolean isUnmark = input.startsWith("unmark ");
        try {
            if (isMark) {
                return mark(t);
            } else if (isUnmark) {
                return unmark(t);
            } else {
                throw new MeownagerException("incorrect input!");
            }
        } catch (MeownagerException e) {
            return ui.showError(e.getMessage());
        }
    }

    /**
     * Returns the content of the task in the specific format required
     * to be stored in the file (i.e. x | x | x ...).
     *
     * @return Task Content
     */
    public String toFileString() {
        return null;
    };

    /**
     * Deletes tag belonging to the task.
     */
    public void deleteTag() {
        this.tag = null;
    }

    public void editTag(String newTagMsg) {
        if (this.tag == null) {
            this.tag = new Tag(newTagMsg);
        } else {
            this.tag.editTag(newTagMsg);
        }
    }

    /**
     * Returns the message of the task to be displayed.
     * E.g. [X] read book
     *
     * @return Task Message.
     */
    public String getMessage() {
        if (this.tag == null) {
            return getStatus() + this.description;
        }
        return getStatus() + "*" + this.tag.showTag() + "* " + this.description;
    }
}
