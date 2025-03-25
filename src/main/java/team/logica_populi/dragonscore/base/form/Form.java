package team.logica_populi.dragonscore.base.form;

import java.util.*;
import java.util.logging.Logger;

/**
 * Represents a form where words can be filled from lists of terms.
 */
public class Form {
    private static final Logger logger = Logger.getLogger(Form.class.getName());

    private final String id;
    private final String form;
    private final Set<FormField> fields;

    /**
     * Create a form using an id and form.
     * @param id The form's id
     * @param form The form's form
     */
    public Form(String id, String form) {
        this(id, form, new HashSet<>());
    }

    /**
     * Create a form using an id, form, and list of fields.
     * @param id The form's id
     * @param form The form's form
     * @param fields The form's fields
     */
    public Form(String id, String form, Set<FormField> fields) {
        this.id = id;
        this.form = form;
        this.fields = fields;
    }

    /**
     * Gets the id of this form.
     * @return The form's id
     */
    public String getId() {
        return id;
    }

    /**
     * Get the form of this form.
     * @return The form's form
     */
    public String getForm() {
        return form;
    }

    /**
     * Gets the list for form fields for this form.
     * @return The form's fields
     */
    public Set<FormField> getFields() {
        return fields;
    }

    /**
     * Picks a word for each form field in this form.
     */
    public void setFields() {
        for (FormField field : fields) {
            field.pickWord();
        }
    }

    /**
     * Applies The form fields to the form and returns it as a string.
     * @return The filled form.
     */
    @Override
    public String toString() {
        String form1 = form;
        for (FormField field : fields) {
            if (field.getWord() == null) continue;
            form1 = form1.replaceAll("\\$\\{" + field.getId() + "\\}", field.getWord().getWord());
        }
        return form1;
    }
}
