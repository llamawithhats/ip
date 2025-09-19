package meownager.ui;

/**
 * Represents a tag assigned to a task.
 *
 * @author Yu Tingan
 */
public class Tag {
    private String tagMsg;

    /**
     * Constructs a Tag object with the given tag message.
     *
     * @param tagMsg Tag message.
     */
    public Tag(String tagMsg) {
        this.tagMsg = tagMsg;
    }

    public String showTagMsg() {
        return this.tagMsg;
    }

    public void editTag(String newTagMsg) {
        this.tagMsg = newTagMsg;
    }

}
