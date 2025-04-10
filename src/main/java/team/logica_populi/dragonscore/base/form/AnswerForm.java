package team.logica_populi.dragonscore.base.form;

/**
 * Storage class used to relate a form to its correctness
 */
public class AnswerForm {
    private final String form;
    private final boolean correct;

    /**
     * Create a new answer form using the form string and a boolean for its correctness.
     * @param form The form string
     * @param correct The correctness
     */
    public AnswerForm(String form, boolean correct) {
        this.form = form;
        this.correct = correct;
    }

    /**
     * Gets the form string for this answer form.
     * @return The form string
     */
    public String getForm() {
        return form;
    }

    /**
     * Is this answer form the correct answer form?
     * @return Correctness
     */
    public boolean isCorrect() {
        return correct;
    }
}
