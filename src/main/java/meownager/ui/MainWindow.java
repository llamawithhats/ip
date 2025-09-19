package meownager.ui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private HBox inputArea;

    private Meownager meownager;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image meowImage = new Image(this.getClass().getResourceAsStream("/images/meownager.jpg"));

    private Ui ui = new Ui();

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        assert userImage != null : "User image should exist";
        assert meowImage != null : "Meow image should exist";
        inputArea.widthProperty().addListener((obs, oldVal, newVal) -> {
            double totalWidth = newVal.doubleValue();
            userInput.setPrefWidth(totalWidth * 0.8);
            sendButton.setPrefWidth(totalWidth * 0.2);
        });
    }

    /**
     * Injects the Meow instance
     *
     * @param m Meownager instance
     */
    public void setMeow(Meownager m) {
        meownager = m;
        String greetings = ui.showGreetings();
        dialogContainer.getChildren().add(DialogBox.getMeowDialog(greetings, meowImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = meownager.run(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMeowDialog(response, meowImage)
        );
        userInput.clear();

        // close the popup window after showing bye response
        if (input.trim().equals("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> {
                Stage stage = (Stage) userInput.getScene().getWindow();
                stage.close();
            });
            delay.play();
        }
    }
}

