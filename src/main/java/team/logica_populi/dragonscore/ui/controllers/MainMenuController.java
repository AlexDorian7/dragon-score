package team.logica_populi.dragonscore.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.util.Duration;
import team.logica_populi.dragonscore.base.json.LessonHeader;
import javafx.scene.text.FontWeight;
import javafx.animation.PauseTransition;

import team.logica_populi.dragonscore.base.registries.DragonHandler;
import team.logica_populi.dragonscore.base.registries.JsonRegistry;

import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;
import javafx.scene.input.MouseEvent;
import java.util.function.BiConsumer;


/**
 * Controller for the Main Menu.
 */
public class MainMenuController {
    private static Logger logger;

    @FXML
    private ComboBox<LessonHeader> lessonsBox;
    @FXML
    private TextFlow lessonArea;
    @FXML
    private Label nameLabel;
    @FXML
    private ListView<String> codes;

    private Consumer<LessonHeader> callback;

    /**
     * Shows all the submission codes from each lesson in a ListView.
     * Each row on click copies the code to the clipboard as well as
     * shows message for it.
     */
    public void setCodes(){
        // Retrieves submission codes
        ObservableList<String> items =FXCollections.observableArrayList();
        JsonRegistry.getInstance().getSubmissionSystem().getSubmissions().forEach(((s, map) -> {
            map.forEach((String key, String val) ->{
                //logger.info(val);
                items.add(key + " : " + val);
            });
        }));
        codes.setItems(items);

        Label msg = new Label("Copied Submission Code to the Clipboard");
        msg.setStyle("-fx-background-color: white; -fx-border: 2; -fx-border-radius: 5; -fx-border-color: black; -fx-font-size: 20;");
        Popup popup = new Popup();
        popup.getContent().add(msg);


        for(int i = 0; i < items.size(); i++){
            int finalI = i;
            codes.setOnMouseClicked(event ->{
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                String val = codes.getItems().get(finalI);
                String trimmedVal = val.substring(val.indexOf(':')+1).trim();
                content.putString(trimmedVal);
                clipboard.setContent(content);
                if (!popup.isShowing())
                    popup.show(DragonHandler.getCurrentSession().getStage(),
                            DragonHandler.getCurrentSession().getStage().getX() + DragonHandler.getCurrentSession().getStage().getWidth() / 2-230,
                            DragonHandler.getCurrentSession().getStage().getY() + DragonHandler.getCurrentSession().getStage().getHeight() / 2-200);

                PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
                delay.setOnFinished(eventHide -> popup.hide());
                delay.play();
            });
        }
    }

    /**
     * Sets the lessons to display in the lessons box.
     * @param lessons The lessons to display
     */
    public void setLessons(List<LessonHeader> lessons) {
        lessonsBox.setItems(FXCollections.observableList(lessons));
    }

    /**
     * Sets the callback to be executed when the user wants to start a lesson.
     * @param callback The callback to fire with the selected lesson
     */
    public void setStartCallback(Consumer<LessonHeader> callback) {
        this.callback = callback;
    }

    /**
     * Sets the name displayed on the main menu.
     * @param name The name to display
     */
    public void setName(String name) {
        nameLabel.setText("Welcome " + name + "!");
    }

    /**
     * Handler for the start lesson button.
     * @param actionEvent The event from JavaFX
     */
    @FXML
    private void startLesson(ActionEvent actionEvent) {
        if (callback != null) {
            callback.accept(lessonsBox.getValue());
        }
    }

    /**
     * Combo Box Handler.
     * @param actionEvent The event from JavaFX
     */
    @FXML
    private void updateSelected(ActionEvent actionEvent) {
        lessonArea.getChildren().clear();
        lessonArea.setLineSpacing(8);

        Text title = new Text(lessonsBox.getValue().name() + "\n");
        title.setFill(Color.web("#282828"));
        title.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));

        Text description = new Text(lessonsBox.getValue().description());
        description.setFill(Color.web("#282828"));
        description.setFont(Font.font("Helvetica", 14));

        lessonArea.getChildren().addAll(title, description);
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

}
