package meownager.ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Loads tasks from the file and saves tasks to the file.
 * Ensures previous task list history is kept when Meownager restarts.
 *
 * @author Yu Tingan
 */
public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Ensures that the file and its folder exists before
     * an attempt is made to load it.
     *
     * @throws IOException If file can not be created.
     */
    public void ensureFileExists() throws IOException {
        File f = new File(this.filePath);
        if (!f.exists()) {
            File parent = f.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdir();
            }
            // create file if no exist
            f.createNewFile();
            System.out.println("New file created at: " + filePath);
        }
    }

    private boolean isTodo(String[] parts) {
        return parts[0].equals("T");
    }

    private boolean isDeadline(String[] parts) {
        return parts[0].equals("D");
    }

    private boolean isEvent(String[] parts) {
        return parts[0].equals("E");
    }

    private boolean isDone(String[] parts) {
        return parts[1].equals("1");
    }

    private boolean isUndone(String[] parts) {
        return parts[1].equals("0");
    }

    /**
     * Checks if loaded task from storage file is tagged.
     *
     * @param parts Parts from the file content.
     * @return True if have tag, else false.
     */
    private boolean hasTag(String[] parts) {
        int size = parts.length;
        // tags are at the back of the line
        if (isTodo(parts) && size == 4) { // if no tag, size is 3 (fixed)
            return true;
        } else if (isDeadline(parts) && size == 5) { // if no tag, size is 4 (fixed)
            return true;
        } else if (isEvent(parts) && size == 6) { // if no tag, size is 5 (fixed)
            return true;
        } else {
            return false;
        }
    }

    private String retrieveTag(String[] parts) {
        return parts[parts.length - 1];
    }

    /**
     * Returns corresponding Todo object based on string line in storage file.
     *
     * @param parts Array of different parts of each file lines.
     * @return Task assigned.
     */
    private Task assignTodo(String[] parts) {
        String desc = parts[2];
        if (hasTag(parts)) {
            return new Todo(desc, retrieveTag(parts));
        } else {
            return new Todo(desc);
        }
    }

    /**
     * Returns corresponding Deadline object based on string line in storage file.
     *
     * @param parts Array of different parts of each file lines.
     * @return Task assigned.
     */
    private Task assignDead(String[] parts) {
        String desc = parts[2];
        String date = parts[3];
        if (hasTag(parts)) {
            return new Deadline(desc, date, retrieveTag(parts));
        } else {
            return new Deadline(desc, date);
        }
    }

    /**
     * Returns corresponding Event object based on string line in storage file.
     *
     * @param parts Array of different parts of each file lines.
     * @return Task assigned.
     */
    private Task assignEvent(String[] parts) {
        String desc = parts[2];
        String from = parts[3];
        String to = parts[4];
        if (hasTag(parts)) {
            return new Event(desc, from, to, retrieveTag(parts));
        } else {
            return new Event(desc, from, to);
        }
    }

    /**
     * Returns corresponding type of Task object based on
     * string line in storage file.
     *
     * @param parts Array of different parts of each file lines.
     * @return Task assigned.
     */
    private Task assignTask(String[] parts) {
        Task t;
        if (isTodo(parts)) {
            t = assignTodo(parts);
        } else if (isDeadline(parts)) {
            t = assignDead(parts);
        } else {
            assert isEvent(parts); // assertions
            t = assignEvent(parts);
        }
        assert (isDone(parts) || isUndone(parts)); // assertions
        if (isDone(parts)) {
            t.mark();
        }
        return t;
    }

    /**
     * Returns the task list from the stored file.
     *
     * @return Task list from previous execution.
     * @throws IOException If file can not be created or read.
     */
    public ArrayList<Task> loadFile() throws IOException {
        ArrayList<Task> listOfTasks = new ArrayList<>();
        File f = new File(this.filePath);
        Scanner s = new Scanner(f);
        // add previous tasks into new arraylist
        while (s.hasNext()) {
            String line = s.nextLine().trim();
            if (line.isEmpty()) continue; // skip blank lines

            String[] parts = line.split(" \\| ");
            Task t = assignTask(parts);

            listOfTasks.add(t);
        }
        return listOfTasks;
    }

    /**
     * Stores the current task list into the dedicated folder.
     *
     * @param listOfTasks Task list from execution.
     * @throws IOException If file does not exist.
     */
    public void store(ArrayList<Task> listOfTasks) throws IOException {
        // keep adding content of each task to file in specific format
        StringBuilder sb = new StringBuilder();
        for (Task t : listOfTasks) {
            sb.append(t.toFileString()).append("\n");
        }
        FileWriter fw = new FileWriter(filePath);
        fw.write(sb.toString());
        fw.close();
    }

}
