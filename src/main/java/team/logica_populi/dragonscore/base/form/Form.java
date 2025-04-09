package team.logica_populi.dragonscore.base.form;

import team.logica_populi.dragonscore.base.term.Term;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a form where words can be filled from lists of terms.
 */
public class Form {
    private static final Logger logger = Logger.getLogger(Form.class.getName());

    private final String id;
    private final String form;
    private final HashMap<String, FormField> fields;

    /**
     * Create a form using an id and form.
     * @param id The form's id
     * @param form The form's form
     */
    public Form(String id, String form) {
        this.id = id;
        this.form = form;
        this.fields = new HashMap<>();
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
        this.fields = new HashMap<>();
        for (FormField field : fields) {
            this.fields.put(field.getId(), field);
        }
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
     * Gets the map for form fields for this form.
     * @return The form's fields
     */
    public HashMap<String, FormField> getFields() {
        return fields;
    }

    /**
     * Picks a word for each form field in this form.
     */
    public void setFields() {
        fields.forEach((String id, FormField field) -> {
            field.pickWord();
        });
    }

    /**
     * Applies The form fields to the form and returns it as a string.
     * The form format is {@code ${list_name_1}} or {@code ${list_name_1|list_name_2}} or more
     * @return The filled form.
     */
    @Override
    public String toString() {
        Pattern pattern = Pattern.compile("\\$\\{[A-Za-z0-9_|]+}");
        Matcher matcher = pattern.matcher(form);

        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            String matched = matcher.group();
            String names = matched.substring(2, matched.length()-1); // remove ${ and }
            String[] split = names.split("\\|");
            ArrayList<Term> possibleTerms = new ArrayList<>();
            for (String name : split) {
                if (fields.containsKey(name)) {
                    possibleTerms.add(fields.get(name).getWord());
                }
            }
            if (!possibleTerms.isEmpty()) {
                matcher.appendReplacement(result, possibleTerms.get((int) (Math.random() * possibleTerms.size())).getWord());
            } else {
                matcher.appendReplacement(result, "ERROR");
            }
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
