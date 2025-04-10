package team.logica_populi.dragonscore.base.form;

import org.jetbrains.annotations.Nullable;
import team.logica_populi.dragonscore.base.registries.TermRegistry;
import team.logica_populi.dragonscore.base.term.Term;

import java.util.logging.Logger;

/**
 * A form Field is a placeholder for a word inside a form.
 * It can be filled with a random word from a loaded term list.
 */
public class FormField {
    private static final Logger logger = Logger.getLogger(FormField.class.getName());

    private final String id;
    private final String list;

    private Term word;

    /**
     * Create a form field using an id and term list name.
     * @param id The id of this form field.
     * @param list The list name
     */
    public FormField(String id, String list) {
        this.id = id;
        this.list = list;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FormField)) return false;
        return this.id.equals(((FormField) obj).id);
    }

    /**
     * Gets the id of this form field.
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the term list name of this form field
     * @return The list name
     */
    public String getList() {
        return list;
    }

    /**
     * Gets the chosen word for this form field or null if no word has been chosen yet.
     * @return The chosen word.
     */
    @Nullable
    public Term getWord() {
        return word;
    }

    /**
     * Pick and set a new word for this form field.
     * The word will only be set if the term list is registered.
     */
    public void pickWord() {
        word = TermRegistry.getInstance().getRandomTerm(list);
    }
}
