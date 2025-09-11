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
            // create directory if no exist
            File parent = f.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdir();
            }
            // create file if no exist
            f.createNewFile();
            System.out.println("New file created at: " + filePath);
        }
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
            Task t;
            if (parts[0].equals("T")) {
                String desc = parts[2];
                t = new Todo(desc);
            } else if (parts[0].equals("D")) {
                String desc = parts[2];
                String date = parts[3];
                t = new Deadline(desc, date);
            } else {
                assert parts[0].equals("E"); // assertions
                String desc = parts[2];
                String from = parts[3];
                String to = parts[4];
                t = new Event(desc, from, to);
            }
            assert (parts[1].equals("1")||parts[1].equals("0")); // assertions
            if (parts[1].equals("1")) {
                t.mark();
            } // mark task as done if it was (default undone)
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
