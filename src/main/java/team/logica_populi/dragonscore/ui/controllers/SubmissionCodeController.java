package team.logica_populi.dragonscore.ui.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import team.logica_populi.dragonscore.base.registries.DragonHandler;

import java.awt.*;
import java.util.logging.Logger;

/**
 * Controller of Submission Code Popup Page
 */
public class SubmissionCodeController {
    private static Logger logger;

    @FXML
    private Label codeElement;

    @FXML
    private TextArea codeArea;

    @FXML
    private Button backToMenuButton;

    @FXML
    private Button backToExerciseButton;

    /**
     * Called by JavaFX.
     */
    @FXML
    public void initialize(){
        codeArea.setEditable(false);
    }

    /**
     * Sets the submission code to the Text Area
     * @param code Submission Code to be set
     */
    public void setCode(String code){
        codeArea.setText(code);
        // copy new label to clipboard in main menu
        Label msg = new Label("Copied Submission Code to the Clipboard");
        msg.setStyle("-fx-background-color: white; -fx-border: 2; -fx-border-radius: 5; -fx-border-color: black; -fx-font-size: 20;");
        Popup popup = new Popup();
        popup.getContent().add(msg);
        codeArea.setOnMouseClicked(event -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(code);
            clipboard.setContent(content);
            if (!popup.isShowing())
            {
                Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
                popup.show(DragonHandler.getCurrentSession().getStage(),mouseLocation.getX() -100,mouseLocation.getY() + 25);
            }
            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(eventHide -> popup.hide());
            delay.play();
        });
    }

    /**
     * Handler that returns user to Main Menu
     */
    @FXML
    private void backToMenu(){
        if( DragonHandler.getCurrentSession() != null){
            DragonHandler.getCurrentSession().showMainMenu();
        }
        else{
            logger.info("Current Session is null!");
        }
    }

    /**
     * Handle that returns the user to the exercise they previously finished
     */
    @FXML
    private void backToExercise(){
        if( DragonHandler.getCurrentSession() != null){
            DragonHandler.getCurrentSession().loadLesson(DragonHandler.getCurrentSession().getLesson());
        }
        else{
            logger.info("Current Session is null!");
        }
    }

    /** Called when mouse enters either button */
    @FXML
    private void handleHover(MouseEvent event) {
        Button b = (Button) event.getSource();
        b.setStyle(
                "-fx-background-color: #000000;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 20px;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 10;" +
                        "-fx-padding: 8 16;" +
                        "-fx-background-radius: 10;"
        );
    }

    @FXML
    private void handleExit(MouseEvent event) {
        Button b = (Button) event.getSource();
        if ("backToExerciseButton".equals(b.getId())) {
            b.setStyle(
                    "-fx-background-color: #282828;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 20px;" +
                            "-fx-border-width: 2;" +
                            "-fx-border-radius: 10;" +
                            "-fx-padding: 8 16;" +
                            "-fx-background-radius: 10;"
            );
        } else {  // backToMenuButton
            b.setStyle(
                    "-fx-background-color: transparent;" +
                            "-fx-border-color: #282828;" +
                            "-fx-font-size: 20px;" +
                            "-fx-border-width: 2;" +
                            "-fx-border-radius: 10;" +
                            "-fx-background-radius: 10;" +
                            "-fx-padding: 6 14;" +
                            "-fx-text-fill: black;"
            );
        }
    }

}
