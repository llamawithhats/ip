package meownager.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Deals with making sense of the user command.
 *
 * @author Yu Tingan
 */
public class Parser {

    private Ui ui = new Ui();

    /**
     * Detects the type of task to be added.
     *
     * @param input Input from user.
     * @return Type of task to be added.
     */
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
     * Checks if user wants to exit the program.
     *
     * @param input Input from user.
     * @return True if user wants to exit, false otherwise.
     */
    boolean isByeCommand(String input) {
        return input.equals("bye");
    }

    /**
     * Checks if user wants to list all tasks.
     *
     * @param input Input from user.
     * @return True if user wants to list all tasks, false otherwise.
     */
    boolean isListCommand(String input) {
        return input.equals("list");
    }


    boolean isMarkCommand(String input) {
        return input.startsWith("mark ");
    }
    boolean isUnmarkCommand(String input) {
        return input.startsWith("unmark ");
    }
    boolean isMarkOrUnmark(String input) {
        return isUnmarkCommand(input) || isMarkCommand(input);
    }
    boolean isDeleteCommand(String input) {
        return input.startsWith("delete") || input.startsWith("deltag");
    }
    boolean isEditTagCommand(String input) {
        return input.startsWith("edittag");
    }

    /**
     * Checks if user wants to modify the task list (mark, unmark, delete, edit tag).
     *
     * @param input Input from user.
     * @return True if user wants to modify the task list, false otherwise.
     */
    boolean isModifyCommand(String input) {
        return isMarkOrUnmark(input) || isDeleteCommand(input)
                || isEditTagCommand(input);
    }

    /**
     * Checks if user wants to find tasks.
     *
     * @param input Input from user.
     * @return True if user wants to find tasks, false otherwise.
     */
    boolean isFindCommand(String input) {
        return input.startsWith("find") || input.startsWith("findtag");
    }

    /**
     * Checks if user wants to see the command book.
     *
     * @param input Input from user.
     * @return True if user wants to see the command book, false otherwise.
     */
    boolean isHelpCommand(String input) {
        return input.trim().equals("/help");
    }

    /**
     * Handles commands by the user ('list', 'mark', 'unmark', 'delete' and addList commands).
     *
     * @param input Input from the user.
     * @param tasks TaskList containing list of tasks.
     * @param storage Storage containing file path to store tasks.
     * @return Response to be shown to user.
     */
    public String handleCommand(String input, TaskList tasks, Storage storage) {
        try {
            if (isByeCommand(input)) {
                return exit(tasks, storage);
            } else if (isListCommand(input)) {
                return handleList(tasks);
            } else if (isModifyCommand(input)) {
                return handleModifyList(input, tasks);
            } else if (isFindCommand(input)) {
                return handleFind(input, tasks);
            } else if (isHelpCommand(input)) {
                return handleHelp();
            } else {
                return handleAddList(input, tasks);
            }
        } catch (MeownagerException e) {
            return ui.showError(e.getMessage());
        }
    }

    /**
     * Exits the program and stores tasks into system file.
     *
     * @param tasks TaskList containing list of tasks.
     * @param storage Storage containing file path to store tasks.
     * @return Farewell message.
     */
    private String exit(TaskList tasks, Storage storage) {
        try {
            storage.store(tasks.getListOfTasks());
        } catch (IOException e) { // file doesnt exist (wont happen)
            return ui.showError("Something went wrong: " + e.getMessage());
        }
        return ui.showFarewell();
    }

    /**
     * Handles 'list' command.
     *
     * @param tasks TaskList containing list of tasks.
     * @return Message showing list of tasks.
     * @throws MeownagerException If list is empty (no tasks).
     */
    private String handleList(TaskList tasks) throws MeownagerException {
        if (tasks.isEmpty()) { // no tasks
            throw MeownagerException.emptyList();
        } else {
            return ui.showTaskList(tasks.getListOfTasks());
        }
    }

    /**
     * Checks if a string is a number.
     *
     * @param s String to be checked.
     * @return True if string is a number, false otherwise.
     */
    public static boolean isNumber(String s) {
        return s.matches("\\d+");
    }

    /**
     * Finds the task number from user input.
     *
     * @param input Input from user.
     * @return Task number.
     */
    private int findTaskNumber(String input) throws MeownagerException {
        String wantedPart = input.split(" ")[1];
        if (!isNumber(wantedPart)) {
            throw MeownagerException.isNotInteger();
        }
        return Integer.parseInt(input.split(" ")[1]);
    }

    /**
     * Deletes a task from the task list.
     *
     * @param t Task to be deleted.
     * @param tasks TaskList containing list of tasks.
     * @return Message showing deleted task.
     */
    private String deleteTask(Task t, TaskList tasks) {
        tasks.remove(t);
        return ui.showDeletedMessage(t, tasks.size());
    }

    /**
     * Deletes the tag from a task.
     *
     * @param t Task whose tag is to be deleted.
     * @return Message showing deleted tag.
     */
    private String deleteTag(Task t) {
        t.deleteTag();
        return ui.showDeletedTag(t);
    }

    /**
     * Edits the tag of a task.
     *
     * @param input Input from user.
     * @param t Task whose tag is to be edited.
     * @return Message showing edited tag.
     */
    private String editTag(String input, Task t) {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            return ui.showError("Missing new tag msg!");
        }
        String newTagMsg = parts[2];
        t.editTag(newTagMsg);
        return ui.showEditedTag(t);
    }

    /**
     * Handles modify list commands.
     *
     * @param input Input from user.
     * @param tasks TaskList containing list of tasks.
     * @return Message showing modified task.
     * @throws MeownagerException If inputted task number is invalid or not a number.
     */
    private String handleModifyList(String input, TaskList tasks) throws MeownagerException {
        int num = findTaskNumber(input);
        if (num <= 0 || num > tasks.size()) { // not a task number
            throw MeownagerException.outOfBoundsTaskNumber(num);
        }
        Task t = tasks.get(num - 1);
        if (input.startsWith("delete")) {
            return deleteTask(t, tasks);
        } else if (input.startsWith("deltag")) {
            return deleteTag(t);
        } else if (isEditTagCommand(input)) {
            return editTag(input, t);
        } else {
            assert isMarkOrUnmark(input) : "Should be mark/unmark command";
            return t.markMessage(t, input);
        }
    }


    private boolean hasTag(String input) {
        return input.contains("/tag");
    }

    private String getTagMsg(String input) {
        return input.split("/tag")[1].trim();
    }

    /**
     * Creates a Todo task from user input.
     *
     * @param input Input from the user.
     * @return Todo task.
     * @throws MeownagerException If description is empty.
     */
    private Task getTodo(String input) throws MeownagerException {
        if (input.strip().equals("todo")) {
            throw MeownagerException.emptyDescription("todo");
        }
        String descr = input.split("todo |/tag")[1].trim();
        if (hasTag(input)) {
            return new Todo(descr, getTagMsg(input));
        }
        return new Todo(descr);
    }

    /**
     * Creates a Deadline task from user input.
     *
     * @param input Input from the user.
     * @return Deadline task.
     * @throws MeownagerException If description is empty or missing /by info or wrong date format.
     */
    private Task getDeadline(String input) throws MeownagerException {
        if (input.strip().equals("deadline")) {
            throw MeownagerException.emptyDescription("deadline");
        }
        if (!input.contains("/by")) {
            throw MeownagerException.missingDeadlineInfo();
        }
        String descr = input.split("deadline |/by")[1].trim();
        String date = input.split("/by |/tag")[1].trim();
        if (!Deadline.isCorrectDateFormat(date)) {
            throw MeownagerException.wrongDateFormat();
        }
        if (hasTag(input)) {
            return new Deadline(descr, date, getTagMsg(input));
        }
        return new Deadline(descr, date);
    }

    /**
     * Creates an Event task from user input.
     *
     * @param input Input from the user.
     * @return Event task.
     * @throws MeownagerException If description is empty or missing /from or /to info.
     */
    private Task getEvent(String input) throws MeownagerException {
        if (input.strip().equals("event")) {
            throw MeownagerException.emptyDescription("event");
        }
        if (!input.contains("/from") || !input.contains("/to")) {
            throw MeownagerException.missingEventInfo();
        }
        String descr = input.split("event |/from")[1].trim();
        String from = input.split("/from | /to")[1]; // get from date
        String to = input.split("/to | /tag")[1]; // get to date
        if (hasTag(input)) {
            return new Event(descr, from, to, getTagMsg(input));
        }
        return new Event(descr, from, to);
    }

    /**
     * Handles commands that adds to task list.
     *
     * @param input Input from user.
     * @param tasks TaskList containing list of tasks.
     * @return Message showing task added.
     * @throws MeownagerException If invalid input.
     */
    private String handleAddList(String input, TaskList tasks) throws MeownagerException {
        Task t = null;
        TaskType type = Parser.detectType(input);
        if (type == null) { // invalid input
            throw MeownagerException.unknownCommand();
        }
        switch (type) {
        case TODO:
            t = getTodo(input);
            break;
        case DEADLINE:
            t = getDeadline(input);
            break;
        case EVENT:
            t = getEvent(input);
            break;
        }
        tasks.add(t); // add task to list
        return ui.showTaskAdded(t, tasks.size());
    }

    /**
     * Handles 'find' commands.
     *
     * @param input Input from user.
     * @param tasks TaskList containing list of tasks.
     * @return Message showing filtered tasks.
     * @throws MeownagerException If no matching tasks found.
     */
    private String findTasksWithContent(String input, TaskList tasks) throws MeownagerException {
        String filter = input.split("find")[1].trim();
        ArrayList<Task> filteredTasks = (ArrayList<Task>) tasks.getListOfTasks()
                .stream()
                .filter((t) -> t.getMessage().contains(filter))
                .collect(Collectors.toList());
        if (filteredTasks.isEmpty()) {
            throw MeownagerException.noMatchingTasks();
        }
        return ui.showFilteredTasks(filteredTasks);
    }

    /**
     * Handles 'findtag' commands.
     *
     * @param input Input from user.
     * @param tasks TaskList containing list of tasks.
     * @return Message showing filtered tasks with matching tags.
     * @throws MeownagerException If no matching tags found.
     */
    private String findTaskWithTag(String input, TaskList tasks) throws MeownagerException {
        String filter = input.split("findtag")[1].trim();
        ArrayList<Task> filteredTasks = (ArrayList<Task>) tasks.getListOfTasks()
                .stream()
                .filter((t) -> t.hasTag())
                .filter((t) -> t.getTagMsg().contains(filter))
                .collect(Collectors.toList());
        if (filteredTasks.isEmpty()) {
            throw MeownagerException.noMatchingTags();
        }
        return ui.showFilteredTasks(filteredTasks);
    }

    /**
     * Handles 'find' and 'findtag' commands.
     *
     * @param input Input from user.
     * @param tasks TaskList containing list of tasks.
     * @return Message showing filtered tasks.
     * @throws MeownagerException If no matching tasks or tags found.
     */
    private String handleFind(String input, TaskList tasks) throws MeownagerException {
        if (input.startsWith("findtag")) {
            return findTaskWithTag(input, tasks);
        }
        return findTasksWithContent(input, tasks);
    }

    /**
     * Handles '/help' command.
     *
     * @return Command book message.
     */
    private String handleHelp() {
        return ui.showCommandBook();
    }

}
