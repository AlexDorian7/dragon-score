package team.logica_populi.dragonscore.logic;

/**
 * A non-abstract version of {@code AbstractQuestion}.
 * @see AbstractQuestion The implimentation of Question
 */
public class BaseQuestion extends AbstractQuestion {
    /**
     * Constructor to make a question given the text, correct answer index, and the answer choices.
     *
     * @param question     The text for the question label
     * @param correctIndex The index into the answers of the correct answer
     * @param answers      The answer choices for this question
     */
    public BaseQuestion(String question, int correctIndex, Answer... answers) {
        super(question, correctIndex, answers);
    }
}
