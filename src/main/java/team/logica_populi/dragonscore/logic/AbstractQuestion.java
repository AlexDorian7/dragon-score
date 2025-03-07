package team.logica_populi.dragonscore.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * A basic implimentation of the {@code Question} interface.
 * @see Question The Question Interface
 */
public abstract class AbstractQuestion implements Question {

    private static final Logger logger = Logger.getLogger(AbstractQuestion.class.getName());

    private String question;

    private List<Answer> answers;

    private int correctIndex;

    /**
     * Constructor to make a question given the text, correct answer index, and the answer choices.
     * @param question The text for the question label
     * @param correctIndex The index into the answers of the correct answer
     * @param answers The answer choices for this question
     */
    protected AbstractQuestion(String question, int correctIndex, Answer... answers) {
        this.question = question;
        this.correctIndex = correctIndex;
        this.answers = Arrays.stream(answers).toList();

        if (correctIndex < 0 || correctIndex >= answers.length) {
            logger.warning("Correct Answer Warning: " + correctIndex + " is out of bounds for answers of size " + this.answers.size());
        }
    }

    /**
     * Gets the question text.
     * @return The text of the question
     */
    @Override
    public String getQuestion() {
        return question;
    }

    /**
     * Sets the text of the question's label.
     * @param question The text to set the label to
     */
    protected void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Gets a reference to the correct answer for this question.
     * @return The correct answer
     */
    @Override
    public Answer getCorrectAnswer() {
        return answers.get(correctIndex);
    }

    /**
     * Gets a List of all answers for this question.
     *
     * @return The answers for this question
     */
    @Override
    public List<Answer> getAnswers() {
        return answers;
    }
}
