public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String getMessage() {
        return "[T]" + super.getMessage();
    }

}
