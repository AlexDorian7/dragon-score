package team.logica_populi.dragonscore.base.form;

import team.logica_populi.dragonscore.base.logic.Answer;
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
    private final List<AnswerForm> answerForms;

    /**
     * Create a form using an id and form.
     * @param id The form's id
     * @param form The form's form
     */
    public Form(String id, String form) {
        this.id = id;
        this.form = form;
        this.answerForms = new ArrayList<>();
        this.fields = new HashMap<>();

    }

    /**
     * Create a form using an id, form, and list of fields.
     * @param id The form's id
     * @param form The form's form
     * @param fields The form's fields
     * @param answerForms The form's answer forms
     */
    public Form(String id, String form, Set<FormField> fields, List<AnswerForm> answerForms) {
        this.id = id;
        this.form = form;
        this.answerForms = answerForms;
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

    /**
     * Applies The form fields to the {@link AnswerForm}s and returns it as a list of answers.
     * The form format (for uppercase) is {@code $U{list_name_1}} or {@code $U{list_name_1|list_name_2}} or more.
     * The form format (for lowercase) is {@code $L{list_name_1}} or {@code $L{list_name_1|list_name_2}} or more.
     * Using something other than {@code U} or {@code L} will result in the case being unchanged.
     * Note using the or (|) might result in a different word being picked than what the question picked.
     * @return The filled answers.
     */
    public List<Answer> getAnswers() {

        ArrayList<Answer> answers = new ArrayList<>();
        for (AnswerForm answerForm : answerForms) {
            Pattern pattern = Pattern.compile("\\$[UL]\\{[A-Za-z0-9_|]+}");
            Matcher matcher = pattern.matcher(answerForm.getForm());

            StringBuilder result = new StringBuilder();
            while (matcher.find()) {
                String matched = matcher.group();
                char cmd = matched.charAt(1);
                String names = matched.substring(3, matched.length()-1); // remove $X{ and }
                String[] split = names.split("\\|");
                ArrayList<Term> possibleTerms = new ArrayList<>();
                for (String name : split) {
                    if (fields.containsKey(name)) {
                        possibleTerms.add(fields.get(name).getWord());
                    }
                }
                char first = possibleTerms.get((int) (Math.random() * possibleTerms.size())).getWord().charAt(0);
                if (cmd == 'U') {
                    first = Character.toUpperCase(first);
                } else if (cmd == 'L') {
                    first = Character.toLowerCase(first);
                }
                if (!possibleTerms.isEmpty()) {
                    matcher.appendReplacement(result, String.valueOf(first));
                } else {
                    matcher.appendReplacement(result, "ERROR");
                }
            }
            matcher.appendTail(result);
            Answer answer = new Answer(result.toString(), answerForm.isCorrect());
            answers.add(answer);
        }
        return answers;
    }

    /**
     * Get the answer forms for this form.
     * @return The answer forms
     */
    public List<AnswerForm> getAnswerForms() {
        return answerForms;
    }
}
