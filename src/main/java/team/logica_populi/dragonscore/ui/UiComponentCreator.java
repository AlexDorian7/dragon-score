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

    public static Pair<Parent, ParagraphQuestionFormController> createParagraphQuestionFormPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(UiComponentCreator.class.getResource("/assets/views/ParagraphQuestionForm.fxml"));
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e); // If we failed to load the View then it is likely the rest of the program will not work. Throw error.
        }
        ParagraphQuestionFormController controller = fxmlLoader.getController();
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
