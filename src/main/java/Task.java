public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false; //start with false
    }

    public void mark() {
        this.isDone = true;
    }
    public void unmark() { this.isDone = false;}

    public String getStatus() {
        return (isDone ? "[X] " : "[ ] ");
    }

    public String getMessage() {
        return getStatus() + this.description;
    }

    public void markMessage(Task t, int num, String input) {
        if (input.startsWith("mark ")) {
            if (t.isDone) {
                System.out.println("\n\tMeow? You've already completed this!");
            } else {
                System.out.println("\n\tMeow! Good job completing this task:");
                t.mark();
                System.out.println("\n\t\t" + t.getMessage());
            }
        } else { //unmark
            if (!t.isDone) {
                System.out.println("\n\tMeow? You've not completed this yet!");
            } else {
                System.out.println("\n\tMeow! I've unmarked this task for you:");
                t.unmark();
                System.out.println("\n\t\t" + t.getMessage());
            }
        }
    }
}
