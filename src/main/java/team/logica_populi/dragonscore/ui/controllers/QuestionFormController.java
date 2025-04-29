package team.logica_populi.dragonscore.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import team.logica_populi.dragonscore.base.logic.Answer;
import team.logica_populi.dragonscore.base.logic.BooleanLogicTreeNode;
import team.logica_populi.dragonscore.base.logic.Question;
import team.logica_populi.dragonscore.base.registries.DragonHandler;
import team.logica_populi.dragonscore.ui.TruthTableView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class QuestionFormController implements IQuestionFormController {

    @FXML
    private Button HomeMenu;
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

    private TruthTableView tableView = null;

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
                btn.setStyle("-fx-background-radius: 100; " +
                        "-fx-background-color: #635325;" +
                        " -fx-text-fill: #ffffff; " +
                        "-fx-pref-width: 50;" +
                        " -fx-pref-height: 50; " +
                        "-fx-font-size: 20px;"); // green
            } else {
                btn.setStyle("-fx-background-radius: 100; " +
                        "-fx-background-color: #e0c97d; " +
                        "-fx-text-fill: #282828; " +
                        "-fx-pref-width: 50; " +
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

    /**
     * Sets the question that this view is to display.
     *
     * @param question The question to display
     */
    public void setQuestion(Question question) {
        this.question = question;
        resultsShown = false;
        questionArea.getEngine().loadContent(
                "<html><head>" +
                        "  <style>" +
                        "    body {" +
                        "      font-family: Helvetica;" +
                        "      font-size: 18px;" +
                        "      line-height: 24px;" +
                        "      background: #fdfdfd;" +
                        "      border: 8px solid #ead8a2;" +
                        "      border-radius: 10px;" +
                        "      margin: 0;" +
                        "      padding: 10px;" +
                        "      color: #000000;" +
                        "    }" +
                        "  </style>" +
                        "</head><body>" +
                        question.getQuestion() +
                        "</body></html>"
        );
        answerButtons.clear();
        answerArea.getChildren().clear();
        FlowPane flow = new FlowPane();
        flow.alignmentProperty().set(Pos.TOP_LEFT);
        flow.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        flow.orientationProperty().set(Orientation.VERTICAL);
        flow.setHgap(10);
        flow.setVgap(10);
        for (Answer answer : question.getAnswers()) {
            RadioButton button = new RadioButton(answer.getText());
            flow.getChildren().add(button);
            answerButtons.add(button);
            button.setOnAction(this::selectAnswer);
        }
        answerArea.getChildren().add(flow);
        submitButton.setText("Submit");
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
     * Sets the question that this view is to display in table mode
     *
     * @param question The question to display
     * @param root The root of the logical expression
     */
    public void setQuestion(Question question, BooleanLogicTreeNode root) {
        this.question = question;
        resultsShown = false;
        questionArea.getEngine().loadContent("<html><head></head><body style='font-size:16px;'>" + question.getQuestion() + "</body></html>");
        answerButtons.clear();
        answerArea.getChildren().clear();
        tableView = new TruthTableView(root);
        answerArea.getChildren().add(tableView);
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
        progressBar.setStyle("-fx-accent: #916e09;");
    }

    /**
     * Shows the correct answer(s) to the user and prevents any additional answers from being recorded.
     */
    public void showCorrect() {
        resultsShown = true;
        if (tableView != null) {
            tableView.applyRowColors(tableView.getExpression().gatherResultsTable(tableView.getTruthMapping(), tableView.getVariables()));
        } else {
            for (int i = 0; i < answerButtons.size(); i++) {
                RadioButton btn = answerButtons.get(i);
                boolean isCorrect = question.getAnswers().get(i).isCorrect();
                if (isCorrect) {
                    // correct answer → green
                    btn.setStyle(
                            "-fx-background-color: lightcoral;" +
                                    "-fx-padding: 1 4 1 4;" +
                                    "-fx-background-radius: 2;"
                    );
                } else if (btn.isSelected()) {
                    // wrong selection → red
                    btn.setStyle(
                            "-fx-background-color: lightgreen;" +
                                    "-fx-padding: 1 4 1 4;" +
                                    "-fx-background-radius: 2;"
                    );
                }
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
            if (tableView != null) {
                if (tableView.getExpression().testTable(tableView.getTruthMapping(), tableView.getVariables())) {
                    answers.add(question.getAnswers().get(1));
                } else {
                    answers.add(question.getAnswers().get(0));
                }
            } else {
                for (int i = 0; i < answerButtons.size(); i++) {
                    if (answerButtons.get(i).isSelected()) {
                        answers.add(question.getAnswers().get(i));
                    }
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
