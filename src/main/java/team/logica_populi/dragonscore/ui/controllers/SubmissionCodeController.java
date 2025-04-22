package team.logica_populi.dragonscore.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
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

}
