import java.util.Scanner;

//NOTE: IdeaProjects -> ip -> src -> main -> java
public class Meownager {

    public static void greetings() {
        String logo = "  ( =＾･ω･＾=)ノ <3\n";

        System.out.println("Hello! I'm your resident MEOWnager!\n" + logo
                            + "Anything I can do for you neow?\n");
    }


    public static void echo(Scanner sc) {
        String input = sc.nextLine();

        if (input.equals("bye")) {
            System.out.println("\n\tMeow you next time!");
        } else {
            System.out.println("\n\t" + input);
            echo(sc);
        }
    }

    public static void main(String[] args) {

        greetings();
        Scanner sc = new Scanner(System.in);
        echo(sc);

    }
}

