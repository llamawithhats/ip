package meownager.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private static final String FILE_PATH = System.getProperty("user.home") + "/Meownager/Meownager.txt";
    private Meownager meownager = new Meownager(FILE_PATH);

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("⋆\uD83D\uDC3E° Meownager ≽^•⩊•^≼");
            stage.setScene(scene);
            stage.setResizable(true);
            fxmlLoader.<MainWindow>getController().setMeow(meownager);  // inject the Meownager instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


