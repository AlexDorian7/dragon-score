package team.logica_populi.dragonscore.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import team.logica_populi.dragonscore.base.logic.Answer;
import team.logica_populi.dragonscore.base.logic.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DefinitionsExampleController {
    @FXML
    public Label questionArea;
    @FXML
    private ToggleButton answer1;
    @FXML
    private ToggleButton answer2;
    @FXML
    private ToggleButton answer3;
    @FXML
    private ToggleButton answer4;
    @FXML
    private ToggleButton answer5;
    @FXML
    private ToggleButton answer6;
    @FXML
    private ToggleButton answer7;
    @FXML
    private Button submitButton;
    @FXML
    private ToggleButton easyDif;
    @FXML
    private ToggleButton normDif;
    @FXML
    private ToggleButton hardDif;

    private Consumer<List<Answer>> callback;
    private Question question;

    /**
     * Sets the question that this view is to display.
     * @param question The question to display
     */
    public void setQuestion(Question question) {
        this.question = question;
        questionArea.setText(question.getQuestion());
    }

    /**
     * Sets the on submitted callback consumer.
     * @param callback The consumer to call with the selected answers
     */
    public void setSubmitCallback(Consumer<List<Answer>> callback) {
        this.callback = callback;
    }

    /**
     * Called when the submit button is pressed.
     * @param actionEvent Event details provided by JavaFX
     */
    @FXML
    public void onSubmit(ActionEvent actionEvent) {
        if (callback != null) {
            callback.accept(new ArrayList<>()); // TODO: Actually send back the selected answers
        }
    }
}
