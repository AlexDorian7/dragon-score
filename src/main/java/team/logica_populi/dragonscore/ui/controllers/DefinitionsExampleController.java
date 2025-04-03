package team.logica_populi.dragonscore.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import team.logica_populi.dragonscore.base.logic.Answer;
import team.logica_populi.dragonscore.base.logic.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DefinitionsExampleController {
    @FXML
    public Label questionArea;
    @FXML
    private HBox answer1;
    @FXML
    private HBox answer2;
    @FXML
    private HBox answer3;
    @FXML
    private HBox answer4;
    @FXML
    private HBox answer5;
    @FXML
    private HBox answer6;
    @FXML
    private HBox answer7;
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

    // Initialize the controller
    @FXML
    public void initialize() {
        // Set default text
        questionArea.setText("Click Submit to see an example response.");
    }

    @FXML
    private void onSubmit(MouseEvent event) {
        // Example: Change the text of the question area when clicked
        questionArea.setText("You clicked Submit!");
    }

    @FXML
    private void onDifficultySelect(MouseEvent event) {
        ToggleButton clickedButton = (ToggleButton) event.getSource();
        String difficulty = clickedButton.getText();
        questionArea.setText("Difficulty set to: " + difficulty);
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
}
