package meownager.ui;

public class Task {
    protected String description;
    protected boolean isDone;
    private Ui ui = new Ui();

    public Task(String description) {
        this.description = description;
        this.isDone = false; //start with false
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

    public String getMessage() {
        return getStatus() + this.description;
    }

    public void markMessage(Task t, String input) {
        try {
            if (input.startsWith("mark ")) {
                if (t.isDone) {
                    throw MeownagerException.alreadyCompleted();
                } else {
                    t.mark();
                    ui.showMarkedMessage(t);
                }
            } else { //unmark
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

    public void deleteMessage(Task t, int totalTasks) {
        ui.showDeletedMessage(t, totalTasks);
    }

    public String toFileString() {
        return null;
    };
}
