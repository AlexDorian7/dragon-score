package team.logica_populi.dragonscore.logic.generators;

import team.logica_populi.dragonscore.logic.Question;

/**
 * Every question generator should implement this interface.
 * This is the basic interface for a question generator
 */
public interface QuestionGenerator {

    /**
     * Gets the id of the given question generator instance.
     * @return The question generator id
     */
    default String getId() {
        return getClass().getName();
    }

    /**
     * Generate a new question.
     * @return The new question
     */
    Question getNextQuestion();
}
