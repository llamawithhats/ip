package meownager.ui;

/**
 * Represents a task with description and completion status.
 *
 * @author Yu Tingan
 */
public class Task {
    protected String description;
    protected boolean isDone;
    private Ui ui = new Ui();

    public Task(String description) {
        this.description = description;
        this.isDone = false; // start with false
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

    /**
     * Marks or unmarks the task according to input.
     * If attempt to mark the task when already marked or
     * unmark the task when not yet marked, a MeownagerException is thrown.
     *
     * @param t Task.
     * @param input Input from user.
     */
    public void markMessage(Task t, String input) {
        try {
            if (input.startsWith("mark ")) {
                if (t.isDone) {
                    throw MeownagerException.alreadyCompleted();
                } else {
                    t.mark();
                    ui.showMarkedMessage(t);
                }
            } else { // unmark
                if (!t.isDone) {
                    throw MeownagerException.stillUncompleted();
                } else {
                    t.unmark();
                    ui.showUnmarkedMessage(t);
                }
            }
        } catch (MeownagerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Show deleted message when task is deleted.
     *
     * @param t Task.
     * @param totalTasks Total number of tasks.
     */
    public void deleteMessage(Task t, int totalTasks) {
        ui.showDeletedMessage(t, totalTasks);
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
     * Returns the message of the task to be displayed.
     * E.g. [X] read book
     *
     * @return Task Message.
     */
    public String getMessage() {
        return getStatus() + this.description;
    }
}
