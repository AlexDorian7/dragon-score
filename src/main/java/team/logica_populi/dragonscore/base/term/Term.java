package team.logica_populi.dragonscore.base.term;

import java.util.logging.Logger;

/**
 * Represents a word and what type it is.
 */
public class Term {

    private static final Logger logger = Logger.getLogger(Term.class.getName());

    private final String word;
    private final TermType type;

    /**
     * Create a term using a word and type.
     * @param word The word this term represents
     * @param type The type of this term
     */
    public Term(String word, TermType type) {
        this.word = word;
        this.type = type;
    }

    /**
     * Gets the type of this term.
     *
     * @return The term Type
     */
    public TermType getTermType() {
        return type;
    }

    /**
     * Gets the word that this term represents.
     *
     * @return The word for this term
     */
    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        return "\"" + word + "\": " + type.toString();
    }
}
