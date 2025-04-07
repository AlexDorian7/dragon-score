package team.logica_populi.dragonscore.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import team.logica_populi.dragonscore.base.logic.Answer;
import team.logica_populi.dragonscore.base.logic.Question;
import team.logica_populi.dragonscore.base.registries.DragonHandler;

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
    @FXML
    private ProgressBar progressBar;

    private Consumer<List<Answer>> callback;
    private Question question;

    // Initialize the controller
    @FXML
    public void initialize() {
        // Set default text
        questionArea.setText("Click Submit to see an example response.");
    }

    @FXML
    private void onDifficultySelect(ActionEvent event) {
        ToggleButton clickedButton = (ToggleButton) event.getSource();
        int difficulty = Integer.parseInt(clickedButton.getText());
        if (DragonHandler.getCurrentSession() != null) {
            DragonHandler.getCurrentSession().setPointsToGive(difficulty);
        }
    }

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

    private void deselectAll() {
        answer1.setSelected(false);
        answer2.setSelected(false);
        answer3.setSelected(false);
        answer4.setSelected(false);
        answer5.setSelected(false);
        answer6.setSelected(false);
        answer7.setSelected(false);
    }

    public void selectAnswer(ActionEvent event) {
        deselectAll();
        ToggleButton clickedButton = (ToggleButton) event.getSource();
        clickedButton.setSelected(true);
        questionArea.setText("Selected answer: " + clickedButton.getText());
    }
}
