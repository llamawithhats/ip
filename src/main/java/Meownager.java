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
        sc.close();
    }

    public static void greetings() {
        String logo = "  ( =＾･ω･＾=)ノ <3\n";

        System.out.println("Hello! I'm your resident MEOWnager!\n" + logo
                + "Anything I can do for you neow?\n");
    }

    //detects the enum field
    private static TaskType detectType(String input) {
        if (input.startsWith("todo ")) {
            return TaskType.TODO;
        } else if (input.startsWith("deadline ")) {
            return TaskType.DEADLINE;
        } else if (input.startsWith("event ")) {
            return TaskType.EVENT;
        } else {
            return null;
        }
    }

    public static void addList(Scanner sc, int index, Task[] listOfTasks) {
        String input = sc.nextLine();

        if (input.equals("bye")) {
            System.out.println("\n\tMeow you next time!");
        } else if (input.equals("list")) {
            if (index == 0) { //no tasks
                System.out.println("\n\tYou have NO tasks!");
            } else {
                System.out.println("\n\t\uD83D\uDE3A Here are your tasks, hooman:");
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
            Task t = null;
            TaskType type = detectType(input);
            if (type == null) {
                System.out.println("\n\t Meow? I don't understand you.");
            } else {
                switch (type) {
                    case TODO: {
                        String description = input.split("todo ")[1];
                        t = new Todo(description);
                        break;
                    }
                    case DEADLINE: {
                        String description = input.split("deadline |/by")[1].trim();
                        String date = input.split(" /by ")[1]; //get deadline
                        t = new Deadline(description, date);
                        break;
                    }
                    case EVENT: {
                        String description = input.split("event |/from")[1].trim();
                        String from = input.split("/from | /to")[1]; //get from date
                        String to = input.split("/to ")[1]; //get to date
                        t = new Event(description, from, to);
                        break;
                    }
                }
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

