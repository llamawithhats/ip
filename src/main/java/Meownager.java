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
        if (input.startsWith("todo")) {
            return TaskType.TODO;
        } else if (input.startsWith("deadline")) {
            return TaskType.DEADLINE;
        } else if (input.startsWith("event")) {
            return TaskType.EVENT;
        } else {
            return null;
        }
    }

    public static void addList(Scanner sc, int index, Task[] listOfTasks) {
        String input = sc.nextLine();

        if (input.equals("bye")) {
            System.out.println("\n\tMeow you next time!");
            return;
        }

        try {
            if (input.equals("list")) {
                if (index == 0) { //no tasks
                    throw MeownagerException.emptyList();
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
                if (num <= 0 || listOfTasks[num-1] == null) { //not a task number
                    throw MeownagerException.outOfBoundsTaskNumber(num);
                }
                Task t = listOfTasks[num-1];
                t.markMessage(t, num, input);
                addList(sc, index, listOfTasks);
            } else {
                Task t = null;
                TaskType type = detectType(input);
                if (type == null) { //invalid input
                    throw MeownagerException.unknownCommand();
                }
                switch (type) {
                    case TODO: {
                        if (input.strip().equals("todo")) {
                            throw MeownagerException.emptyDescription("todo");
                        }
                        String description = input.split("todo ")[1];
                        t = new Todo(description);
                        break;
                    }
                    case DEADLINE: {
                        if (input.strip().equals("deadline")) {
                            throw MeownagerException.emptyDescription("deadline");
                        }
                        if (!input.contains("/by")) {
                            throw MeownagerException.missingDeadlineInfo();
                        }
                        String description = input.split("deadline |/by")[1].trim();
                        String date = input.split(" /by ")[1]; //get deadline
                        t = new Deadline(description, date);
                        break;
                    }
                    case EVENT: {
                        if (input.strip().equals("event")) {
                            throw MeownagerException.emptyDescription("event");
                        }
                        if (!input.contains("/from") || !input.contains("/to")) {
                            throw MeownagerException.missingEventInfo();
                        }
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
                addList(sc, index, listOfTasks);
            }
        } catch (MeownagerException e) {
                System.out.println("\n\t" + e.getMessage());
                addList(sc, index, listOfTasks);
        }
    }
}

