package team.logica_populi.dragonscore.logic;

import java.util.UUID;

/**
 * This class represents a possible answer to a question.
 * This answer may or may not be the correct one.
 */
public class Answer implements Cloneable {
    private String text;
    private UUID id;

    /**
     * Constructor to make an Answer using the provided text.
     * @param text The text for this answer's label
     */
    public Answer(String text) {
        this.text = text;
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
}
