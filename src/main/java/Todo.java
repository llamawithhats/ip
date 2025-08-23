public class Todo extends Task {
    TaskType type = TaskType.TODO;
    public Todo(String description) {
        super(description);
    }

    @Override
    public String getMessage() {
        return "[T]" + super.getMessage();
    }

}
