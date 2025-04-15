package team.logica_populi.dragonscore.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import team.logica_populi.dragonscore.base.points.SubmissionCode;

public class SubmissionCodeController {
    @FXML
    public Text codeElement;

    @FXML
    private Button submitButton;

    @FXML
    public void initialize(){
    }

    public void setCode(String code){
        codeElement.setText(code);
    }

    private void onSubmit(){

    }

}
