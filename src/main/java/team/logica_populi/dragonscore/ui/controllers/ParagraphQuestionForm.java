package team.logica_populi.dragonscore.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.w3c.dom.events.MouseEvent;
import team.logica_populi.dragonscore.base.logic.Answer;
import team.logica_populi.dragonscore.base.logic.Question;
import team.logica_populi.dragonscore.base.registries.DragonHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class ParagraphQuestionForm {

    private static final Logger logger = Logger.getLogger(ParagraphQuestionForm.class.getName());
    private static final Font DEFAULT = new Font("System Default", 14);
    private static final Font BOLD_DEFAULT = new Font("System Bold", 14);

    @FXML
    private Button HomeMenu;
    @FXML
    private TextFlow paragraphArea;
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
    private ArrayList<Text> answerButtons = new ArrayList<>();
    private Answer selectedAnswer = null;

    private boolean resultsShown = false;

    // Initialize the controller
    @FXML
    public void initialize() {
        ToggleGroup difficultyGroup = new ToggleGroup();
        easyDif.setToggleGroup(difficultyGroup);
        normDif.setToggleGroup(difficultyGroup);
        hardDif.setToggleGroup(difficultyGroup);

        setDifficultlyHandler(easyDif);
        setDifficultlyHandler(normDif);
        setDifficultlyHandler(hardDif);
        updateDifficultyStyles();
    }
    private void setDifficultlyHandler(ToggleButton button) {
        button.setOnAction(event -> {

            ToggleButton clickedButton = (ToggleButton) event.getSource();
            int difficulty = Integer.parseInt(clickedButton.getText());

            updateDifficultyStyles();

            if (DragonHandler.getCurrentSession() != null) {
                DragonHandler.getCurrentSession().setPointsToGive(difficulty);
            }
        });
    }
    private void updateDifficultyStyles() {
        ToggleButton[] buttons = { easyDif, normDif, hardDif };

        for (ToggleButton btn : buttons) {
            if (btn.isSelected()) {
                btn.setStyle("-fx-background-radius: 100; -fx-background-color: #645c41; -fx-text-fill: black; -fx-pref-width: 50; -fx-pref-height: 50; -fx-font-size: 1.1em;"); // green
            } else {
                btn.setStyle("-fx-background-radius: 100; -fx-background-color: #fce6a4; -fx-text-fill: black; -fx-pref-width: 50; -fx-pref-height: 50; -fx-font-size: 1.1em;"); // green
            }
        }
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
     *
     * @param question The question to display
     */
    public void setQuestion(Question question) {
        this.question = question;
        resultsShown = false;
        answerButtons.clear();
        selectedAnswer = null;
        paragraphArea.getChildren().clear();
        int charPos = 0;
        for (Answer answer : question.getAnswers()) {
            int startPos = charPos;
            logger.info(String.valueOf(startPos));
            charPos += answer.getText().length();
            Text text = new Text(answer.getText());
            Text space = new Text(" ");
            space.setFont(DEFAULT);
            paragraphArea.getChildren().addAll(text, space);
            answerButtons.add(text);
            text.setFont(DEFAULT);
            text.setOnMouseClicked((event) -> {
                deselectAll();
                selectedAnswer = answer;
                text.underlineProperty().set(true);
            });
            text.setOnMouseEntered((event) -> {
                text.setFont(BOLD_DEFAULT);

            });
            text.setOnMouseExited((event) -> {
                text.setFont(DEFAULT);
            });
        }
        submitButton.setText("Submit");
    }

    /**
     * Sets the progress bar value.
     *
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
//        resultsShown = true;
//        for (int i = 0; i < answerButtons.size(); i++) {
//            if (answerButtons.get(i).isSelected()) {
//                answerButtons.get(i).setStyle("-fx-background-color: #FF7770");
//            }
//            if (question.getAnswers().get(i).isCorrect()) {
//                answerButtons.get(i).setStyle("-fx-background-color: #9FD4A3");
//            }
//        }
//        submitButton.setText("Next Question");
    }

    /**
     * Sets the on submitted callback consumer.
     *
     * @param callback The consumer to call with the selected answers
     */
    public void setSubmitCallback(Consumer<List<Answer>> callback) {
        this.callback = callback;
    }

    /**
     * Called when the submit button is pressed.
     *
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
//            for (int i = 0; i < answerButtons.size(); i++) {
//                if (answerButtons.get(i).isSelected()) {
//                    answers.add(question.getAnswers().get(i));
//                }
//            }
            answers.add(selectedAnswer);
            if (answers.isEmpty()) return; // The user did not select an answer. Do nothing.
            callback.accept(answers);
        }
    }

    private void deselectAll() {
        for (Text text : answerButtons) {
            text.underlineProperty().set(false

            );
        }

    }

    /**
     * Sets the callback that will be called when the user wants to go to the next question.
     *
     * @param nextQuestionCallback The callback to be executed
     */
    public void setNextQuestionCallback(Runnable nextQuestionCallback) {
        this.nextQuestionCallback = nextQuestionCallback;
    }

    @FXML
    private void goHome(ActionEvent actionEvent) {
        DragonHandler currentSession = DragonHandler.getCurrentSession();
        if (currentSession == null) return; //This should never be possible, but do nothing id it is
        currentSession.showMainMenu(); // return to main menu. Any unsaved progress will be lost, but program saves after each submit
    }
}
