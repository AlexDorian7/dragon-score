package team.logica_populi.dragonscore.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import team.logica_populi.dragonscore.base.logic.Answer;
import team.logica_populi.dragonscore.base.logic.Question;
import team.logica_populi.dragonscore.base.registries.DragonHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class ParagraphQuestionFormController implements IQuestionFormController {

    private static final Logger logger = Logger.getLogger(ParagraphQuestionFormController.class.getName());
    private static final Font DEFAULT_FONT = new Font("System Default", 16);
    private static final Font BOLD_DEFAULT_FONT = new Font("System Bold", 16);
    private static final Font QUESTION_FONT = new Font("System Default", 20);

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
            HBox.setHgrow(btn, Priority.SOMETIMES);
            if (btn.isSelected()) {
                btn.setStyle("-fx-background-radius: 100; " +
                        "-fx-background-color: #635325;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-pref-width: 50;"+
                        "-fx-min-width: 50;" +
                        "-fx-max-width: 100;" +
                        "-fx-pref-height: 50;" +
                        "-fx-font-size: 20px;"); // green
            } else {
                btn.setStyle("-fx-background-radius: 100; " +
                        "-fx-background-color: #e0c97d; " +
                        "-fx-text-fill: #282828; " +
                        "-fx-pref-width: 50; " +
                        "-fx-min-width: 50;" +
                        "-fx-max-width: 100;" +
                        "-fx-pref-height: 50; " +
                        "-fx-font-size: 20px;"); // green
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

    @FXML
    private void handleHover(MouseEvent ev) {
        Button b = (Button)ev.getSource();
        b.setStyle(
                "-fx-background-color: black;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-font-size: 20px;" +
                        "-fx-padding: 8 28;"
        );
    }

    @FXML
    private void handleExit(MouseEvent ev) {
        Button b = (Button)ev.getSource();
        b.setStyle(
                "-fx-background-color: #282828;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-font-size: 20px;" +
                        "-fx-padding: 8 28;"
        );
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
        Text questionText = new Text(question.getQuestion() + "\n\n");
        questionText.setFont(QUESTION_FONT);
        paragraphArea.getChildren().add(questionText);
        for (Answer answer : question.getAnswers()) {
            String[] split = answer.getText().split("\\|");
            Text text;
            Text addB = null;
            Text addA = null;
            if (split.length == 1) {
                text = new Text(answer.getText());
            } else if (split.length == 2) {
                text = new Text(split[0]);
                addA = new Text(split[1]);
                addA.setFont(DEFAULT_FONT);
            } else {
                addB = new Text(split[0]);
                text = new Text(split[1]);
                addA = new Text(split[2]);
                addA.setFont(DEFAULT_FONT);
                addB.setFont(DEFAULT_FONT);
            }
            Text space = new Text(" ");
            space.setFont(DEFAULT_FONT);
            if (addB != null) paragraphArea.getChildren().add(addB);
            paragraphArea.getChildren().add(text);
            if (addA != null) paragraphArea.getChildren().add(addA);
            paragraphArea.getChildren().add(space);
            answerButtons.add(text);
            text.setFont(DEFAULT_FONT);
            text.setOnMouseClicked((event) -> {
                deselectAll();
                selectedAnswer = answer;
                text.underlineProperty().set(true);
            });
            text.setOnMouseEntered((event) -> {
                text.setFont(BOLD_DEFAULT_FONT);

            });
            text.setOnMouseExited((event) -> {
                text.setFont(DEFAULT_FONT);
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
        progressBar.setStyle("-fx-accent: #dcbe64;");
    }

    /**
     * Shows the correct answer(s) to the user and prevents any additional answers from being recorded.
     */
    public void showCorrect() {
        resultsShown = true;
        for (int i = 0; i < answerButtons.size(); i++) {
            if (answerButtons.get(i).underlineProperty().get()) {
                answerButtons.get(i).fillProperty().set(Color.LIGHTCORAL);
            }
            if (question.getAnswers().get(i).isCorrect()) {
                answerButtons.get(i).fillProperty().set(Color.LIGHTGREEN);
            }
        }
        submitButton.setText("Next Question");
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
