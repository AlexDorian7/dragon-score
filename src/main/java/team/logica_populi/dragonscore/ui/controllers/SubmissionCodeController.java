package team.logica_populi.dragonscore.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import team.logica_populi.dragonscore.base.registries.DragonHandler;

import java.util.logging.Logger;

/**
 * PLEASE COMMENT ME
 */
public class SubmissionCodeController {
    private static Logger logger;

    @FXML
    public Label codeElement;

    @FXML
    public TextArea codeArea;

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
    }

    /**
     * OnClick return to the Main Menu
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
     * OnClick return to the exercise the user finished
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
