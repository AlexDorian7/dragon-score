package team.logica_populi.dragonscore.base.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * A basic implementation of the {@code Question} interface.
 * @see Question The Question Interface
 */
public abstract class AbstractQuestion implements Question {

    private static final Logger logger = Logger.getLogger(AbstractQuestion.class.getName());

    private String question;

    /**
     * THe internal List of answers for this question
     */
    protected final List<Answer> answers;


    /**
     * Constructor to make a question given the text, and the answer choices.
     * @param question The text for the question label
     * @param answers The answer choices for this question
     */
    protected AbstractQuestion(String question, Answer... answers) {
        this.question = question;
        this.answers = new ArrayList<>(Arrays.stream(answers).toList());
    }

    /**
     * Constructor to make a question given the text, and the answer choices as a list.
     * @param question The text for the question label
     * @param answers The answer choices for this question
     */
    protected AbstractQuestion(String question, List<Answer> answers) {
        this.question = question;
        this.answers = answers;
    }

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

    @Override
    public List<Answer> getAnswers() {
        return answers;
    }

    /**
     * Returns this question as a human-readable string.
     * @return The human-readable string
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(question + "\n");
        int i = 0;
        for (Answer answer : answers) {
            string.append("  ").append(i).append(": ").append(answer).append("\n");
            i++;
        }
        return string.toString();
    }
}
