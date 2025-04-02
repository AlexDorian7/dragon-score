package team.logica_populi.dragonscore.base.logic.generators;

import team.logica_populi.dragonscore.base.DataFile;
import team.logica_populi.dragonscore.base.form.Form;
import team.logica_populi.dragonscore.base.logic.Answer;
import team.logica_populi.dragonscore.base.logic.BaseQuestion;
import team.logica_populi.dragonscore.base.logic.Question;
import team.logica_populi.dragonscore.base.registries.JsonRegistry;

import java.util.Objects;

/**
 * An example {@link QuestionGenerator} to show off the new forms system.
 * @see Form
 */
public class ExampleFormQuestionGenerator implements QuestionGenerator {

    @Override
    public Question getNextQuestion() {
        DataFile dataFile = JsonRegistry.getInstance().getDataFile();
        Objects.requireNonNull(dataFile); // assert we have a loaded Data file
        if (dataFile.getForms().isEmpty()) {
            throw new IllegalStateException("Tried to use a form generator without any loaded forms!");
        }
        Form form = dataFile.getForms().get((int) (Math.random() * dataFile.getForms().size()));
        form.setFields();
        Answer answer1 = new Answer("Correct", true);
        Answer answer2 = new Answer("Incorrect", false);
        Answer answer3 = new Answer("Incorrect", false);
        Answer answer4 = new Answer("Incorrect", false);
        return new BaseQuestion(form.toString(), answer1, answer2, answer3, answer4);
    }
}
