package team.logica_populi.dragonscore.base.logic;

/**
 * A simple implementation of {@link AbstractQuestion} with only two answers being true and false
 */
public class YesNoQuestion extends AbstractQuestion {

    private Boolean isTrue;

    /**
     * Constructor to make a true false question given the text, and if the question is true.
     *
     * @param question     The text for the question label
     * @param isTrue       Is the question true
     */
    public YesNoQuestion(String question, boolean isTrue) {
        super(question, new Answer("Yes", isTrue), new Answer("No", !isTrue));
        this.isTrue = isTrue;
    }

    /**
     * Is this true false question true.
     * It is important to note that this may not always reflect the correctness of the answers
     * @return The truth value of this question
     */
    public Boolean getTrue() {
        return isTrue;
    }
}
