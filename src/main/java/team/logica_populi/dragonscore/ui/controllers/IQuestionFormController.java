package team.logica_populi.dragonscore.ui.controllers;

import team.logica_populi.dragonscore.base.logic.Answer;
import team.logica_populi.dragonscore.base.logic.Question;

import java.util.List;
import java.util.function.Consumer;

public interface IQuestionFormController {
    /**
     * Sets the question that this view is to display.
     *
     * @param question The question to display
     */
    void setQuestion(Question question);

    /**
     * Sets the progress bar value.
     *
     * @param progress The value to display in the progress bar
     */
    void setProgress(double progress);

    /**
     * Shows the correct answer(s) to the user and prevents any additional answers from being recorded.
     */
    void showCorrect();

    /**
     * Sets the on submitted callback consumer.
     *
     * @param callback The consumer to call with the selected answers
     */
    void setSubmitCallback(Consumer<List<Answer>> callback);

    /**
     * Sets the callback that will be called when the user wants to go to the next question.
     *
     * @param nextQuestionCallback The callback to be executed
     */
    void setNextQuestionCallback(Runnable nextQuestionCallback);
}
