package meownager.ui;

import java.io.IOException;
import java.util.Scanner;

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
            tasks = new TaskList(); // empty list instead
        }
    }

    /**
     * Runs the program.
     */
    public String run(String input) {
        return parser.handleCommand(input, tasks, storage);
    }


}

