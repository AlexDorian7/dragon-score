package team.logica_populi.dragonscore.base.registries;

import com.google.gson.reflect.TypeToken;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.jetbrains.annotations.Nullable;
import team.logica_populi.dragonscore.base.Lesson;
import team.logica_populi.dragonscore.base.ResourceLocation;
import team.logica_populi.dragonscore.base.json.LessonHeader;
import team.logica_populi.dragonscore.base.logic.Answer;
import team.logica_populi.dragonscore.base.logic.BooleanLogicTreeNode;
import team.logica_populi.dragonscore.base.logic.Question;
import team.logica_populi.dragonscore.base.points.SubmissionCode;
import team.logica_populi.dragonscore.ui.UiComponentCreator;
import team.logica_populi.dragonscore.ui.controllers.*;

import java.util.HashMap;
import java.util.List;
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
    private SubmissionCode code;

    private Stage stage;
    private Scene mainMenuScene;
    private Scene questionScene;
    private Scene submissionCodeScene;
    private IQuestionFormController questionController;
    private SubmissionCodeController submissionCodeController;

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
     * Adds points.dat to the current point total.
     * @param points The amount of points.dat to add
     */
    protected void addPoints(int points) {
        setPoints(Integer.max(0, points + getPoints())); // Using max call here to make sure points.dat can never go negative
    }

    public Stage getStage(){
        return stage;
    }

    /**
     * Sets the current amount of points.dat tied to this session.
     * @param points The new point count
     */
    protected void setPoints(int points) {
        if (lesson == null) {
            logger.warning("You need to load a lesson before trying to set points.dat");
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
     * Get the current amount of points.dat tied to this session.
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
        name = name.toLowerCase();
        showMainMenu();
    }

    /**
     * Sets the submission code
     * @param code the submission code
     */
    protected void setSubmissionCode(SubmissionCode code) {
         if (lesson == null) {
            logger.warning("You need to load a lesson before trying to set submission.dat");
            return;
        }
        this.code = code;
        JsonRegistry.getInstance().getSubmissionSystem().setSubmission(name.toLowerCase(), lesson, code);
    }

    /**
     * Returns the current submission code for the lesson
     * @return current submission code for lesson
     */
    public SubmissionCode getCode(){
        return code;
    }

    /**
     * Sets up the scene for Submission code as well as generates the code
     */
    private void loadSubmissionCode(){
        if(stage == null) {
            throw new IllegalStateException("Attempt to show submission code pane before session was set up!");
        }
        if(submissionCodeScene == null){
            Pair<Parent, SubmissionCodeController> submissionCodeControllerPane = UiComponentCreator.createSubmissionCodePane();
            submissionCodeController = submissionCodeControllerPane.getValue();
            submissionCodeScene = new Scene(submissionCodeControllerPane.getKey(), 600, 400);
        }

        String regex = "[\\s]";

        String[] arr = name.split(regex);

        code = new SubmissionCode(arr[0], arr[1], getLesson().getId());
        setSubmissionCode(code);

        submissionCodeController.setCode(code.getCode());

        stage.setScene(submissionCodeScene);
        stage.show();
    }

    /**
     * Sets up a lesson and displays it to the user.
     * @param lesson The lesson to load and run
     */
     public void loadLesson(Lesson lesson) {
         if (stage == null) {
             throw new IllegalStateException("Attempt to show question menu before session was set up!");
         }
         setLesson(lesson);
         logger.info("Points required: " + lesson.getPointsRequired());
         updatePoints();
         if (lesson.getFormType() != null) {
             switch (lesson.getFormType()) {
                 case("PARAGRAPH"):
                     Pair<Parent, ParagraphQuestionForm> paragraphQuestionFormPane = UiComponentCreator.createParagraphQuestionFormPane();
                     questionController = paragraphQuestionFormPane.getValue();
                     questionScene = new Scene(paragraphQuestionFormPane.getKey());
                     break;
                 default:
                     Pair<Parent, QuestionFormController> questionFormPane = UiComponentCreator.createQuestionFormPane();
                     questionController = questionFormPane.getValue();
                     questionScene = new Scene(questionFormPane.getKey());
                     break;
             }
         } else {
             Pair<Parent, QuestionFormController> questionFormPane = UiComponentCreator.createQuestionFormPane();
             questionController = questionFormPane.getValue();
             questionScene = new Scene(questionFormPane.getKey());
         }




         questionController.setNextQuestionCallback(() -> {
             // Check for lesson completion
             if(getPoints() >= lesson.getPointsRequired()) {
                 loadSubmissionCode();
                 setPoints(0);
             }
             Question nextQuestion = lesson.getNextQuestion();
             if (nextQuestion.getExtra() != null && nextQuestion.getExtra() instanceof BooleanLogicTreeNode && questionController instanceof QuestionFormController) {
                 ((QuestionFormController) questionController).setQuestion(nextQuestion, (BooleanLogicTreeNode) nextQuestion.getExtra());
             } else {
                 questionController.setQuestion(nextQuestion); // Display next question
             }
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
             questionController.setProgress((double) getPoints() / lesson.getPointsRequired());
             questionController.showCorrect();
         });

         questionController.setProgress((double) getPoints() / lesson.getPointsRequired()); // Update the progress bar for the first time
         Question firstQuestion = lesson.getNextQuestion();
         if ((firstQuestion.getExtra() != null) && (firstQuestion.getExtra() instanceof BooleanLogicTreeNode) && (questionController instanceof QuestionFormController)) {
             ((QuestionFormController) questionController).setQuestion(firstQuestion, (BooleanLogicTreeNode) firstQuestion.getExtra());
         } else {
             questionController.setQuestion(firstQuestion); // Display the first question
         }

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
        mainMenuPane.getValue().setCodes();
        mainMenuPane.getValue().setStartCallback((LessonHeader lessonHeader) -> {
            JsonRegistry.getInstance().loadDataFile(lessonHeader.location().tryGetResource(), true);
            // Load the lesson
            logger.finest("Attempt to load " + lessonHeader);
            List<Lesson> lessons = JsonRegistry.getInstance().getDataFile().getLessons();
            for (Lesson lesson : lessons) {
                if (lesson.getId().equals(lessonHeader.id())) {
                    loadLesson(lesson);
                    return;
                }
            }
            logger.warning("No lesson found with id: " + lessonHeader.id());
        });
        mainMenuScene = new Scene(mainMenuPane.getKey());
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
        ResourceLocation location = new ResourceLocation("dynamic:points.dat");
        if (!location.exists()) {
            logger.fine("Creating new Point System.");
            JsonRegistry.getInstance().createNewPointSystem();
        } else {
            String data = null;
            try{
                data = EncryptionRegistry.getInstance().decrypt(location.tryGetResource());
                logger.info(data);
            } catch (Exception e) {
                JsonRegistry.getInstance().createNewPointSystem();
                return;
            }
            logger.fine("Loaded existing Point System.");
            JsonRegistry.getInstance().loadPointSystem(data, true);
        }
    }

    /**
     * Loads or creates a Submission system with the {@link JsonRegistry}
     */
    private void loadOrCreateSubmissionsFile(){
        ResourceLocation location = new ResourceLocation("dynamic:submissions.dat");
         if (!location.exists()) {
            logger.fine("Creating new Submission System.");
            JsonRegistry.getInstance().createSubmissionSystem();
        } else {
            String data = null;
            try{
                data = EncryptionRegistry.getInstance().decrypt(location.tryGetResource());
                logger.info(data);
            } catch (Exception e) {
                JsonRegistry.getInstance().createSubmissionSystem();
                return;
            }
            logger.fine("Loaded existing Submission System.");
            JsonRegistry.getInstance().loadSubmissionSystem(data, true);
        }
    }

    /**
     * Sets us this session with JavaFX and the back end.
     * This should be the first thing you call on a new session
     * @param stage The state that everything will be displayed to
     * @param lessonHeadersPath The path to the {@link LessonHeader} list resource that will be loaded for this session
     */
    public void setupSession(Stage stage, ResourceLocation lessonHeadersPath) {
        this.stage = stage;
        stage.setMinWidth(600); // Set window dimensions
        stage.setMinHeight(600);
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setTitle("LogiQuest"); // Do other future stage set up here.
        lessonHeaders = JsonRegistry.getInstance().getGson().fromJson(lessonHeadersPath.tryGetResource(), LESSON_HEADERS_TYPE.getType());
        loadOrCreatePointFile();
        loadOrCreateSubmissionsFile();
    }

    /**
     * After setting up this handler, use this to start it.
     * This will handle all the talking between the front and back end.
     */
    public void start() {
        Pair<Parent, NameFormController> nameFormPane = UiComponentCreator.createNameFormPane();
        nameFormPane.getValue().setSubmitCallback(this::handleOnName);
        stage.setScene(new Scene(nameFormPane.getKey(), 800, 600));
        stage.show();
    }

    /**
     * Gets the amount of points.dat given per question answered.
     * @return The amount of points.dat given
     */
    public int getPointsToGive() {
        return pointsToGive;
    }

    /**
     * Sets the amount of points.dat given per question answered.
     * @param pointsToGive The amount of points.dat to give per question
     */
    public void setPointsToGive(int pointsToGive) {
        this.pointsToGive = pointsToGive;
    }
}
