package team.logica_populi.dragonscore.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;

import javax.print.DocFlavor;
import java.util.function.BiConsumer;
import java.io.*;

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

    private static final String FILE_PATH = "user.json";

    // User Class
    private static class User {
        String firstName;
        String lastName;

        User(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    // Simple JSON string value extractor
    private String extractValue(String json, String key) {
        String pattern = "\"" + key + "\":\"";
        int start = json.indexOf(pattern);
        if (start == -1) return null;

        start += pattern.length();
        int end = json.indexOf("\"", start);
        if (end == -1) return null;

        return unescapeJson(json.substring(start, end));
    }

    private String escapeJson(String value) {
        return value.replace("\"", "\\\"");
    }

    private String unescapeJson(String value) {
        return value.replace("\\\"", "\"");
    }

    // Save user data to JSON manually
    private void saveUser(User user) {
        String json = "{\n" +
                "  \"firstName\": \"" + escapeJson(user.firstName) + "\",\n" +
                "  \"lastName\": \"" + escapeJson(user.lastName) + "\"\n" +
                "}";
        try (Writer writer = new FileWriter(FILE_PATH)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Load user data from JSON manually
    private User loadUser() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line.trim());
            }
            String json = jsonBuilder.toString();
            String firstName = extractValue(json, "firstName");
            String lastName = extractValue(json, "lastName");
            if (firstName != null && lastName != null) {
                return new User(firstName, lastName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @FXML
    public void initialize() {
        // Assuming 'fName' is your container, or replace with your actual root container node:
        fName.setOnAction(e -> submit(new ActionEvent()));
        lName.setOnAction(e -> submit(new ActionEvent()));

        //load user data
        User savedUser = loadUser();
        if(savedUser != null) {
            fName.setText(savedUser.firstName);
            lName.setText(savedUser.lastName);
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
            submitCallback.accept(fName.getText(), lName.getText());

            // Save user data
            saveUser(new User(first, last));
            submitCallback.accept(first, last);
        }
    }

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
