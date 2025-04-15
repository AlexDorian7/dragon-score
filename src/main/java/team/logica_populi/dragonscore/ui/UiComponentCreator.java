package team.logica_populi.dragonscore.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Pair;
import team.logica_populi.dragonscore.ui.controllers.*;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * A static class that is used to help create and demo how to create FXML components
 */
public class UiComponentCreator {
    private static final Logger logger = Logger.getLogger(UiComponentCreator.class.getName());

    /**
     * Create a new Example Question pane
     * @return A pair where the key is the pane and the value is the controller for the pane
     */
    public static Pair<Parent, ExampleQuestionPane> createExampleQuestionPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(UiComponentCreator.class.getResource("/assets/views/ExampleQuestionPane.fxml"));
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e); // If we failed to load the View then it is likely the rest of the program will not work. Throw error.
        }
        ExampleQuestionPane controller = fxmlLoader.getController();
        return new Pair<>(parent, controller);
    }

    public static Pair<Parent, QuestionFormController> createQuestionFormPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(UiComponentCreator.class.getResource("/assets/views/QuestionForm.fxml"));
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e); // If we failed to load the View then it is likely the rest of the program will not work. Throw error.
        }
        QuestionFormController controller = fxmlLoader.getController();
        return new Pair<>(parent, controller);
    }

    public static Pair<Parent, DefinitionsExampleController> createDefinitionExamplePane() {
        FXMLLoader fxmlLoader = new FXMLLoader(UiComponentCreator.class.getResource("/assets/views/DefinitionsExample.fxml"));
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e); // If we failed to load the View then it is likely the rest of the program will not work. Throw error.
        }
        DefinitionsExampleController controller = fxmlLoader.getController();
        return new Pair<>(parent, controller);
    }

    public static Pair<Parent, NameFormController> createNameFormPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(UiComponentCreator.class.getResource("/assets/views/NameForm.fxml"));
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e); // If we failed to load the View then it is likely the rest of the program will not work. Throw error.
        }
        NameFormController controller = fxmlLoader.getController();
        return new Pair<>(parent, controller);
    }

    public static Pair<Parent, MainMenuController> createMainMenuPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(UiComponentCreator.class.getResource("/assets/views/MainMenu.fxml"));
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e); // If we failed to load the View then it is likely the rest of the program will not work. Throw error.
        }
        MainMenuController controller = fxmlLoader.getController();
        return new Pair<>(parent, controller);
    }

    public static Pair<Parent, SubmissionCodeController> createSubmissionCodePane(){
        FXMLLoader fxmlLoader = new FXMLLoader(UiComponentCreator.class.getResource("/assets/views/SubmissionCode.fxml"));
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e); // If we failed to load the View then it is likely the rest of the program will not work. Throw error.
        }
        SubmissionCodeController controller = fxmlLoader.getController();
        return new Pair<>(parent, controller);
    }
}
