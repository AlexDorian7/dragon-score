package team.logica_populi.dragonscore.base;

import org.jetbrains.annotations.Nullable;
import team.logica_populi.dragonscore.base.logic.BaseQuestion;
import team.logica_populi.dragonscore.base.logic.Question;
import team.logica_populi.dragonscore.base.logic.generators.QuestionGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A lesson represents a set of questions or question generators that fit a common theme.
 * A lesson also contains a name, description, and internal id.
 */
public class Lesson {
    private static final Logger logger = Logger.getLogger(Lesson.class.getName());

    private final String id;
    private String name;
    private String description;
    private final List<BaseQuestion> staticQuestions;
    private final List<QuestionGenerator> questionGenerators;
    private final boolean randomize;
    private final int pointsRequired;

    @Nullable
    private String formType;

    /**
     * Create a lesson using only an internal id.
     * The name and description will be empty strings.
     * @param id The lesson's is
     */
    public Lesson(String id) {
        this(id, "", "", new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Create a lesson using only an internal id and a name.
     * The description will be an empty string.
     * @param id The lesson's id
     * @param name The lesson's name
     */
    public Lesson(String id, String name) {
        this(id, name, "", new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Create a lesson using only an internal id, a name, and a description.
     * The description will be an empty string.
     * @param id The lesson's id
     * @param name The lesson's name
     * @param description The lesson's description
     */
    public Lesson(String id, String name, String description) {
        this(id, name, description, new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Create a lesson using an internal id and lists for static questions and question generators.
     * @param id The lesson's id
     * @param staticQuestions The static questions assigned to this lesson
     * @param questionGenerators The question generators assigned to this lesson
     */
    public Lesson(String id, List<BaseQuestion> staticQuestions, List<QuestionGenerator> questionGenerators) {
        this(id, "", "", staticQuestions, questionGenerators);
    }

    /**
     * Create a lesson using an internal id, name, and lists for static questions and question generators.
     * @param id The lesson's id
     * @param name The lesson's name
     * @param staticQuestions The static questions assigned to this lesson
     * @param questionGenerators The question generators assigned to this lesson
     */
    public Lesson(String id, String name, List<BaseQuestion> staticQuestions, List<QuestionGenerator> questionGenerators) {
        this(id, name, "", staticQuestions, questionGenerators);
    }

    /**
     * Create a lesson using an internal id, name, description, and lists for static questions and question generators.
     * @param id The lesson's id
     * @param name The lesson's name
     * @param description The lesson's description
     * @param staticQuestions The static questions assigned to this lesson
     * @param questionGenerators The question generators assigned to this lesson
     */
    public Lesson(String id, String name, String description, List<BaseQuestion> staticQuestions, List<QuestionGenerator> questionGenerators) {
        this(id, name, description, staticQuestions, questionGenerators, true);
    }

    /**
     * Create a lesson using an internal id, name, description, and lists for static questions and question generators.
     * @param id The lesson's id
     * @param name The lesson's name
     * @param description The lesson's description
     * @param staticQuestions The static questions assigned to this lesson
     * @param questionGenerators The question generators assigned to this lesson
     * @param randomize Randomize the order of answers for questions in this lesson
     */
    public Lesson(String id, String name, String description, List<BaseQuestion> staticQuestions, List<QuestionGenerator> questionGenerators, boolean randomize) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.staticQuestions = staticQuestions;
        this.questionGenerators = questionGenerators;
        this.randomize = randomize;
        this.pointsRequired = 100;
    }

    /**
     * Gets the lesson's id.
     * @return the lesson's id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the lesson's name.
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
     * Gets the lesson's description.
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
     * Get the list of static questions assigned to this lesson.
     * @return The list of static questions for this lesson
     */
    public List<BaseQuestion> getStaticQuestions() {
        return staticQuestions;
    }

    /**
     * Gets the list of question generators assigned to this lesson.
     * @return The list of question generators for this lesson
     */
    public List<QuestionGenerator> getQuestionGenerators() {
        return questionGenerators;
    }

    private Question randomizeAnswers(Question question) {
        if (randomize) Collections.shuffle(question.getAnswers());
        return question;
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
            return randomizeAnswers(questionGenerator.getNextQuestion());
        }
        if (questionGenerators.isEmpty()) {
            return randomizeAnswers(staticQuestions.get((int) (Math.random() * staticQuestions.size())));
        }
        if (Math.random() < 0.5) {
            QuestionGenerator questionGenerator = questionGenerators.get((int) (Math.random() * questionGenerators.size()));
            return randomizeAnswers(questionGenerator.getNextQuestion());
        }
        return randomizeAnswers(staticQuestions.get((int) (Math.random() * staticQuestions.size())));
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Gets the amount of points required for this lesson to be considered complete.
     * @return The amount of points required
     */
    public int getPointsRequired() {
        return pointsRequired;
    }

    /**
     * Gets the type of window form to use for this lesson.
     * @return The current window form type
     */
    @Nullable
    public String getFormType() {
        return formType;
    }

    /**
     * Sets the type of window form to use for this lesson.
     * Current valid options are {@code null}, and {@code "PARAGRAPH"}
     * @param formType The window form type to set
     */
    public void setFormType(@Nullable String formType) {
        this.formType = formType;
    }
}
