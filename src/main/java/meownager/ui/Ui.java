package meownager.ui;

import java.util.ArrayList;

public class Ui {

    public void showGreetings() {
        String logo = "  ( =＾･ω･＾=)ノ <3\n";
        System.out.println("Hello! I'm your resident MEOWnager!\n" + logo
                + "Anything I can do for you neow?\n");
    }

    public void showFarewell() {
        System.out.println("\n\tMeow you next time!");
    }

    public void showError(String errorMsg) {
        System.out.println("\n\t" + errorMsg);
    }

    public void showTaskList(ArrayList<Task> listOfTasks) {
        System.out.println("\n\t\uD83D\uDE3A Here are your tasks, hooman:");
        for (int i = 0; i < listOfTasks.size(); i++) {
            System.out.println("\n\t" + (i + 1) + "." + listOfTasks.get(i).getMessage());
        }
    }

    public void showMarkedMessage(Task t) {
        System.out.println("\n\tMeow! Good job completing this task:");
        System.out.println("\n\t\t" + t.getMessage());
    }

    public void showUnmarkedMessage(Task t) {
        System.out.println("\n\tMeow! I've unmarked this task for you:");
        System.out.println("\n\t\t" + t.getMessage());
    }

    public void showDeletedMessage(Task t, int totalTasks) {
        System.out.println("\n\tPurr-fect! I’ve scratched that task off your list~ \uD83D\uDC3E");
        System.out.println("\n\t\t" + t.getMessage());
        System.out.println("\n\tYou now have " + totalTasks + " tasks left!");
    }

    public void showTaskAdded(Task t, int total) {
        System.out.println("\n\tMeow-K! I've added this task:");
        System.out.println("\n\t\t" + t.getMessage());
        System.out.println("\n\tYou neow have " + total + " tasks in your list.");
    }


}
