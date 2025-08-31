package meownager.ui;

/**
 * Deals with making sense of the user command
 *
 * @author Yu Tingan
 */
public class Parser {
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
}
