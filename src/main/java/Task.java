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

    public void markMessage(Task t, String input) {
        try {
            if (input.startsWith("mark ")) {
                if (t.isDone) {
                    throw MeownagerException.alreadyCompleted();
                } else {
                    System.out.println("\n\tMeow! Good job completing this task:");
                    t.mark();
                    System.out.println("\n\t\t" + t.getMessage());
                }
            } else { //unmark
                if (!t.isDone) {
                    throw MeownagerException.stillUncompleted();
                } else {
                    System.out.println("\n\tMeow! I've unmarked this task for you:");
                    t.unmark();
                    System.out.println("\n\t\t" + t.getMessage());
                }
            }
        } catch (MeownagerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteMessage(Task t, int totalTasks) {
        System.out.println("\n\tPurr-fect! I’ve scratched that task off your list~ \uD83D\uDC3E");
        System.out.println("\n\t\t" + t.getMessage());
        System.out.println("\n\tYou now have " + totalTasks + " tasks left!");
    }
}
