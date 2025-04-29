package team.logica_populi.dragonscore.base.logic.generators;

import team.logica_populi.dragonscore.base.logic.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * THe true false question generator will combine logical operations together and then ask if it is true or false
 */
public class UnknownTrueFalseGenerator implements QuestionGenerator {
    @Override
    public Question getNextQuestion() {
        BooleanLogicTreeNode node = BooleanLogicTreeNode.getRandomTree();

        Character toFlip = (Character) node.getCache().keySet().toArray()[(int) Math.floor(Math.random() * node.getCache().size())];

        StringBuilder question = new StringBuilder("<p>If ");
        for (Character character : node.getCache().keySet()) {
            question.append(character).append("=").append(character.equals(toFlip) ? "?" : node.getCache().get(character)).append(", ");
        }
        String questionString = question.toString();
        questionString = questionString.substring(0, questionString.length()-2);
        questionString += "</p><pre>" + node + "</pre>";
        boolean value = node.getValue();
        boolean unknown = !(value == node.getValue(toFlip));
        return new BaseQuestion(questionString, new Answer("True", !unknown && value), new Answer("False", !unknown && !value), new Answer("Unknown", unknown));
    }
}
