package meownager.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {
    private Task task;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setup() {
        task = new Task("read book");
        System.setOut(new PrintStream(outContent)); // capture System.out for error checks
    }

    @Test
    public void testMarkMessage_whenUnmarked_marksSuccessfully() {
        task.markMessage(task, "mark 1");
        assertTrue(task.isDone);
        assertTrue(outContent.toString().contains("Meow! Good job completing this task:")); // message is printed
    }

    @Test
    public void testMarkMessage_whenAlreadyMarked_showsError() {
        task.mark(); // pre-mark the task
        task.markMessage(task, "mark 1");
        assertTrue(task.isDone); // still marked
        assertTrue(outContent.toString().contains("Meow? You've already completed this!")); // error printed
    }

    @Test
    public void testMarkMessage_whenMarked_unmarksSuccessfully() {
        task.mark(); // pre-mark
        task.markMessage(task, "unmark 1");
        assertFalse(task.isDone);
        assertTrue(outContent.toString().contains("Meow! I've unmarked this task for you:")); // success message
    }

    @Test
    public void testMarkMessage_whenAlreadyUnmarked_showsError() {
        task.markMessage(task, "unmark 1");
        assertFalse(task.isDone); // still unmarked
        assertTrue(outContent.toString().contains("Meow? You've not completed this yet!")); // error printed
    }

}
