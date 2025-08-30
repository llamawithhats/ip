package meownager.ui;

import java.io.IOException;
import java.util.Scanner;

//NOTE: IdeaProjects -> ip -> src -> main -> java
//sout: System.out.println

public class Meownager {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Meownager(String filePath) {
        storage = new Storage(filePath);
        ui = new Ui();
        try {
            storage.ensureFileExists();
            tasks = new TaskList(storage.loadFile());
        } catch (IOException e) {
            ui.showError("MEOW!! Couldn't create or read the file: " + e.getMessage());
            tasks = new TaskList(); //empty list instead
        }
    }

    public void run() {
        ui.showGreetings();
        Scanner sc = new Scanner(System.in); //get inputs from user
        while (true) {
            String input = sc.nextLine();
            if (input.equals("bye")) {
                exit();
                break;
            }
            handleCommand(input);
        }
        sc.close();
    }

    private void exit() {
        try {
            storage.store(tasks.getListOfTasks());
        } catch (IOException e) { // file doesnt exist (wont happen)
            System.out.println("Something went wrong: " + e.getMessage());
        }
        ui.showFarewell();
    }

    private void handleCommand(String input) {
        try {
            if (input.equals("list")) {
                handleList();
            } else if (input.startsWith("mark ") || input.startsWith("unmark ") || input.startsWith("delete")) {
                handleModifyList(input);
            } else {
                handleAddList(input);
            }
        } catch (MeownagerException e) {
            ui.showError(e.getMessage());
        }
    }

    private void handleList() throws MeownagerException {
        if (tasks.isEmpty()) { //no tasks
            throw MeownagerException.emptyList();
        } else {
            ui.showTaskList(tasks.getListOfTasks());
        }
    }

    private void handleModifyList(String input) throws MeownagerException {
        // finding task no. of input
        int num = Integer.parseInt(input.split(" ")[1]);
        if (num <= 0 || num > tasks.size()) { //not a task number
            throw MeownagerException.outOfBoundsTaskNumber(num);
        }
        Task t = tasks.get(num - 1);
        if (input.startsWith("delete")) {
            tasks.remove(t);
            t.deleteMessage(t, tasks.size());
        } else { //mark, unmark
            t.markMessage(t, input);
        }
    }

    private void handleAddList(String input) throws MeownagerException {
        Task t = null;
        TaskType type = Parser.detectType(input);
        if (type == null) { //invalid input
            throw MeownagerException.unknownCommand();
        }
        switch (type) {
        case TODO:
            if (input.strip().equals("todo")) {
                throw MeownagerException.emptyDescription("todo");
            }
            String descriptionTodo = input.split("todo ")[1];
            t = new Todo(descriptionTodo);
            break;
        case DEADLINE:
            if (input.strip().equals("deadline")) {
                throw MeownagerException.emptyDescription("deadline");
            }
            if (!input.contains("/by")) {
                throw MeownagerException.missingDeadlineInfo();
            }
            String descriptionDead = input.split("deadline |/by")[1].trim();
            String date = input.split("/by")[1].trim(); //get deadline
            t = new Deadline(descriptionDead, date);
            break;
        case EVENT:
            if (input.strip().equals("event")) {
                throw MeownagerException.emptyDescription("event");
            }
            if (!input.contains("/from") || !input.contains("/to")) {
                throw MeownagerException.missingEventInfo();
            }
            String descriptionEve = input.split("event |/from")[1].trim();
            String from = input.split("/from | /to")[1]; //get from date
            String to = input.split("/to ")[1]; //get to date
            t = new Event(descriptionEve, from, to);
            break;
        }
        tasks.add(t); //add task to list
        ui.showTaskAdded(t, tasks.size());
    }

    public static void main(String[] args) {
        new Meownager("./data/Meownager.txt").run();
    }
}

