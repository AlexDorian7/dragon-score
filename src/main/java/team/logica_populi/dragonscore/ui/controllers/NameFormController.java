package team.logica_populi.dragonscore.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;
import java.util.function.BiConsumer;

/**
 * Controller for the name form.
 */
public class NameFormController {

    @FXML
    public void initialize() {
        // Assuming 'fName' is your container, or replace with your actual root container node:
    }

    @FXML
    private TextField fName;
    @FXML
    private TextField lName;

    private BiConsumer<String, String> submitCallback;

    /**
     * Submit button handler.
     * @param actionEvent The action from JavaFX
     */
    @FXML
    private void submit(ActionEvent actionEvent) {
        if (submitCallback != null) {
            submitCallback.accept(fName.getText(), lName.getText());
        }
    }

    /**
     * Sets the callback for when the name form is submitted.
     * @param submitCallback The Callback receiving the first and last name
     */
    public void setSubmitCallback(BiConsumer<String, String> submitCallback) {
        this.submitCallback = submitCallback;
    }

    @FXML
    private void handleHover(MouseEvent ev) {
        Button b = (Button)ev.getSource();
        b.setStyle(
                "-fx-background-color: black;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-font-size: 22px;" +
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
                        "-fx-font-size: 22px;" +
                        "-fx-padding: 8 28;"
        );
    }
}
