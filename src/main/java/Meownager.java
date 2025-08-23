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
            System.out.println("\n\t\uD83D\uDC3E Here are your purr-fect tasks:");
            if (index == 0) { //no tasks
                System.out.println("\n\t" + "1. " + null);
            }
            for (int i = 0; i < index; i++) {
                System.out.println("\n\t" + (i + 1) + "." + listOfTasks[i].getMessage());
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
            //adding tasks
            Task t = new Task(input);
            System.out.println("\n\tadded: " + input);
            listOfTasks[index] = t;
            index++; //increment array position
            addList(sc, index, listOfTasks);
        }
    }
}

