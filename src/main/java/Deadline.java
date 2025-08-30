import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Deadline extends Task{
    protected String date;

    public Deadline(String description, String date) {
        super(description);
        //checking if input is in specific date format
        Pattern patternWithTime = Pattern.compile("(\\d{1,2})/(\\d{1,2})/(\\d{4}) (\\d{4})");
        Matcher mTime = patternWithTime.matcher(date);
        Pattern patternNoTime = Pattern.compile("(\\d{1,2})/(\\d{1,2})/(\\d{4})");
        Matcher mNoTime = patternNoTime.matcher(date);
        if (mTime.matches() || mNoTime.matches()) { //if input date is in the specific format
            this.date = parseAndFormatDate(date);
        } else {
            this.date = date; //fallback to raw input (e.g. Monday 4pm)
        }
    }

    public String parseAndFormatDate(String date) {
        //with time
        try {
            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            LocalDateTime dateTime = LocalDateTime.parse(date, inputFormat);
            DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");
            return dateTime.format(outputFormat);
        } catch (DateTimeParseException e) {
            //fall through to next try
        }
        //with no time
        try {
            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate dateOnly = LocalDate.parse(date, inputFormat);
            DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM d yyyy");
            return dateOnly.format(outputFormat);
        } catch (DateTimeParseException e) {

        }
        return null; //won't happen
    }

    @Override
    public String getMessage() {
        return "[D]" + super.getMessage() + " (by: " + date + ")";
    }
}
