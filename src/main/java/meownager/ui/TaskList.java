package meownager.ui;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> listOfTasks;

    public TaskList(ArrayList<Task> listOfTasks) {
        this.listOfTasks = listOfTasks;
    }

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

}
