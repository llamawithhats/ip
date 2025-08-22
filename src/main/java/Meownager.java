import java.util.Scanner;

//NOTE: IdeaProjects -> ip -> src -> main -> java
//sout: System.out.println

public class Meownager {

    public static void greetings() {
        String logo = "  ( =＾･ω･＾=)ノ <3\n";

        System.out.println("Hello! I'm your resident MEOWnager!\n" + logo
                            + "Anything I can do for you neow?\n");
    }

    public static void addList(Scanner sc, int index, String[] listOfTasks) {
        String input = sc.nextLine();

        if (input.equals("bye")) {
            System.out.println("\n\tMeow you next time!");
        } else if (input.equals("list")) {
            if (index == 0) {
                System.out.println("\n\t" + "1. " + null);
            }
            for (int i = 0; i < index; i++) {
                System.out.println("\n\t" + (i+1) + ". " + listOfTasks[i]);
            }
            addList(sc, index, listOfTasks); //repeat inputs
        } else {
            System.out.println("\n\tadded: " + input);
            listOfTasks[index] = input;
            index++; //increment array position
            addList(sc, index, listOfTasks);
        }
    }

    public static void main(String[] args) {
        greetings();
        Scanner sc = new Scanner(System.in);
        String[] listOfTasks = new String[100];
        addList(sc, 0, listOfTasks);
    }

}

