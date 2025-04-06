package team.logica_populi.dragonscore;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import team.logica_populi.dragonscore.base.DataFile;
import team.logica_populi.dragonscore.base.Lesson;
import team.logica_populi.dragonscore.base.logic.Answer;
import team.logica_populi.dragonscore.base.registries.JsonRegistry;
import team.logica_populi.dragonscore.ui.UiComponentCreator;
import team.logica_populi.dragonscore.ui.controllers.DefinitionsExampleController;
import team.logica_populi.dragonscore.base.points.PointSystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * This is the main entrypoint to the application.
 * This code sets up the logger and other required systems as well as providing some example code.
 * @see Main#start(Stage)
 */
public class Main extends Application {

    private static Logger logger;

    /**
     * JavaFX Start Method.
     * Currently, this contains mostly example code.
     * @param stage The default stage created by JavaFX for us
     * @throws Exception Any exception thrown by our code to be sent back to JavaFX
     */
    @Override
    public void start(Stage stage) throws Exception {
        Pair<Parent, DefinitionsExampleController> pair = UiComponentCreator.createDefinitionExamplePane();

        DataFile dataFile = JsonRegistry.getInstance().loadDataFile(Objects.requireNonNull(getClass().getResourceAsStream("/data/lessons/definitions.json")), true);
        PointSystem records = JsonRegistry.getInstance().loadPointSystem(Objects.requireNonNull(getClass().getResourceAsStream("/data/pointsystem.example.json")), true);
        Lesson lesson = dataFile.getLessons().getFirst();

        records.setPoints("eric", lesson, 0);

        pair.getValue().setSubmitCallback((List<Answer> selectedAnswers) -> {
            pair.getValue().setQuestion(lesson.getNextQuestion());
        });
        pair.getValue().setQuestion(lesson.getNextQuestion());

        Scene scene = new Scene(pair.getKey(), 800, 600);
        stage.setScene(scene);
        stage.setTitle("EXAMPLE");
        stage.show();

    }

    /**
     * The main entry point to the program.
     * This code sets up the logger properties and then starts JavaFX.
     * @param args The command line args
     */
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
