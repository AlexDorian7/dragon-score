package team.logica_populi.dragonscore.base;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.logging.Logger;

/**
 * Represents a word and what type it is.
 */

public class Term {

    private static final Logger logger = Logger.getLogger(Term.class.getName());

    private final String word;
    private final TermType type;

    public Term(String word, TermType type) {
        this.word = word;
        this.type = type;
    }

    /**
     * Gets the type of this term.
     *
     * @return The term Type
     */
    TermType getTermType() {
        return type;
    }

    /**
     * Gets the word that this term represents.
     *
     * @return The word for this term
     */
    String getWord() {
        return word;
    }

    /**
     * Loads a term from the provided JSON object.
     * @param object The JSON object to load from
     * @return The loaded term or null on failure
     */
    @Nullable
    public static Term loadFromJSON(JSONObject object) {
        String word = object.getString("word");
        if (word == null || word.isEmpty()) {
            logger.warning("Failed to load term. Word was empty or null!");
            return null;
        }
        String typeS = object.getString("type");
        if (typeS == null || typeS.isEmpty()) {
            logger.warning("Failed to load term. Type was null or empty!");
            return null;
        }
        TermType type;
        try {
            type = TermType.valueOf(typeS);
        } catch (IllegalArgumentException e) {
            logger.warning("Failed to load term. " + typeS + " is not a valid term type!");
            return null;
        }
        return new Term(word, type);
    }

    @Override
    public String toString() {
        return "\"" + word + "\": " + type.toString();
    }
}
