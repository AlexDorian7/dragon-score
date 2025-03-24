package team.logica_populi.dragonscore.base.logic;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * This class represents a possible answer to a question.
 * This answer may or may not be the correct one.
 * It is important to note that iss is the job of the UI controller to make sure the questions are displayed in a random order.
 */
public class Answer implements Cloneable {

    private static final Logger logger  = Logger.getLogger(Answer.class.getName());

    private String text;
    private UUID id;
    private boolean correct;

    /**
     * Constructor to make an Answer using the provided text. The answer defaults to being incorrect.
     * @param text The text for this answer's label
     */
    public Answer(String text) {
        this(text, false);
    }

    /**
     * Constructor to make an Answer using the provided text and the provided correctness.
     * @param text The text for this answer's label
     * @param correct Is this answer correct or not.
     */
    public Answer(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
        id = UUID.randomUUID();
    }

    /**
     * Gets the text of this answer's label.
     * @return The answer's text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the answer's text
     * @param text The text to set the answer's label to
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Clone the answer into a new instance.
     * It should be noted that given an Answer a
     * <blockquote><pre>
     *    a.clone().equals(a);
     * </pre></blockquote>
     * will return true.
     * @return The cloned answer
     */
    @Override
    public Answer clone() {
        try {
            Answer clone = (Answer) super.clone();
            clone.id = UUID.fromString(id.toString());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    /**
     * Checks if two answers are equal using their Ids.
     * @param obj The object to check against. This will be false if it is not of type {@code Answer}
     * @return If the two provided answers are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != Answer.class) return false; // Not an Answer object
        return id.equals(((Answer) obj).id);
    }

    /**
     * Returns this answer as a human-readable string.
     * @return The human-readable string
     */
    @Override
    public String toString() {
        return text;
    }

    /**
     * Is this answer marked as correct?
     * @return Is Correct
     */
    public boolean isCorrect() {
        return correct;
    }

    /**
     * Sets the correct state of this answer.
     * @param correct The state to set to
     */
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    /**
     * Loads an Answer from a JSON object.
     * @param object The object to load from
     * @return The loaded answer or null if something failed.
     */
    @Nullable
    public static Answer loadFromJSON(JSONObject object) {
        String answerText = object.getString("answer");
        boolean correct = object.getBoolean("correct");
        if (answerText == null || answerText.isEmpty()) {
            logger.warning("Failed to load answer. Empty or null answer text.");
            return null;
        }
        return new Answer(answerText, correct);
    }
}
