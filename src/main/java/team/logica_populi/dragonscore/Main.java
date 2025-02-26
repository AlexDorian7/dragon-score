package team.logica_populi.dragonscore;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
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
    @Override
    public void start(Stage stage) throws Exception {
        Pair<Parent, ExampleQuestionPane> pair = UiComponentCreator.createExampleQuestionPane();
        Scene scene = new Scene(pair.getKey(), 600, 400);
        stage.setScene(scene);
        stage.setTitle("EXAMPLE");
        stage.show();
    }

    public static void main(String[] args) {
        Logger logger;

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
