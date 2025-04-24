package team.logica_populi.dragonscore;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import team.logica_populi.dragonscore.base.ResourceLocation;
import team.logica_populi.dragonscore.base.logic.Question;
import team.logica_populi.dragonscore.base.logic.TrueFalseQuestion;
import team.logica_populi.dragonscore.base.registries.DragonHandler;
import team.logica_populi.dragonscore.base.registries.EncryptionRegistry;
import team.logica_populi.dragonscore.base.registries.JsonRegistry;
import team.logica_populi.dragonscore.ui.UiComponentCreator;
import team.logica_populi.dragonscore.ui.controllers.ParagraphQuestionForm;
import team.logica_populi.dragonscore.ui.controllers.QuestionFormController;

import java.io.File;
import java.io.FileWriter;
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

//        Question q = new TrueFalseQuestion("Hi", true);
//        Pair<Parent, ParagraphQuestionForm> paragraphQuestionFormPane = UiComponentCreator.createParagraphQuestionFormPane();
//        paragraphQuestionFormPane.getValue().setQuestion(q);
//        stage.setScene(new Scene(paragraphQuestionFormPane.getKey(), 800, 600));
//        stage.show();

        DragonHandler dragonHandler = DragonHandler.newSession();
        dragonHandler.setupSession(stage, new ResourceLocation("index.json"));
        dragonHandler.start();

    }

    /**
     * JavaFX Stop Method.
     * @throws Exception Any exception thrown when program is stopping
     */
    @Override
    public void stop() throws Exception{
        File file1 = new File("dynamic/points.dat");
        EncryptionRegistry.getInstance().EncryptFile(file1);
        File file2 = new File("dynamic/submissions.dat");
        EncryptionRegistry.getInstance().EncryptFile(file2);

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