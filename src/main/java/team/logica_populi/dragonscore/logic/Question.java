package team.logica_populi.dragonscore.logic;

import java.util.List;

/**
 * The Question Interface.
 * Anything that is a question in this program should implement this interface.
 * @see AbstractQuestion A basic implimentation
 */
public interface Question {

    /**
     * Gets the question text.
     * @return The text of the question
     */
    String getQuestion();

    /**
     * Gets a reference to the correct answer for this question.
     * @return The correct answer
     */
    Answer getCorrectAnswer();

    /**
     * Gets a List of all answers for this question.
     *
     * @return The answers for this question
     */
    List<Answer> getAnswers();
}
