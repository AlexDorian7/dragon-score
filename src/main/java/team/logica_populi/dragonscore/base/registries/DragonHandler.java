package team.logica_populi.dragonscore.base.registries;

import com.google.gson.reflect.TypeToken;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.jetbrains.annotations.Nullable;
import team.logica_populi.dragonscore.base.Lesson;
import team.logica_populi.dragonscore.base.json.LessonHeader;
import team.logica_populi.dragonscore.base.logic.Answer;
import team.logica_populi.dragonscore.ui.UiComponentCreator;
import team.logica_populi.dragonscore.ui.controllers.MainMenuController;
import team.logica_populi.dragonscore.ui.controllers.NameFormController;
import team.logica_populi.dragonscore.ui.controllers.QuestionFormController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Registry for handling middle man communications.
 * Also stores state information about the overall state of the program and the users state through it.
 */
public class DragonHandler {
    private static final Logger logger = Logger.getLogger(DragonHandler.class.getName());
    private static final TypeToken<List<LessonHeader>> LESSON_HEADERS_TYPE = new TypeToken<>(){};

    private static DragonHandler currentSession;

    private String name;
    private int points = 0;
    private int pointsToGive = 10;
    private Lesson lesson;

    private Stage stage;
    private Scene mainMenuScene;
    private Scene questionScene;
    private QuestionFormController questionController;

    private List<LessonHeader> lessonHeaders;

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
        setPoints(Integer.max(0, points + getPoints())); // Using max call here to make sure points can never go negative
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
        JsonRegistry.getInstance().getPointSystem().setPoints(name.toLowerCase(), lesson, points);
    }

    private void updatePoints() {
        HashMap<String, HashMap<String, Integer>> records = JsonRegistry.getInstance().getPointSystem().getLessonRecords();
        if (records.containsKey(name)) {
            logger.finer("User Record Found for name");
            HashMap<String, Integer> userRecords = records.get(name.toLowerCase());
            if (userRecords.containsKey(lesson.getId())) {
                points = userRecords.get(lesson.getId());
                return;
            }
        }
        points = 0;
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
     * Sets up a lesson and displays it to the user.
     * @param lesson The lesson to load and run
     */
    private void loadLesson(Lesson lesson) {
        if (stage == null) {
            throw new IllegalStateException("Attempt to show question menu before session was set up!");
        }
        setLesson(lesson);
        updatePoints();
        if (questionScene == null) {
            Pair<Parent, QuestionFormController> questionFormPane = UiComponentCreator.createQuestionFormPane();
            questionController = questionFormPane.getValue();
            questionScene = new Scene(questionFormPane.getKey(), 800, 600);
        }

        questionController.setNextQuestionCallback(() -> {
            // TODO: Make it so if user has at last 100 points, they complete the lesson!
            questionController.setQuestion(lesson.getNextQuestion());
        });
        questionController.setSubmitCallback((List<Answer> selectedAnswers) -> {
            boolean correct = true;
            for (Answer answer : selectedAnswers) {
                if (!answer.isCorrect()) {
                    correct = false;
                    break;
                }
            }
            addPoints(getPointsToGive() * (correct ? 1 : -1));
            questionController.setProgress((double) getPoints() / 100);
            questionController.showCorrect();
        });

        questionController.setProgress((double) getPoints() / 100); // Update the progress bar for the first time
        questionController.setQuestion(lesson.getNextQuestion()); // Display the first question

        stage.setScene(questionScene); // Add the scene to the stage
        stage.show(); // Show the stage
    }

    /**
     * Create the main menu scene
     */
    private void setupMainMenu() {
        Pair<Parent, MainMenuController> mainMenuPane = UiComponentCreator.createMainMenuPane();
        assert JsonRegistry.getInstance().getDataFile() != null; // If there is no loaded data file we should not even be here
        mainMenuPane.getValue().setLessons(lessonHeaders);
        mainMenuPane.getValue().setName(name);
        mainMenuPane.getValue().setStartCallback((LessonHeader lessonHeader) -> {
            JsonRegistry.getInstance().loadDataFile(Objects.requireNonNull(getClass().getResourceAsStream(lessonHeader.location)), true);
            // TODO: MAKE ME LOAD THE LESSON
            logger.finest("Attempt to load " + lessonHeader);
            List<Lesson> lessons = JsonRegistry.getInstance().getDataFile().getLessons();
            for (Lesson lesson : lessons) {
                if (lesson.getId().equals(lessonHeader.id)) {
                    loadLesson(lesson);
                    return;
                }
            }
            logger.warning("No lesson found with id: " + lessonHeader.id);
        });
        mainMenuScene = new Scene(mainMenuPane.getKey(), 600, 400);
    }

    /**
     * Show the main menu scene to the user overwriting whatever scene was there before.
     */
    public void showMainMenu() {
        if (stage == null) {
            throw new IllegalStateException("Attempt to show main menu before session was set up!");
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
            logger.fine("Creating new Point System.");
            JsonRegistry.getInstance().createNewPointSystem();
            return;
        }
        String contents;
        try {
            contents = Files.readString(file.toPath());
            //contents = EncryptionRegistry.getInstance().decrypt(contents);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.fine("Loaded existing Point System.");
        JsonRegistry.getInstance().loadPointSystem(contents, true);
    }

    /**
     * Sets us this session with JavaFX and the back end.
     * This should be the first thing you call on a new session
     * @param stage The state that everything will be displayed to
     * @param lessonHeadersPath The path to the {@link team.logica_populi.dragonscore.base.json.LessonHeader} list resource that will be loaded for this session
     */
    public void setupSession(Stage stage, String lessonHeadersPath) {
        this.stage = stage;
        stage.setTitle("LogiQuest"); // Do other future stage set up here.
        try {
            lessonHeaders = JsonRegistry.getInstance().getGson().fromJson(new String(getClass().getResourceAsStream(lessonHeadersPath).readAllBytes()), LESSON_HEADERS_TYPE.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loadOrCreatePointFile();
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

    /**
     * Gets the amount of points given per question answered.
     * @return The amount of points given
     */
    public int getPointsToGive() {
        return pointsToGive;
    }

    /**
     * Sets the amount of points given per question answered.
     * @param pointsToGive The amount of points to give per question
     */
    public void setPointsToGive(int pointsToGive) {
        this.pointsToGive = pointsToGive;
    }
}
