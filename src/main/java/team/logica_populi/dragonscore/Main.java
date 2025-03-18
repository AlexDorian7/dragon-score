package team.logica_populi.dragonscore;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import team.logica_populi.dragonscore.logic.Question;
import team.logica_populi.dragonscore.logic.generators.ExampleQuestionGenerator;
import team.logica_populi.dragonscore.logic.generators.QuestionGenerator;
import team.logica_populi.dragonscore.logic.generators.QuestionGeneratorRegistry;
import team.logica_populi.dragonscore.ui.UiComponentCreator;
import team.logica_populi.dragonscore.ui.controllers.ExampleQuestionPane;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * This is the main entrypoint to the application.
 * For now all this does is create a example window
 */
public class Main extends Application {

    private static Logger logger;

    @Override
    public void start(Stage stage) throws Exception {
        Pair<Parent, ExampleQuestionPane> pair = UiComponentCreator.createExampleQuestionPane();

        // Create an example question and then put it in the example window
        QuestionGenerator questionGenerator = QuestionGeneratorRegistry.getInstance().getQuestionGenerator("team.logica_populi.dragonscore.logic.generators.ExampleQuestionGenerator");
        if (questionGenerator == null) {
            throw new IllegalStateException("Question generator is null");
        }
        Question nextQuestion = questionGenerator.getNextQuestion();

        logger.info(questionGenerator.getId());

        pair.getValue().setQuestion(nextQuestion);

        Scene scene = new Scene(pair.getKey(), 600, 400);
        stage.setScene(scene);
        stage.setTitle("EXAMPLE");
        stage.show();
    }

    public static void main(String[] args) {
        // Set up the logger
        try {
            InputStream stream = Main.class.getResourceAsStream("/logging.properties");
            LogManager.getLogManager().readConfiguration(stream);
            logger = Logger.getLogger(Main.class.getName());
            logger.info("Welcome!");
        } catch (IOException e) {
            logger = Logger.getLogger(Main.class.getName());
            logger.warning("Failed to load logging.properties");
        }

        launch(args); // Tell JavaFX to launch
    }
}
