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

import java.util.List;
import java.util.function.Consumer;

public class MainMenuController {
    @FXML
    private ComboBox<Lesson> lessonsBox;
    @FXML
    private TextFlow lessonArea;
    @FXML
    private Label nameLabel;

    private Consumer<Lesson> callback;


    /**
     * Sets the lessons to display in the lessons box.
     * @param lessons The lessons to display
     */
    public void setLessons(List<Lesson> lessons) {
        lessonsBox.setItems(FXCollections.observableList(lessons));
    }

    /**
     * Sets the callback to be executed when the user wants to start a lesson.
     * @param callback The callback to fire with the selected lesson
     */
    public void setStartCallback(Consumer<Lesson> callback) {
        this.callback = callback;
    }

    /**
     * Sets the name displayed on the main menu.
     * @param name The name to display
     */
    public void setName(String name) {
        nameLabel.setText("Welcome " + name + "!");
    }

    @FXML
    private void startLesson(ActionEvent actionEvent) {
        if (callback != null) {
            callback.accept(lessonsBox.getValue());
        }
    }

    @FXML
    private void updateSelected(ActionEvent actionEvent) {
        lessonArea.getChildren().clear();
        Text title = new Text(lessonsBox.getValue().getName() + "\n");
        title.setStroke(Color.BLACK);
        Text description = new Text(lessonsBox.getValue().getDescription());
        lessonArea.getChildren().addAll(title, description);
    }
}
