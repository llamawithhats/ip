import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//NOTE: IdeaProjects -> ip -> src -> main -> java
//sout: System.out.println

public class Meownager {

    public static void main(String[] args) {
        greetings();
        Scanner sc = new Scanner(System.in); //get inputs from user
        ArrayList<Task> listOfTasks = new ArrayList<>(); //to track list of tasks

        String filePath = "./data/Meownager.txt";
        File f = new File(filePath);
        try {
            if (!f.exists()) {
                //Create directory if no exist
                File parent = f.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdir();
                }
                //create file if no exist
                f.createNewFile();
                System.out.println("📁 New file created at: " + filePath);
            }
            loadFile(listOfTasks);
        } catch (IOException e) {
            System.out.println("MEOW!! Couldn't create or read the file: " + e.getMessage());
        }

        addList(sc, listOfTasks);
        sc.close();
    }

    public static void greetings() {
        String logo = "  ( =＾･ω･＾=)ノ <3\n";

        System.out.println("Hello! I'm your resident MEOWnager!\n" + logo
                + "Anything I can do for you neow?\n");
    }

    //detects the enum field
    private static TaskType detectType(String input) {
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

    public static void writeToFile(String filePath, String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        fw.write(textToAdd);
        fw.close();
    }

    // store list
    public static String storeFile(ArrayList<Task> listOfTasks,
                                   String fileContent) {
        //keep adding content of each task to file in specific format
        for (Task t : listOfTasks) {
            String taskMsg = t.getMessage();
            //regex pattern
            //todo
            if (taskMsg.startsWith("[T]")) {
                Pattern pattern = Pattern.compile("\\[(.)]\\[(.)] (.+)");
                Matcher m = pattern.matcher(taskMsg);
                if (m.matches()) {
                    String type = m.group(1);
                    String status = m.group(2).equals("X") ? "1" : "0"; //1 if X else 0
                    String desc = m.group(3);
                    fileContent += type + " | " + status + " | " + desc + "\n";
                }
            //deadline
            } else if (taskMsg.startsWith("[D]")) {
                Pattern pattern = Pattern.compile("\\[(.)]\\[(.)] (.+) \\(by: (.+)\\)");
                Matcher m = pattern.matcher(taskMsg);
                if (m.matches()) {
                    String type = m.group(1);
                    String status = m.group(2).equals("X") ? "1" : "0";
                    String desc = m.group(3);
                    String date = m.group(4);
                    fileContent += type + " | " + status + " | " + desc + " | " + date + "\n";
                }
            //event
            } else {
                Pattern pattern = Pattern.compile("\\[(.)]\\[(.)] (.+) \\(from: (.+) to: (.+)\\)");
                Matcher m = pattern.matcher(taskMsg);
                if (m.matches()) {
                    String type = m.group(1);
                    String status = m.group(2).equals("X") ? "1" : "0";
                    String desc = m.group(3);
                    String from = m.group(4);
                    String to = m.group(5);
                    fileContent += type + " | " + status + " | " + desc + " | "
                            + from + " | " + to + "\n";
                }
            }
        }
        return fileContent;
    }

    public static void loadFile(ArrayList<Task> listOfTasks) throws IOException {
        File f = new File("./data/Meownager.txt");
        Scanner s = new Scanner(f);
        //add previous tasks into new arraylist
        while (s.hasNext()) {
            String taskMsg = s.nextLine();
            String[] parts = taskMsg.split(" \\| ");
            if (taskMsg.startsWith("T")) {
                String desc = parts[2];
                Task t = new Todo(desc);
                if (parts[1].equals("1")) {
                    t.mark();
                } //mark task as done if it was (default undone)
                listOfTasks.add(t);
            } else if (taskMsg.startsWith("D")) {
                String desc = parts[2];
                String date = parts[3];
                Task t = new Deadline(desc, date);
                if (parts[1].equals("1")) {
                    t.mark();
                } //mark task as done if it was (default undone)
                listOfTasks.add(t);
            } else {
                String desc = parts[2];
                String from = parts[3];
                String to = parts[4];
                Task t = new Event(desc, from, to);
                if (parts[1].equals("1")) {
                    t.mark();
                } //mark task as done if it was (default undone)
                listOfTasks.add(t);
            }
        }
    }

    public static void addList(Scanner sc, ArrayList<Task> listOfTasks) {
        String input = sc.nextLine();

        if (input.equals("bye")) {
            String filePath = "./data/Meownager.txt";
            String fileText = storeFile(listOfTasks, "");
            try {
                writeToFile(filePath, fileText);
            } catch (IOException e) { // file doesnt exist (wont happen)
                System.out.println("Something went wrong: " + e.getMessage());
            }
            System.out.println("\n\tMeow you next time!");
            return;
        }

        try {
            if (input.equals("list")) {
                if (listOfTasks.isEmpty()) { //no tasks
                    throw MeownagerException.emptyList();
                } else {
                    System.out.println("\n\t\uD83D\uDE3A Here are your tasks, hooman:");
                    for (int i = 0; i < listOfTasks.size(); i++) {
                        System.out.println("\n\t" + (i + 1) + "." + listOfTasks.get(i).getMessage());
                    }
                }
                addList(sc, listOfTasks); //repeat inputs
            } else if (input.startsWith("mark ") || input.startsWith("unmark ") || input.startsWith("delete")) {
                // marking, unmarking, deleting
                // finding task no.
                int num = Integer.parseInt(input.split(" ")[1]); //split makes it a string[],
                // Integer.parseInt converts to int
                //NOTE task 1 is index 0 in list (num-1)

                if (num <= 0 || num > listOfTasks.size()) { //not a task number
                    throw MeownagerException.outOfBoundsTaskNumber(num);
                }
                Task t = listOfTasks.get(num - 1);
                if (input.startsWith("delete")) { //delete
                    listOfTasks.remove(t);
                    t.deleteMessage(t, listOfTasks.size());
                } else { //mark, unmark
                    t.markMessage(t, input);
                }
                addList(sc, listOfTasks);
            } else {
                Task t = null;
                TaskType type = detectType(input);
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
                listOfTasks.add(t); //add task to list
                System.out.println("\n\tMeow-K! I've added this task:");
                System.out.println("\n\t\t" + t.getMessage());
                int total = listOfTasks.size();
                System.out.println("\n\tYou neow have " + total + " tasks in your list.");
                addList(sc, listOfTasks);
            }
        } catch (MeownagerException e) {
                System.out.println("\n\t" + e.getMessage());
                addList(sc, listOfTasks);
        }
    }


}

