package team.logica_populi.dragonscore.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.*;

public class SubmissionCodeController {
    @FXML
    public Label codeElement;

    @FXML
    private Button backToMenu;

    @FXML
    private Button backToExercise;

    @FXML
    public void initialize(){
    }

    public void setCode(String code){
        codeElement.setText(code);
    }

    private void backToMenu(){

    }

    private void backToExercise(){

    }

}
