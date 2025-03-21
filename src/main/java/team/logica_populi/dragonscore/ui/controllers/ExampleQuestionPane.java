package team.logica_populi.dragonscore.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import team.logica_populi.dragonscore.logic.Question;

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

    private Question question;

    private Runnable buttonCallback;

    public void setQuestion(Question question) {
        // TODO: Make this support question with more or less than 4 answers
        // TODO: Display answers in random order
        this.question = question;
        questionText.setText(question.getQuestion());
        a1Button.setText(question.getAnswers().get(0).toString());
        a2Button.setText(question.getAnswers().get(1).toString());
        a3Button.setText(question.getAnswers().get(2).toString());
        a4Button.setText(question.getAnswers().get(3).toString());
        deselectAll();
    }

    public void setCallback(Runnable callback) {
        this.buttonCallback = callback;
    }

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
        if (buttonCallback != null) buttonCallback.run();
    }

    @FXML
    private void onA2Click() {
//        if (question == null) return;
//        question.setSelected(AnswerOptions.ANSWER2);
        deselectAll();
        a2Button.setSelected(true);
        if (buttonCallback != null) buttonCallback.run();
    }

    @FXML
    private void onA3Click() {
//        if (question == null) return;
//        question.setSelected(AnswerOptions.ANSWER3);
        deselectAll();
        a3Button.setSelected(true);
        if (buttonCallback != null) buttonCallback.run();
    }

    @FXML
    private void onA4Click() {
//        if (question == null) return;
//        question.setSelected(AnswerOptions.ANSWER4);
        deselectAll();
        a4Button.setSelected(true);
        if (buttonCallback != null) buttonCallback.run();
    }
}
