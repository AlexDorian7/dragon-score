package team.logica_populi.dragonscore.base.logic.generators;

import team.logica_populi.dragonscore.base.DataFile;
import team.logica_populi.dragonscore.base.form.Form;
import team.logica_populi.dragonscore.base.logic.Answer;
import team.logica_populi.dragonscore.base.logic.BaseQuestion;
import team.logica_populi.dragonscore.base.logic.Question;
import team.logica_populi.dragonscore.base.registries.JsonRegistry;

import java.util.List;
import java.util.Objects;

/**
 * A {@link QuestionGenerator} for the Syllogistic Translations lesson.
 * @see Form
 */
public class FormGenerator implements QuestionGenerator {

    @Override
    public Question getNextQuestion() {
        DataFile dataFile = JsonRegistry.getInstance().getDataFile();
        Objects.requireNonNull(dataFile); // assert we have a loaded Data file
        if (dataFile.getForms().isEmpty()) {
            throw new IllegalStateException("Tried to use a form generator without any loaded forms!");
        }
        Form form = dataFile.getForms().get((int) (Math.random() * dataFile.getForms().size()));
        form.setFields();
        List<Answer> answers = form.getAnswers();
        return new BaseQuestion(form.toString(), answers);
    }
}
