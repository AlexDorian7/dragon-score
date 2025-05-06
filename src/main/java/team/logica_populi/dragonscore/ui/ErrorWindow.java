package team.logica_populi.dragonscore.ui;

import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
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
        Text message = new Text("On no! LogiQuest has crashed! You can attempt to save any progress made during this session or exit without saving.\n\n" + e.getMessage() + "\nSee console for more information.");
        logger.log(Level.SEVERE, "LogiaQuest experienced an uncaught Exception.", e);
        flow.getChildren().addAll(message);

        Region region = new Region();
        region.prefHeight(16);

        TitledPane stacktracePane = new TitledPane();
        stacktracePane.setText("Stacktrace");
        ScrollPane scroll = new ScrollPane();
        scroll.setPrefHeight(400);
        scroll.setMinHeight(100);
        scroll.setMaxHeight(600);
        TextFlow stackFlow = new TextFlow();
        scroll.setContent(stackFlow);
        stacktracePane.setContent(scroll);

        stacktracePane.setExpanded(false);

        stackFlow.getChildren().add(new Text(e.getMessage() + "\n"));
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            stackFlow.getChildren().add(new Text("\t" + stackTraceElement.toString() + "\n"));
        }
        Throwable cause = e.getCause();
        while (cause != null) {
            stackFlow.getChildren().add(new Text("\nCaused by: " + cause.getMessage() + "\n"));
            for (StackTraceElement stackTraceElement : cause.getStackTrace()) {
                stackFlow.getChildren().add(new Text("\t" + stackTraceElement.toString() + "\n"));
            }
            cause = cause.getCause();
        }


        Region region1 = new Region();
        region1.prefHeight(16);

        HBox hBox = new HBox();

        Button saveButton = new Button("Save and quit");
        Button closeButton = new Button("Quit without saving");
        Region region2 = new Region();
        hBox.getChildren().addAll(saveButton, region2, closeButton);
        hBox.spacingProperty().set(8);

        box.getChildren().addAll(flow, region, stacktracePane, region1, hBox);
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
        stage.setHeight(400);
        stage.setWidth(400);
        stage.show();

        return true;
    }
}
