package meownager.ui;

import java.util.ArrayList;

/**
 * Represents Meownager interactions with the user.
 *
 * @author Yu Tingan
 */
public class Ui {

    public String showGreetings() {
        String logo = "  ( =＾･ω･＾=)ノ <3\n";
        return "Hello! I'm your resident MEOWnager!\n" + logo
                + "Anything I can do for you neow?\n";
    }

    public String showFarewell() {
        return "Meow you next time!";
    }

    public String showError(String errorMsg) {
        return errorMsg;
    }

    public String showTaskList(ArrayList<Task> listOfTasks) {
        String s = "\uD83D\uDE3A Here are your tasks, hooman:";
        for (int i = 0; i < listOfTasks.size(); i++) {
            s += "\n" + (i + 1) + "." + listOfTasks.get(i).getMessage();
        }
        return s;
    }

    public String showMarkedMessage(Task t) {
        return "Meow! Good job completing this task:"
                + "\n\t" + t.getMessage();
    }

    public String showUnmarkedMessage(Task t) {
        return "Meow! I've unmarked this task for you:"
                + "\n\t" + t.getMessage();
    }

    public String showDeletedMessage(Task t, int totalTasks) {
        return "Purr-fect! I’ve scratched that task off your list~ \uD83D\uDC3E"
                + "\n\t" + t.getMessage()
                + "\nYou now have " + totalTasks + " tasks left!";
    }

    public String showTaskAdded(Task t, int total) {
        return "Meow-K! I've added this task:"
                + "\n\t" + t.getMessage()
                + "\nYou neow have " + total + " tasks in your list.";
    }

    public String showFilteredTasks(ArrayList<Task> listOfTasks) {
        String s = "Paws up! I’ve sniffed out these matching tasks:";
        for (int i = 0; i < listOfTasks.size(); i++) {
            s += "\n" + (i + 1) + "." + listOfTasks.get(i).getMessage();
        }
        return s;
    }

}
