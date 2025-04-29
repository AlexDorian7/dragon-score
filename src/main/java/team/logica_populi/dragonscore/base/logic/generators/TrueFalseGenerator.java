package team.logica_populi.dragonscore.base.logic.generators;

import team.logica_populi.dragonscore.base.logic.BooleanLogicTreeNode;
import team.logica_populi.dragonscore.base.logic.Question;
import team.logica_populi.dragonscore.base.logic.TrueFalseQuestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * THe true false question generator will combine logical operations together and then ask if it is true or false
 */
public class TrueFalseGenerator implements QuestionGenerator {
    @Override
    public Question getNextQuestion() {
        BooleanLogicTreeNode node = BooleanLogicTreeNode.getRandomTree();
        StringBuilder question = new StringBuilder("<p>If ");
        for (Character character : node.getCache().keySet()) {
            question.append(character).append("=").append(node.getCache().get(character)).append(", ");
        }
        String questionString = question.toString();
        questionString = questionString.substring(0, questionString.length()-2);
        questionString += "</p><pre>" + node + "</pre>";
        return new TrueFalseQuestion(questionString, node.getValue());
    }
}
