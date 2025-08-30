package meownager.ui;

public class MeownagerException extends Exception {
    public MeownagerException (String message) {
        super(message);
    }

    public static MeownagerException emptyDescription(String taskType) {
        return new MeownagerException("MEOW!!! You didn’t tell me what the " + taskType + " is about!");
    }

    public static MeownagerException unknownCommand() {
        return new MeownagerException("MEOW??? I don’t understand that command. Try again, hooman~");
    }

    public static MeownagerException missingDeadlineInfo() {
        return new MeownagerException("HISSS!! Deadlines need a /by date, nya~");
    }

    public static MeownagerException wrongDateFormat() {
        return new MeownagerException("PURRlease format your deadline in [d/M/yyyy HHmm] format " +
                "or [d/M/yyyy] format if no time!");
    }

    public static MeownagerException missingEventInfo() {
        return new MeownagerException("PURRlease specify /from and /to for events, nya!");
    }

    public static MeownagerException outOfBoundsTaskNumber(int taskNum) {
        return new MeownagerException("Mrowr! meownager.ui.Task number " + taskNum + " doesn’t exist!");
    }

    public static MeownagerException alreadyCompleted() {
        return new MeownagerException("Meow? You've already completed this!");
    }

    public static MeownagerException stillUncompleted() {
        return new MeownagerException("Meow? You've not completed this yet!");
    }

    public static MeownagerException emptyList() {
        return new MeownagerException("Mrowr! You have NO tasks. Add some neow, hooman!");
    }
}
