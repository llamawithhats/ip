package meownager.ui;

import java.util.ArrayList;

/**
 * Represents Meownager interactions with the user.
 *
 * @author Yu Tingan
 */
public class Ui {

    /**
     * Greets the user.
     *
     * @return Greeting message.
     */
    public String showGreetings() {
        String logo = "  ( =＾･ω･＾=)ノ <3\n";
        return "Hello! I'm your resident MEOWnager!\n" + logo
                + "Anything I can do for you neow?\n"
                + "(if require command meowsistance, type '/help')";
    }

    public String showFarewell() {
        return "Meow you next time!";
    }

    public String showError(String errorMsg) {
        return errorMsg;
    }

    /**
     * Displays the list of tasks.
     *
     * @param listOfTasks List of tasks to be displayed.
     * @return Formatted string of tasks.
     */
    public String showTaskList(ArrayList<Task> listOfTasks) {
        String s = "\uD83D\uDE3A Here are your tasks, hooman:";
        for (int i = 0; i < listOfTasks.size(); i++) {
            s += "\n" + (i + 1) + "." + listOfTasks.get(i).getMessage();
        }
        return s;
    }

    /**
     * Displays a message when a task is marked as completed.
     *
     * @param t The task that was marked.
     * @return Formatted string indicating the task was marked.
     */
    public String showMarkedMessage(Task t) {
        return "Meow! Good job completing this task:"
                + "\n\t" + t.getMessage();
    }

    /**
     * Displays a message when a task is unmarked as completed.
     *
     * @param t The task that was unmarked.
     * @return Formatted string indicating the task was unmarked.
     */
    public String showUnmarkedMessage(Task t) {
        return "Meow! I've unmarked this task for you:"
                + "\n\t" + t.getMessage();
    }

    /**
     * Displays a message when a task is deleted.
     *
     * @param t The task that was deleted.
     * @param totalTasks The total number of tasks remaining.
     * @return Formatted string indicating the task was deleted and the total tasks left.
     */
    public String showDeletedMessage(Task t, int totalTasks) {
        return "Purr-fect! I’ve scratched that task off your list~ \uD83D\uDC3E"
                + "\n\t" + t.getMessage()
                + "\nYou now have " + totalTasks + " tasks left!";
    }

    public String showEditedTask() {
        return "This is your edited task: ";
    }

    /**
     * Displays a message when a tag is deleted from a task.
     *
     * @param t The task from which the tag was deleted.
     * @return Formatted string indicating the tag was deleted and showing the edited task.
     */
    public String showDeletedTag(Task t) {
        return "Hehe, I've clawed away that tag~ \uD83D\uDC3E \n"
                + showEditedTask()
                + "\n\t" + t.getMessage();
    }

    /**
     * Displays a message when a tag is edited or added to a task.
     *
     * @param t The task to which the tag was added or edited.
     * @return Formatted string indicating the tag was edited/added and showing the edited task.
     */
    public String showEditedTag(Task t) {
        return "Meow! I've edited your tag message~ \n"
                + showEditedTask()
                + "\n\t" + t.getMessage();
    }

    public String showTaskAdded(Task t, int total) {
        return "Meow-K! I've added this task:"
                + "\n\t" + t.getMessage()
                + "\nYou neow have " + total + " tasks in your list.";
    }

    /**
     * Displays a list of tasks that match a certain filter.
     *
     * @param listOfTasks List of tasks that match the filter.
     * @return Formatted string of matching tasks.
     */
    public String showFilteredTasks(ArrayList<Task> listOfTasks) {
        String s = "Paws up! I’ve sniffed out these matching tasks:";
        for (int i = 0; i < listOfTasks.size(); i++) {
            s += "\n" + (i + 1) + "." + listOfTasks.get(i).getMessage();
        }
        return s;
    }

    private String addTasks() {
        return "To add tasks: \n"
                + "\t-> todo: 'todo {task}'\n"
                + "\t-> deadline: 'deadline {task} /by {date}'\n"
                + "\t-> event: 'event {task} /from {start time} /to {end time}'\n\n";
    }

    private String tagCommands() {
        return "Want to add tags?\n"
                + "\t-> include '/tag {tag msg}' at the end of your task!\n"
                + "Delete tags?\n"
                + "\t-> 'deltag {task no.}'\n"
                + "Edit tags/introduce tags to existing task?\n"
                + "\t-> 'edittag {task no.} {new tag msg}'\n"
                + "Find tags?\n"
                + "\t-> 'findtag {tag msg}'\n\n";
    }

    private String otherCommands() {
        return "To get list of tasks: \n\t-> 'list'\n"
                + "To mark/unmark a task: \n\t-> 'mark {task no.}' / 'unmark {task no.}'\n"
                + "To delete a task: \n\t-> 'delete {task no.}'\n"
                + "To find a task: \n\t-> 'find {content}'\n\n";
    }

    private String byeCommand() {
        return "Done with your tasks?\n\t-> 'bye' \nto exit the program!";
    }

    private String divider() {
        return "____________________________________\n";
    }

    /**
     * Displays the command book to assist users with available commands.
     *
     * @return Formatted string of the command book.
     */
    public String showCommandBook() {
        String s = "Lost in the litter of commands? Here’s your paw-some guide! \uD83D\uDC31 \n\n";
        s += addTasks() + divider();
        s += tagCommands() + divider();
        s += otherCommands() + divider();
        s += byeCommand();
        return s;
    }

}
