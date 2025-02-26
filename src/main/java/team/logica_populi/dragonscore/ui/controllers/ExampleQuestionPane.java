package team.logica_populi.dragonscore.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller for /assets/views/ExampleQuestionPane.fxml
 */
public class ExampleQuestionPane {
    @FXML
    private TextFlow questionArea;

    @FXML
    private Text questionText;

    @FXML
    private ToggleButton a1Button;

    @FXML
    private ToggleButton a2Button;

    @FXML
    private ToggleButton a3Button;

    @FXML
    private ToggleButton a4Button;

//    public void setQuestion(Question question) {
//        this.question = question;
//        questionText.setText(question.question.getValue());
//        a1Button.setText(question.answer1.getValue());
//        a2Button.setText(question.answer2.getValue());
//        a3Button.setText(question.answer3.getValue());
//        a4Button.setText(question.answer4.getValue());
//    }

    private void deselectAll() {
        a1Button.setSelected(false);
        a2Button.setSelected(false);
        a3Button.setSelected(false);
        a4Button.setSelected(false);
    }

    @FXML
    private void onA1Click() {
//        if (question == null) return;
//        question.setSelected(AnswerOptions.ANSWER1);
        deselectAll();
        a1Button.setSelected(true);
    }

    @FXML
    private void onA2Click() {
//        if (question == null) return;
//        question.setSelected(AnswerOptions.ANSWER2);
        deselectAll();
        a2Button.setSelected(true);
    }

    @FXML
    private void onA3Click() {
//        if (question == null) return;
//        question.setSelected(AnswerOptions.ANSWER3);
        deselectAll();
        a3Button.setSelected(true);
    }

    @FXML
    private void onA4Click() {
//        if (question == null) return;
//        question.setSelected(AnswerOptions.ANSWER4);
        deselectAll();
        a4Button.setSelected(true);
    }
}
