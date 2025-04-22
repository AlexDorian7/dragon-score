package team.logica_populi.dragonscore.base.logic;

import java.util.List;
import java.util.logging.Logger;

/**
 * A non-abstract version of {@code AbstractQuestion}.
 * @see AbstractQuestion The implimentation of Question
 */
public class BaseQuestion extends AbstractQuestion {

    private static final Logger logger = Logger.getLogger(BaseQuestion.class.getName());

    /**
     * Constructor to make a question given the text, and the answer choices.
     * @param question     The text for the question label
     * @param answers      The answer choices for this question
     */
    public BaseQuestion(String question, Answer... answers) {
        super(question, answers);
    }

    /**
     * Constructor to make a question given the text, and the answer choices as a list.
     * @param question The text for the question label
     * @param answers The answer choices for this question
     */
    public BaseQuestion(String question, List<Answer> answers) {
        super(question, answers);
    }
}
