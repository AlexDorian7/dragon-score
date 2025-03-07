package team.logica_populi.dragonscore.logic.generators;

import team.logica_populi.dragonscore.logic.Question;

/**
 * Every question generator should implement this interface.
 * This is the basic interface for a question generator
 */
public interface QuestionGenerator {

    /**
     * Generate a new question.
     * @return The new question
     */
    Question getNextQuestion();
}
