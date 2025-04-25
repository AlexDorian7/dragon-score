package team.logica_populi.dragonscore;

import javafx.application.Application;
import javafx.stage.Stage;
import team.logica_populi.dragonscore.base.ResourceLocation;
import team.logica_populi.dragonscore.base.registries.DragonHandler;
import team.logica_populi.dragonscore.base.registries.EncryptionRegistry;
import team.logica_populi.dragonscore.base.registries.JsonRegistry;
import team.logica_populi.dragonscore.ui.ErrorWindow;

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

//        Question q = new TrueFalseQuestion("Please find the conclusion in this argument.", true);
//        Pair<Parent, ParagraphQuestionForm> paragraphQuestionFormPane = UiComponentCreator.createParagraphQuestionFormPane();
//        paragraphQuestionFormPane.getValue().setQuestion(q);
//        stage.setScene(new Scene(paragraphQuestionFormPane.getKey(), 800, 600));
//        stage.show();

        try {
            DragonHandler dragonHandler = DragonHandler.newSession();
            dragonHandler.setupSession(stage, new ResourceLocation("index.json"));
            dragonHandler.start();
        } catch (Exception e) {
            ErrorWindow.makeErrorWindow(stage, e, this);
        }

    }

    /**
     * JavaFX Stop Method.
     * @throws Exception Any exception thrown when program is stopping
     */
    @Override
    public void stop() throws Exception{
        ResourceLocation loc1 = new ResourceLocation("dynamic:points.dat");
        loc1.createIfNotExists();
        if (loc1.tryGetResource().isEmpty()){
            loc1.write(EncryptionRegistry.getInstance().encrypt(JsonRegistry.getInstance().getGson().toJson(JsonRegistry.getInstance().createNewPointSystem().getLessonRecords())));
        }
        else{
            loc1.write(EncryptionRegistry.getInstance().encrypt(JsonRegistry.getInstance().getGson().toJson(JsonRegistry.getInstance().getPointSystem().getLessonRecords())));
        }

        ResourceLocation loc2 = new ResourceLocation("dynamic:submissions.dat");
        loc2.createIfNotExists();
        if(loc2.tryGetResource().isEmpty()){
           loc2.write(EncryptionRegistry.getInstance().encrypt(JsonRegistry.getInstance().getGson().toJson(JsonRegistry.getInstance().createSubmissionSystem().getSubmissions())));
        }
        else{
            loc2.write(EncryptionRegistry.getInstance().encrypt(JsonRegistry.getInstance().getGson().toJson(JsonRegistry.getInstance().getSubmissionSystem().getSubmissions())));
        }
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