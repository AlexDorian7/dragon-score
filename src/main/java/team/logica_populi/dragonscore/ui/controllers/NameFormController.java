package team.logica_populi.dragonscore.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;
import team.logica_populi.dragonscore.base.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.logging.Logger;

/**
 * Controller for the name form.
 */
public class NameFormController {

    @FXML
    private TextField fName;
    @FXML
    private TextField lName;
    @FXML
    private Label nameError;

    private BiConsumer<String, String> submitCallback;

    // Name logger that gets the store name
    private static final Logger logger = Logger.getLogger(NameFormController.class.getName());

    /**
     * Stores the name of the current user.
     * If there is a name, fills the TextField.
     */
    public void fillInName(){
        ResourceLocation location = new ResourceLocation("dynamic:user.dat");
        if(location.exists()){
            String name = location.tryGetResource();
            logger.info(name);
            String firstName = name.substring(0, name.indexOf(" "));
            String lastName = name.substring(name.indexOf(" ")+1);

            fName.setText(firstName);
            lName.setText(lastName);
        }
    }

    // initialize action event for enter button for submission
    @FXML
    public void initialize() {
        // Assuming 'fName' is your container, or replace with your actual root container node:
        fName.setOnAction(e -> submit(new ActionEvent()));
        lName.setOnAction(e -> submit(new ActionEvent()));

        //stores user data into dat file
        ResourceLocation location = new ResourceLocation("dynamic:user.dat");
        location.createIfNotExists();

        //if the location is empty then fill the name
        if(!location.tryGetResource().isEmpty()){
            fillInName();
        }

    }

    /**
     * Submit button handler.
     * @param actionEvent The action from JavaFX
     */
    @FXML
    private void submit(ActionEvent actionEvent) {
        if (submitCallback != null) {
            String first = fName.getText().trim();
            String last = lName.getText().trim();
            if(fName.getText().trim().isEmpty() || lName.getText().trim().isEmpty()){
                fName.setStyle("-fx-background-color: #FDFDFD; -fx-border-color: red; -fx-border-width: 1; -fx-border-style: solid; -fx-border-radius: 4; -fx-background-radius: 4; -fx-prompt-text-fill: #a2a2a2; -fx-font-size: 16px;");
                lName.setStyle("-fx-background-color: #FDFDFD; -fx-border-color: red; -fx-border-width: 1; -fx-border-style: solid; -fx-border-radius: 4; -fx-background-radius: 4; -fx-prompt-text-fill: #a2a2a2; -fx-font-size: 16px;");
                nameError.setText("Please enter your First and Last Name!");
                return;
            }
            if(fName.getText().trim().matches(".*\\d.*") || lName.getText().trim().matches(".*\\d.*")){
                fName.setStyle("-fx-background-color: #FDFDFD; -fx-border-color: red; -fx-border-width: 1; -fx-border-style: solid; -fx-border-radius: 4; -fx-background-radius: 4; -fx-prompt-text-fill: #a2a2a2; -fx-font-size: 16px;");
                lName.setStyle("-fx-background-color: #FDFDFD; -fx-border-color: red; -fx-border-width: 1; -fx-border-style: solid; -fx-border-radius: 4; -fx-background-radius: 4; -fx-prompt-text-fill: #a2a2a2; -fx-font-size: 16px;");
                nameError.setText("Please use letters only for First and Last name!");
                return;
            }

            submitCallback.accept(fName.getText(), lName.getText());

            // Save user data
            submitCallback.accept(first, last);
        }
    }

    /**
     * Removal of error message handler
     * @param ev The Mouse event from User
     */
    @FXML
    private void onFocused(MouseEvent ev){
        if (fName.isFocused() || lName.isFocused()){
            fName.setStyle("-fx-background-color: #FDFDFD; -fx-border-color: #a5a5a5; -fx-border-width: 1; -fx-border-style: solid; -fx-border-radius: 4; -fx-background-radius: 4; -fx-prompt-text-fill: #a2a2a2; -fx-font-size: 16px;");
            lName.setStyle("-fx-background-color: #FDFDFD; -fx-border-color: #a5a5a5; -fx-border-width: 1; -fx-border-style: solid; -fx-border-radius: 4; -fx-background-radius: 4; -fx-prompt-text-fill: #a2a2a2; -fx-font-size: 16px;");
            nameError.setText("");
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
