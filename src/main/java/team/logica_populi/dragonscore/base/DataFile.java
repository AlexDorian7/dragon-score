package team.logica_populi.dragonscore.base;

import team.logica_populi.dragonscore.base.form.Form;
import team.logica_populi.dragonscore.base.registries.JsonRegistry;
import team.logica_populi.dragonscore.base.term.Term;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * Represents the overall data storage structure in code
 */
public class DataFile {
    private static final Logger logger = Logger.getLogger(DataFile.class.getName());

    private final HashMap<String, List<Term>> terms;
    private final List<Lesson> lessons;
    private final List<Form> forms;
    private final List<String> requires;

    /**
     * Default constructor.
     * @param terms The term lists for this data file
     * @param lessons The lessons for this data file
     * @param forms the forms for this data file
     * @see team.logica_populi.dragonscore.base.registries.JsonRegistry#loadDataFile(String, boolean)
     */
    private DataFile(HashMap<String, List<Term>> terms, List<Lesson> lessons, List<Form> forms, List<String> requires) {
        this.terms = terms;
        this.lessons = lessons;
        this.forms = forms;
        this.requires = requires;
    }

    /**
     * Gets the term lists for this data file.
     * @return The term lists
     */
    public HashMap<String, List<Term>> getTerms() {
        return terms;
    }

    /**
     * Gets the lessons for this data file.
     * @return The lessons
     */
    public List<Lesson> getLessons() {
        return lessons;
    }

    /**
     * Gets the forms for this data file.
     * @return The forms
     */
    public List<Form> getForms() {
        return forms;
    }

    /**
     * Gets the required data files for this data file.
     * @return The required files
     */
    public List<String> getRequires() {
        return requires;
    }

    /**
     * Merges data from another data file into this one.
     * @param other The data file to merge from
     */
    private void mergeDataFile(DataFile other) {
        other.terms.forEach((String name, List<Term> termList) -> {
            if (!terms.containsKey(name)) {
                terms.put(name, termList);
            } else {
                terms.get(name).addAll(termList);
            }
        });
        lessons.addAll(other.lessons);
        forms.addAll(other.forms);
        loadRequires(other.requires);
    }

    /**
     * Loads and merges the required files of this data file into this data file.
     */
    public void loadRequires() {
        loadRequires(requires);
    }

    /**
     * Loads and merges the required files of this data file into this data file.
     * @param requires The list of path names to load
     */
    private void loadRequires(List<String> requires) {
        logger.finer(requires.toString());
        for (String path : requires) {
            logger.fine("Loading require from" + path);
            InputStream stream = getClass().getResourceAsStream(path);
            if (stream == null) continue;
            mergeDataFile(JsonRegistry.getInstance().loadDataFile(stream, false));
        }
    }
}
