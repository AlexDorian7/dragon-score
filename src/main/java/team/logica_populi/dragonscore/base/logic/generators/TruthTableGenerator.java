package team.logica_populi.dragonscore.base.logic.generators;

import team.logica_populi.dragonscore.base.logic.*;

import java.util.HashMap;

/**
 * THe true false question generator will combine logical operations together and then ask if it is true or false
 */
public class TruthTableGenerator implements QuestionGenerator {
    @Override
    public Question getNextQuestion() {
        BooleanLogicTreeNode node = BooleanLogicTreeNode.getRandomTree(2);
        String questionString = "<p>Please fill out the truth table for: </p><pre>" + node + "</pre>";
        BaseQuestion question = new BaseQuestion(questionString, new Answer("THIS SHOULD NOT BE SEEN", false));
        question.setExtra(node);
        return question;
    }

}
