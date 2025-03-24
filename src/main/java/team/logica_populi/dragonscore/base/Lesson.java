package team.logica_populi.dragonscore.base;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import team.logica_populi.dragonscore.base.registries.QuestionGeneratorRegistry;
import team.logica_populi.dragonscore.logic.BaseQuestion;
import team.logica_populi.dragonscore.logic.Question;
import team.logica_populi.dragonscore.logic.generators.QuestionGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Lesson {
    private static final Logger logger = Logger.getLogger(Lesson.class.getName());

    private final String id;
    private String name;
    private String description;
    private final List<Question> staticQuestions;
    private final List<QuestionGenerator> questionGenerators;

    public Lesson(String id) {
        this(id, "", "", new ArrayList<>(), new ArrayList<>());
    }

    public Lesson(String id, String name) {
        this(id, name, "", new ArrayList<>(), new ArrayList<>());
    }

    public Lesson(String id, List<Question> staticQuestions, List<QuestionGenerator> questionGenerators) {
        this(id, "", "", staticQuestions, questionGenerators);
    }

    public Lesson(String id, String name, List<Question> staticQuestions, List<QuestionGenerator> questionGenerators) {
        this(id, name, "", staticQuestions, questionGenerators);
    }

    public Lesson(String id, String name, String description, List<Question> staticQuestions, List<QuestionGenerator> questionGenerators) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.staticQuestions = staticQuestions;
        this.questionGenerators = questionGenerators;
    }

    /**
     * @return The lesson's id
     */
    public String getId() {
        return id;
    }

    /**
     * @return The lesson's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the lesson's name.
     * @param name The name to set to
     */
    protected void setName(String name) {
        this.name = name;
    }

    /**
     * @return The lesson's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the lesson's description.
     * @param description The description to set to
     */
    protected void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The list of static questions for this lesson
     */
    public List<Question> getStaticQuestions() {
        return staticQuestions;
    }

    /**
     * @return The list of question generators for this lesson
     */
    public List<QuestionGenerator> getQuestionGenerators() {
        return questionGenerators;
    }

    /**
     * Create a new question from a generator or the static pool.
     * <p>
     * It should be worth noting that with using both static questions and question generators in the same lesson
     * users will notice static questions more often than generated ones.
     * @return The new question
     */
    public Question getNextQuestion() {
        if (staticQuestions.isEmpty() && questionGenerators.isEmpty()) {
            throw new IllegalStateException("This lesson has no questions or generators to choose from!");
        }
        if (staticQuestions.isEmpty()) {
            QuestionGenerator questionGenerator = questionGenerators.get((int) (Math.random() * questionGenerators.size()));
            return questionGenerator.getNextQuestion();
        }
        if (questionGenerators.isEmpty()) {
            return staticQuestions.get((int) (Math.random() * staticQuestions.size()));
        }
        if (Math.random() < 0.5) {
            QuestionGenerator questionGenerator = questionGenerators.get((int) (Math.random() * questionGenerators.size()));
            return questionGenerator.getNextQuestion();
        }
        return staticQuestions.get((int) (Math.random() * staticQuestions.size()));
    }

    /**
     * Loads a lesson from the provided JSON object.
     * @param object The object to load from
     * @return The loaded less or null if something failed
     */
    @Nullable
    public static Lesson loadFromJSON(JSONObject object) {
        String id = object.getString("id");
        if (id == null || id.isEmpty()) {
            logger.warning("Failed to load lesson. id is empty or null.");
            return null;
        }
        String name = object.getString("name");
        if (name == null) name = "";
        String description = object.getString("description");
        if (description == null) description = "";
        ArrayList<Question> staticQuestions = new ArrayList<>();
        ArrayList<QuestionGenerator> questionGenerators = new ArrayList<>();

        JSONArray staticQuestionArray = object.getJSONArray("staticQuestions");
        for (Object questionObject : staticQuestionArray) {
            if (!(questionObject instanceof JSONObject)) continue;
            Question question = BaseQuestion.loadFromJSON((JSONObject) questionObject);
            if (question == null) {
                logger.warning("Failed to load lesson. A static question failed to load.");
                return null;
            }
            staticQuestions.add(question);
        }
        JSONArray questionGeneratorArray = object.getJSONArray("questionGenerators");
        for (Object generatorObject : questionGeneratorArray) {
            if (!(generatorObject instanceof String generatorId)) continue;
            if (generatorId.isEmpty()) {
                logger.warning("Lesson failed to load. Generator id was empty.");
                return null;
            }
            QuestionGenerator generator = QuestionGeneratorRegistry.getInstance().getQuestionGenerator(generatorId);
            if (generator == null) {
                logger.warning("Lesson Failed to load. A generator failed to load.");
                return null;
            }
            questionGenerators.add(generator);
        }
        return new Lesson(id, name, description, staticQuestions, questionGenerators);
    }

    @Override
    public String toString() {
        return id + ": " + name + "\n> " + description + "\nStatic Questions: " + staticQuestions.size() + "\nQuestion Generators: " + questionGenerators.size();
    }
}
