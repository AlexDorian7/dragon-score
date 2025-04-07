package team.logica_populi.dragonscore.base.registries;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.jetbrains.annotations.Nullable;
import team.logica_populi.dragonscore.base.Lesson;
import team.logica_populi.dragonscore.base.points.PointSystem;
import team.logica_populi.dragonscore.ui.UiComponentCreator;
import team.logica_populi.dragonscore.ui.controllers.MainMenuController;
import team.logica_populi.dragonscore.ui.controllers.NameFormController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Registry for handling middle man communications.
 * Also stores state information about the overall state of the program and the users state through it.
 */
public class DragonHandler {
    private static final Logger logger = Logger.getLogger(DragonHandler.class.getName());

    private static DragonHandler currentSession;

    private String name;
    private int points = 0;
    private Lesson lesson;

    private final PointSystem pointSystem = new PointSystem();
    private Stage stage;
    private Scene mainMenuScene;

    /**
     * Default constructor.
     * @param name The name of the user currently using the program
     */
    private DragonHandler(String name) {
        this.name = name;
    }

    /**
     * Makes a new Session from the provided name.
     * @return The new session
     */
    public static DragonHandler newSession() {
        if (currentSession != null)
            logger.fine("Replacing old session with new one.");
        currentSession = new DragonHandler("UNKNOWN");
        return currentSession;
    }

    /**
     * Gets the current session.
     * @return The current session or null if none
     */
    @Nullable
    public static DragonHandler getCurrentSession() {
        return currentSession;
    }

    /**
     * Gets the name of the user for this current session.
     * @return The user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Adds points to the current point total.
     * @param points The amount of points to add
     */
    protected void addPoints(int points) {
        setPoints(points + getPoints());
    }

    /**
     * Sets the current amount of points tied to this session.
     * @param points The new point count
     */
    protected void setPoints(int points) {
        if (lesson == null) {
            logger.warning("You need to load a lesson before trying to set points");
            return;
        }
        this.points = points;
        pointSystem.setPoints(name, lesson, points);
    }

    /**
     * Get the current amount of points tied to this session.
     * @return The current point count
     */
    public int getPoints() {
        return points;
    }

    /**
     * Gets the selected lesson for this session.
     * @return The selected lesson
     */
    public Lesson getLesson() {
        return lesson;
    }

    /**
     * Sets the selected lesson for this session.
     * @param lesson The lesson to select
     */
    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    /**
     * Handles the form submit of the name form.
     * @param fName The user's first name
     * @param lName The user's last name
     */
    private void handleOnName(String fName, String lName) {
        name = fName + " " + lName;
        showMainMenu();
    }

    /**
     * Create the main menu scene
     */
    private void setupMainMenu() {
        Pair<Parent, MainMenuController> mainMenuPane = UiComponentCreator.createMainMenuPane();
        assert JsonRegistry.getInstance().getDataFile() != null; // If there is no loaded data file we should not even be here
        mainMenuPane.getValue().setLessons(JsonRegistry.getInstance().getDataFile().getLessons());
        mainMenuPane.getValue().setName(name);
        mainMenuPane.getValue().setStartCallback((Lesson lesson) -> {
            // TODO: MAKE ME LOAD THE LESSON
            logger.info("Attempt to load " + lesson);
        });
        mainMenuScene = new Scene(mainMenuPane.getKey(), 600, 400);
    }

    /**
     * Show the main menu scene to the user overwriting whatever scene was there before.
     */
    public void showMainMenu() {
        if (stage == null) {
            throw new IllegalStateException("Attempt to show main menu before session was set up");
        }
        if (mainMenuScene == null) {
            setupMainMenu();
        }
        stage.setScene(mainMenuScene);
        stage.show();
    }

    /**
     * Loads or creates a Point system with the {@link JsonRegistry}.
     */
    private void loadOrCreatePointFile() {

        File file = new File("./points");
        if (!file.exists()) {
            JsonRegistry.getInstance().createNewPointSystem();
            return;
        }
        String contents;
        try {
            contents = Files.readString(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsonRegistry.getInstance().loadPointSystem(contents, true);
    }

    /**
     * Sets us this session with JavaFX and the back end.
     * This should be the first thing you call on a new session
     * @param stage The state that everything will be displayed to
     * @param dataFilePath The path to the data file resource that will be loaded for this session
     */
    public void setupSession(Stage stage, String dataFilePath) {
        this.stage = stage;
        stage.setTitle("LogiQuest"); // Do other future stage set up here.
        JsonRegistry.getInstance().loadDataFile(Objects.requireNonNull(getClass().getResourceAsStream(dataFilePath)), true);
    }

    /**
     * After setting up this handler, use this to start it.
     * This will handle all the talking between the front and back end.
     */
    public void start() {
        Pair<Parent, NameFormController> nameFormPane = UiComponentCreator.createNameFormPane();
        nameFormPane.getValue().setSubmitCallback(this::handleOnName);
        stage.setScene(new Scene(nameFormPane.getKey(), 400, 400));
        stage.show();
    }
}
