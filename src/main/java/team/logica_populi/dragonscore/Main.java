package team.logica_populi.dragonscore;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import team.logica_populi.dragonscore.base.logic.Question;
import team.logica_populi.dragonscore.base.logic.generators.ExampleQuestionGenerator;
import team.logica_populi.dragonscore.base.registries.DragonHandler;
import team.logica_populi.dragonscore.ui.UiComponentCreator;
import team.logica_populi.dragonscore.ui.controllers.DefinitionsExampleController;
import team.logica_populi.dragonscore.ui.controllers.QuestionFormController;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * This is the main entrypoint to the application.
 * This code sets up the logger and other required systems.
 * @see Main#start(Stage)
 */
public class Main extends Application {

    private static Logger logger;

    /**
     * JavaFX Start Method.
     * @param stage The default stage created by JavaFX for us
     * @throws Exception Any exception thrown by our code to be sent back to JavaFX
     */
    @Override
    public void start(Stage stage) throws Exception {

//        ExampleQuestionGenerator generator = new ExampleQuestionGenerator();
//        Question nextQuestion = generator.getNextQuestion();
//
//        Pair<Parent, QuestionFormController> definitionExamplePane = UiComponentCreator.createQuestionFormPane();
//        definitionExamplePane.getValue().setQuestion(nextQuestion);
//        stage.setScene(new Scene(definitionExamplePane.getKey(), 800, 600));
//        stage.show();


        DragonHandler dragonHandler = DragonHandler.newSession();
        dragonHandler.setupSession(stage, "/data/index.json");
        dragonHandler.start();

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
