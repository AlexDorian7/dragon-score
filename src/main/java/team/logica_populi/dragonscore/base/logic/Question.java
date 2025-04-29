package team.logica_populi.dragonscore.base.logic;

import org.jetbrains.annotations.Nullable;

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
     * Gets a List of all answers for this question.
     *
     * @return The answers for this question
     */
    List<Answer> getAnswers();

    /**
     * Gets any extra data that this question may have.
     * @return The extra data
     */
    @Nullable
    Object getExtra();
}
