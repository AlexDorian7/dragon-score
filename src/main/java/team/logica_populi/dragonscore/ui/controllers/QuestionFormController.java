package team.logica_populi.dragonscore.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import team.logica_populi.dragonscore.base.logic.Answer;
import team.logica_populi.dragonscore.base.logic.Question;
import team.logica_populi.dragonscore.base.registries.DragonHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class QuestionFormController {
    @FXML
    private WebView questionArea;
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
    private Runnable nextQuestionCallback;
    private Question question;
    private ArrayList<RadioButton> answerButtons = new ArrayList<>();

    private boolean resultsShown = false;

    // Initialize the controller
    @FXML
    public void initialize() {
        // Set default text
        questionArea.getEngine().loadContent("<b>Click Submit to see an example response.</b>");
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
        resultsShown = false;
        questionArea.getEngine().loadContent(question.getQuestion());
        answerButtons.clear();
        answerArea.getChildren().clear();
        for (Answer answer : question.getAnswers()) {
            RadioButton button = new RadioButton(answer.getText());
            answerArea.getChildren().add(button);
            answerButtons.add(button);
            button.setOnAction(this::selectAnswer);
        }
        submitButton.setText("Submit");
    }

    /**
     * Sets the progress bar value.
     * @param progress The value to display in the progress bar
     */
    public void setProgress(double progress) {
        if (progress > 1) progress = 1;
        if (progress < 0) progress = 0;
        progressBar.setProgress(progress);
    }

    /**
     * Shows the correct answer(s) to the user and prevents any additional answers from being recorded.
     */
    public void showCorrect() {
        resultsShown = true;
        for (int i=0; i<answerButtons.size(); i++) {
            if (answerButtons.get(i).isSelected()) {
                answerButtons.get(i).setStyle("-fx-background-color: #FF7F7F");
            }
            if (question.getAnswers().get(i).isCorrect()) {
                answerButtons.get(i).setStyle("-fx-background-color: #7FFF7F");
            }
        }
        submitButton.setText("Next Question");
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
        if (resultsShown) {
            if (nextQuestionCallback != null)
                nextQuestionCallback.run();
            return;
        }
        if (callback != null) {
            ArrayList<Answer> answers = new ArrayList<>();
            for (int i=0; i<answerButtons.size(); i++) {
                if (answerButtons.get(i).isSelected()) {
                    answers.add(question.getAnswers().get(i));
                }
            }
            if (answers.isEmpty()) return; // The user did not select an answer. Do nothing.
            callback.accept(answers);
        }
    }

    private void deselectAll() {
        for (RadioButton answerButton : answerButtons) {
            answerButton.setSelected(false);
        }

    }

    private void selectAnswer(ActionEvent event) {
        deselectAll();
        RadioButton clickedButton = (RadioButton) event.getSource();
        clickedButton.setSelected(true);
    }

    /**
     * Sets the callback that will be called when the user wants to go to the next question.
     * @param nextQuestionCallback The callback to be executed
     */
    public void setNextQuestionCallback(Runnable nextQuestionCallback) {
        this.nextQuestionCallback = nextQuestionCallback;
    }
}
