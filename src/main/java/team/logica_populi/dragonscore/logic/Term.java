package team.logica_populi.dragonscore.logic;

import java.util.List;
import team.logica_populi.dragonscore.logic.TermType;

/**
 * The Term Interface.
 * Anything that is a Term should implement this interface.
 *
 */

public interface Term {
    // Terms that describe or put in a category
    // Terms that pick out a specific person or thing

    TermType getTermType();

    String getWord();

    List<String> getWords();

}
