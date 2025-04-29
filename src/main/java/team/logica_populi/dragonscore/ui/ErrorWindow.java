package team.logica_populi.dragonscore.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import team.logica_populi.dragonscore.Main;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ErrorWindow {

    private static final Logger logger = Logger.getLogger(ErrorWindow.class.getName());

    public static boolean makeErrorWindow(Stage stage, Exception e, Main main) {
        VBox box = new VBox();
        TextFlow flow = new TextFlow();
        Text message = new Text("On no! Logiquest has crashed! You can attempt to save any progress made during this session or exit without saving.\n\n" + e.getMessage() + "\nSee console for stacktrace.");
        logger.log(Level.SEVERE, "LogiaQuest experienced an uncaught Exception.", e);
        flow.getChildren().addAll(message);

        Region region = new Region();
        region.prefHeight(16);

        HBox hBox = new HBox();

        Button saveButton = new Button("Save and quit");
        Button closeButton = new Button("Quit without saving");
        Region region1 = new Region();
        hBox.getChildren().addAll(saveButton, region1, closeButton);
        hBox.spacingProperty().set(8);

        box.getChildren().addAll(flow, region, hBox);
        box.spacingProperty().set(16);

        closeButton.setOnAction((event) -> {
            System.exit(1);
        });

        saveButton.setOnAction((event) -> {
            try {
                main.stop();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            System.exit(1);
        });

        Scene errorScene = new Scene(box);
        stage.setScene(errorScene);
        stage.show();

        return true;
    }
}
