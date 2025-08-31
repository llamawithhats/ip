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

    public ArrayList<Task> getListOfTasks() {
        return listOfTasks;
    }

    public Task get(int index) {
        return listOfTasks.get(index);
    }

    public void add(Task t) {
        listOfTasks.add(t);
    }

    public void remove(Task t) {
        listOfTasks.remove(t);
    }

    public int size() {
        return listOfTasks.size();
    }

    public boolean isEmpty() {
        return listOfTasks.isEmpty();
    }

    public ArrayList<Task> getFoundTasks(String filter) {
        ArrayList<Task> filteredTasks = new ArrayList<>();
        for (Task t : this.getListOfTasks()) {
            if (t.getMessage().contains(filter)) {
                filteredTasks.add(t);
            }
        }
        return filteredTasks;
    }
}
