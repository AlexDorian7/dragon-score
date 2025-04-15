package team.logica_populi.dragonscore.ui.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import team.logica_populi.dragonscore.base.Lesson;
import team.logica_populi.dragonscore.base.json.LessonHeader;

import java.util.List;
import java.util.function.Consumer;

/**
 * Controller for the Main Menu.
 */
public class MainMenuController {
    @FXML
    private ComboBox<LessonHeader> lessonsBox;
    @FXML
    private TextFlow lessonArea;
    @FXML
    private Label nameLabel;

    private Consumer<LessonHeader> callback;


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
        Text title = new Text(lessonsBox.getValue().name + "\n");
        title.setStroke(Color.BLACK);
        Text description = new Text(lessonsBox.getValue().description);
        lessonArea.getChildren().addAll(title, description);
    }
}
