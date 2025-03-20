package team.logica_populi.dragonscore.logic;

/**
 * The Term Interface.
 * Anything that is a Term should implement this interface.
 *
 */

public interface Term {
    // Terms that describe or put in a category
    // Terms that pick out a specific person or thing

    /**
     * Gets the type of this term.
     * @return The term Type
     */
    TermType getTermType();

    /**
     * Gets the word that this term represents.
     * @return The word for this term
     */
    String getWord();

}
