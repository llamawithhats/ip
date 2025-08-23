import java.sql.SQLOutput;
import java.util.Scanner;

//NOTE: IdeaProjects -> ip -> src -> main -> java
//sout: System.out.println

public class Meownager {

    public static void main(String[] args) {
        greetings();
        Scanner sc = new Scanner(System.in); //get inputs from user
        Task[] listOfTasks = new Task[100]; //to track list of tasks
        addList(sc, 0, listOfTasks);
    }

    public static void greetings() {
        String logo = "  ( =＾･ω･＾=)ノ <3\n";

        System.out.println("Hello! I'm your resident MEOWnager!\n" + logo
                + "Anything I can do for you neow?\n");
    }

    public static void addList(Scanner sc, int index, Task[] listOfTasks) {
        String input = sc.nextLine();

        if (input.equals("bye")) {
            System.out.println("\n\tMeow you next time!");
        } else if (input.equals("list")) {
            if (index == 0) { //no tasks
                System.out.println("\n\tYou have NO tasks!");
            } else {
                System.out.println("\n\t\uD83D\uDC3E Here are your purr-fect tasks:");
                for (int i = 0; i < index; i++) {
                    System.out.println("\n\t" + (i + 1) + "." + listOfTasks[i].getMessage());
                }
            }
            addList(sc, index, listOfTasks); //repeat inputs
        } else if (input.startsWith("mark ") || input.startsWith("unmark ")) {
            // marking, unmarking
            // finding task no.
            int num = Integer.parseInt(input.split(" ")[1]); //split makes it a string[],
            // parseint converts to int
            //NOTE task 1 is index 0 in list (num-1)
            Task t = listOfTasks[num-1];
            t.markMessage(t, num, input);
            addList(sc, index, listOfTasks);
        } else {
            Task t = new Task(null);
            if (input.startsWith("todo "))  {
                t = new Todo(input);
                listOfTasks[index] = t;
            } else if (input.startsWith("deadline ")) {
                String description = input.split("deadline |/by")[1].trim();
                String date = input.split(" /by ")[1]; //get deadline
                t = new Deadline(description, date);
            } else if (input.startsWith("event ")) {
                String description = input.split("event |/from")[1].trim();
                String from = input.split("/from | /to")[1]; //get from date
                String to = input.split("/to ")[1]; //get to date
                t = new Event(description, from, to);
            } else {
                System.out.println("\n\t Meow? I don't understand you.");
            }
            if (t.description != null) { //if valid input
                listOfTasks[index] = t; //add task to list
                System.out.println("\n\tMeow-K! I've added this task:");
                System.out.println("\n\t\t" + t.getMessage());
                index++;//increment array position
                System.out.println("\n\tYou neow have " + index + " tasks in your list.");
            }
            addList(sc, index, listOfTasks);
        }
    }
}

