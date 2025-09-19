package meownager.ui;

import java.util.ArrayList;

/**
 * Contains the task list.
 * Replicates ArrayList commands.
 *
 * @author Yu Tingan
 */
public class TaskList {
    private ArrayList<Task> listOfTasks;

    /**
     * Constructs a new TaskList with the given list of tasks.
     *
     * @param listOfTasks List of tasks.
     */
    public TaskList(ArrayList<Task> listOfTasks) {
        this.listOfTasks = listOfTasks;
    }

    /**
     * Constructs a new empty TaskList.
     */
    public TaskList() {
        this.listOfTasks = new ArrayList<>();
    }

    /**
     * Returns the list of tasks.
     *
     * @return List of tasks.
     */
    public ArrayList<Task> getListOfTasks() {
        return listOfTasks;
    }

    /**
     * Returns the task at the given index.
     *
     * @param index Index of the task.
     * @return Task at the given index.
     */
    public Task get(int index) {
        return listOfTasks.get(index);
    }

    /**
     * Adds the given task to the list.
     *
     * @param t Task to be added.
     */
    public void add(Task t) {
        listOfTasks.add(t);
    }

    /**
     * Removes the given task from the list.
     *
     * @param t Task to be removed.
     */
    public void remove(Task t) {
        listOfTasks.remove(t);
    }

    /**
     * Returns the size of the list.
     *
     * @return Size of the list.
     */
    public int size() {
        return listOfTasks.size();
    }

    /**
     * Returns true if the list is empty.
     *
     * @return True if the list is empty.
     */
    public boolean isEmpty() {
        return listOfTasks.isEmpty();
    }

}
