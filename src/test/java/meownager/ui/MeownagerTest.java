package meownager.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MeownagerTest {
    private Meownager meownager;

    @BeforeEach
    public void setup() {
        meownager = new Meownager("./data/testfile.txt");
    }

    @Test
    public void testAddTodo_validInput_success() throws MeownagerException {
        meownager.handleAddList("todo read book");
        assertEquals(1, meownager.tasks.size());
        assertTrue(meownager.tasks.get(0) instanceof Todo);
    }

    @Test
    public void testAddDeadline_validInput_success() throws MeownagerException {
        meownager.handleAddList("deadline return book /by 2/12/2019 1800");
        assertEquals(1, meownager.tasks.size());
        assertTrue(meownager.tasks.get(0) instanceof Deadline);
    }

    @Test
    public void testAddEvent_validInput_success() throws MeownagerException {
        meownager.handleAddList("event project meeting /from Mon /to Tues");
        assertEquals(1, meownager.tasks.size());
        assertTrue(meownager.tasks.get(0) instanceof Event);
    }

    @Test
    public void testAddTodo_emptyDescription_throwsException() {
        MeownagerException thrown = assertThrows(
                MeownagerException.class,
                () -> meownager.handleAddList("todo"),
                "Expected exception for empty todo description"
        );
        assertTrue(thrown.getMessage().contains("todo"));
    }

    @Test
    public void testAddDeadline_missingByKeyword_throwsException() {
        MeownagerException thrown = assertThrows(
                MeownagerException.class,
                () -> meownager.handleAddList("deadline submit assignment"),
                "Expected exception for missing /by"
        );
        assertTrue(thrown.getMessage().contains("HISSS!! Deadlines need a /by date, nya~"));
    }

    @Test
    public void testAddEvent_missingToKeyword_throwsException() {
        MeownagerException thrown = assertThrows(
                MeownagerException.class,
                () -> meownager.handleAddList("event project /from Mon"),
                "Expected exception for missing /to"
        );
        assertTrue(thrown.getMessage().contains("PURRlease specify /from and /to for events, nya!"));
    }

    @Test
    public void testUnknownCommand_throwsException() {
        MeownagerException thrown = assertThrows(
                MeownagerException.class,
                () -> meownager.handleAddList("blah blah blah"),
                "Expected exception for unknown command"
        );
        assertTrue(thrown.getMessage().contains("MEOW??? I don’t understand that command. Try again, hooman~"));
    }
}
