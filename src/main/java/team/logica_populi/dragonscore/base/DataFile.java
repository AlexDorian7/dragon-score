package team.logica_populi.dragonscore.base;

import team.logica_populi.dragonscore.base.form.Form;
import team.logica_populi.dragonscore.base.term.Term;

import java.util.HashMap;
import java.util.List;

/**
 * Represents the overall data storage structure in code
 */
public class DataFile {
    private final HashMap<String, List<Term>> terms;
    private final List<Lesson> lessons;
    private final List<Form> forms;

    /**
     * Default constructor.
     * @param terms The term lists for this data file
     * @param lessons The lessons for this data file
     * @param forms the forms for this data file
     * @see team.logica_populi.dragonscore.base.registries.JsonRegistry#loadDataFile(String) 
     */
    private DataFile(HashMap<String, List<Term>> terms, List<Lesson> lessons, List<Form> forms) {
        this.terms = terms;
        this.lessons = lessons;
        this.forms = forms;
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
     * Gets the forms for this data file
     * @return The forms
     */
    public List<Form> getForms() {
        return forms;
    }
}
