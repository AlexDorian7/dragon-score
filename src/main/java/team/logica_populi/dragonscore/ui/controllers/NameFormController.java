package team.logica_populi.dragonscore.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.function.BiConsumer;

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
}
