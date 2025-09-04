package meownager.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//NOTE: IdeaProjects -> ip -> src -> main -> java
//sout: System.out.println

/**
 * Represents a task organiser system to help keep track of tasks.
 *
 * @author Yu Tingan
 */
public class Meownager {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;
    private static final String FILE_PATH = "./data/Meownager.txt";

    /**
     * Constructs a new Meownager object with the given file path
     * where previous history may be stored.
     *
     * Initialises storage to contain file path and ui.
     * Assigns tasks to be the list of previous execution if available.
     *
     * @param filePath Path for file with stored content.
     */
    public Meownager(String filePath) {
        storage = new Storage(filePath);
        ui = new Ui();
        parser = new Parser();
        
        try {
            storage.ensureFileExists();
            tasks = new TaskList(storage.loadFile());
        } catch (IOException e) {
            ui.showError("MEOW!! Couldn't create or read the file: " + e.getMessage());
            tasks = new TaskList(); //empty list instead
        }
    }

    /**
     * Runs the program.
     * Includes greetings and scanner to parse input from user.
     */
    public void run() {
        ui.showGreetings();
        Scanner sc = new Scanner(System.in); //get inputs from user
        while (true) {
            String input = sc.nextLine();
            if (input.equals("bye")) {
                exit();
                break;
            }
            parser.handleCommand(input, tasks);
        }
        sc.close();
    }

    /**
     * Exits the program and stores tasks into system file.
     */
    private void exit() {
        try {
            storage.store(tasks.getListOfTasks());
        } catch (IOException e) { // file doesnt exist (wont happen)
            System.out.println("Something went wrong: " + e.getMessage());
        }
        ui.showFarewell();
    }

    public static void main(String[] args) {
        new Meownager(FILE_PATH).run();
    }
}

