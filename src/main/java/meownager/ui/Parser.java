package meownager.ui;

import java.util.ArrayList;

/**
 * Deals with making sense of the user command
 *
 * @author Yu Tingan
 */
public class Parser {

    private Ui ui = new Ui();

    static TaskType detectType(String input) {
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

    /**
     * Handles commands by the user ('list', 'mark', 'unmark', 'delete' and addList commands).
     *
     * @param input Input from the user.
     */
    public void handleCommand(String input, TaskList tasks) {
        try {
            if (input.equals("list")) {
                handleList(tasks);
            } else if (input.startsWith("mark ") || input.startsWith("unmark ") || input.startsWith("delete")) {
                handleModifyList(input, tasks);
            } else if (input.startsWith("find")) {
                handleFind(input, tasks);
            } else {
                handleAddList(input, tasks);
            }
        } catch (MeownagerException e) {
            ui.showError(e.getMessage());
        }
    }

    /**
     * Handles 'list' command.
     *
     * @throws MeownagerException If list is empty (no tasks).
     */
    private void handleList(TaskList tasks) throws MeownagerException {
        if (tasks.isEmpty()) { //no tasks
            throw MeownagerException.emptyList();
        } else {
            ui.showTaskList(tasks.getListOfTasks());
        }
    }

    /**
     * Handles 'mark', 'unmark' and 'delete' commands.
     *
     * @param input Input from user.
     * @throws MeownagerException If inputted task number is invalid.
     */
    private void handleModifyList(String input, TaskList tasks) throws MeownagerException {
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

    /**
     * Handles commands that adds to task list.
     *
     * @param input Input from user.
     * @throws MeownagerException If invalid input.
     */
    private void handleAddList(String input, TaskList tasks) throws MeownagerException {
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

    private void handleFind(String input, TaskList tasks) {
        String filter = input.split("find ")[1].trim();
        ArrayList<Task> filteredTasks = tasks.getFoundTasks(filter);
        ui.showFilteredTasks(filteredTasks);
    }
}
