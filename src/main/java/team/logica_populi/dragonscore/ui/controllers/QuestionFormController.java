package team.logica_populi.dragonscore.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import team.logica_populi.dragonscore.base.logic.Answer;
import team.logica_populi.dragonscore.base.logic.Question;
import team.logica_populi.dragonscore.base.registries.DragonHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class QuestionFormController {
    @FXML
    private Label questionArea;
    @FXML
    private VBox answerArea;
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
    private ArrayList<RadioButton> answerButtons = new ArrayList<>();

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
        answerButtons.clear();
        answerArea.getChildren().clear();
        for (Answer answer : question.getAnswers()) {
            RadioButton button = new RadioButton(answer.getText());
            answerArea.getChildren().add(button);
            answerButtons.add(button);
            button.setOnAction(this::selectAnswer);
        }

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
            ArrayList<Answer> answers = new ArrayList<>();
            for (int i=0; i<answerButtons.size(); i++) {
                if (answerButtons.get(i).isSelected()) {
                    answers.add(question.getAnswers().get(i));
                }
            }

            callback.accept(answers); // TODO: Actually send back the selected answers
        }
    }

    private void deselectAll() {
        for (RadioButton answerButton : answerButtons) {
            answerButton.setSelected(false);
        }

    }

    public void selectAnswer(ActionEvent event) {
        deselectAll();
        RadioButton clickedButton = (RadioButton) event.getSource();
        clickedButton.setSelected(true);
        questionArea.setText("Selected answer: " + clickedButton.getText());
    }
}
