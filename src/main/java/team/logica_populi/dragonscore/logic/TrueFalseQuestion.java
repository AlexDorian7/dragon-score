package team.logica_populi.dragonscore.logic;

public class TrueFalseQuestion extends AbstractQuestion {

    private static final Answer TRUE_ANSWER = new Answer("True");
    private static final Answer FALSE_ANSWER = new Answer("False");

    private Boolean isTrue;

    /**
     * Constructor to make a true false question given the text, and if the question is true.
     *
     * @param question     The text for the question label
     * @param isTrue       Is the question true
     */
    public TrueFalseQuestion(String question, boolean isTrue) {
        super(question, new Answer("True", isTrue), new Answer("False", !isTrue));
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
