package team.logica_populi.dragonscore;

import javafx.application.Application;
import javafx.stage.Stage;
import team.logica_populi.dragonscore.base.registries.DragonHandler;

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

        DragonHandler dragonHandler = DragonHandler.newSession();
        dragonHandler.setupSession(stage, "/data/index.json");
        dragonHandler.start();

    }

    /**
     * JavaFX Stop Method.
     * @throws Exception Any exception thrown when program is stopping
     */
//    @Override
//    public void stop() throws Exception{
//        File file = new File("./points");
//        FileWriter writer = new FileWriter(file);
//        //writer.write(EncryptionRegistry.getInstance().encrypt(String.valueOf(file)));
//        writer.close();
//        logger.info("Application Stopping ...");
//    }


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
