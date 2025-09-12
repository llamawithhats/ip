package meownager.ui;

public class Tag {
    private String tagMsg;

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
