package team.logica_populi.dragonscore.base.logic.generators;

import team.logica_populi.dragonscore.base.logic.Answer;
import team.logica_populi.dragonscore.base.logic.BaseQuestion;
import team.logica_populi.dragonscore.base.logic.Question;
import team.logica_populi.dragonscore.base.logic.TrueFalseQuestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * THe true false question generator will combine logical operations together and then ask if it is true or false
 */
public class UnknownTrueFalseGenerator implements QuestionGenerator {
    private char nextChar = 'A';
    private HashMap<Character, Boolean> cache = new HashMap<>();
    @Override
    public Question getNextQuestion() {
        nextChar = 'A';
        cache.clear();
        BooleanLogicTreeNode node = getRandomNode(false, 0);

        Character toFlip = (Character) cache.keySet().toArray()[(int) Math.floor(Math.random() * cache.size())];

        StringBuilder question = new StringBuilder("<p>If ");
        for (Character character : cache.keySet()) {
            question.append(character).append("=").append(character.equals(toFlip) ? "?" : cache.get(character)).append(", ");
        }
        String questionString = question.toString();
        questionString = questionString.substring(0, questionString.length()-2);
        questionString += "</p><pre>" + node + "</pre>";
        boolean value = node.getValue();
        boolean unknown = !(value == node.getValue(toFlip));
        return new BaseQuestion(questionString, new Answer("True", !unknown && value), new Answer("False", !unknown && !value), new Answer("Unknown", unknown));
    }

    private static void check(int argc, List<Boolean> inputs) {
        if (inputs.size() != argc) throw new IllegalStateException("Expected " + argc + " input booleans, but got " + inputs.size() + ".");
    }

    private static boolean conditional(boolean a, boolean b) {
        if (!a) return true; // F F -> T, F T -> T
        return b; // T F -> F, T T -> T
    }

    private BooleanLogicTreeNode getRandomNode(boolean allowConst, int depth) {
        if (depth >= 3 || allowConst && Math.random() > 0.5) { // Pick Const
            boolean val = Math.random() > 0.5;
            BooleanLogicTreeNode node = new BooleanLogicTreeNode(Operators.CONSTANT, new ArrayList<>(), val, nextChar);
            cache.put(nextChar, val);
            nextChar++;
            return node;
        } else {
            Operators type = Operators.values()[(int) Math.floor(Math.random() * (Operators.values().length-1))]; // Does not include last index as that should be const
            List<BooleanLogicTreeNode> nodes = new ArrayList<>(type.argc);
            for (int i=0; i<type.argc; i++) {
                nodes.add(getRandomNode(true, depth+1));
            }
            return new BooleanLogicTreeNode(type, nodes);
        }
    }

    /**
     * Class to store the boolean logic tree
     */
    private class BooleanLogicTreeNode {
        public final Operators operator;
        public final List<BooleanLogicTreeNode> inputs;
        public final boolean constantValue;
        public final char constantChar;

        BooleanLogicTreeNode(Operators operator, List<BooleanLogicTreeNode> inputs) {
            this(operator, inputs, false, '0');
        }

        BooleanLogicTreeNode(Operators operator, List<BooleanLogicTreeNode> inputs, boolean constantValue, char constantChar) {
            this.operator = operator;
            this.inputs = inputs;
            this.constantValue = constantValue;
            this.constantChar = constantChar;
        }

        boolean getValue() {
            if (operator == Operators.CONSTANT) return constantValue;
            List<Boolean> list = new ArrayList<>(inputs.size());
            for (int i=0; i<inputs.size(); i++) {
                BooleanLogicTreeNode input = inputs.get(i);
                list.add(input.getValue());
            }
            check(operator.argc, list);
            return operator.transformer.transform(list);
        }

        boolean getValue(char toFlip) {
            if (operator == Operators.CONSTANT) return (constantChar == toFlip) != constantValue;
            List<Boolean> list = new ArrayList<>(inputs.size());
            for (int i=0; i<inputs.size(); i++) {
                BooleanLogicTreeNode input = inputs.get(i);
                list.add(input.getValue(toFlip));
            }
            check(operator.argc, list);
            return operator.transformer.transform(list);
        }

        @Override
        public String toString() {
            switch (operator) { // TODO: Make these use proper symbols
                case OR -> {
                    return "(" + inputs.getFirst() + " || " + inputs.getLast() + ")";
                }
                case AND -> {
                    return "(" + inputs.getFirst() + " && " + inputs.getLast() + ")";
                }
                case NOT -> {
                    return "~" + inputs.getFirst();
                }
                case EQUALS -> {
                    return "(" + inputs.getFirst() + " == " + inputs.getLast() + ")";
                }
                case CONDITIONAL -> {
                    return "(" + inputs.getFirst() + " -> " + inputs.getLast() + ")";
                }
                case CONSTANT -> {
                    return String.valueOf(constantChar);
                }
            }
            return "";
        }
    }

    /**
     * Enum for boolean operators
     */
    private enum Operators {
        OR(2, (List<Boolean> inputs) -> inputs.getFirst() || inputs.getLast()),
        AND(2, (List<Boolean> inputs) -> inputs.getFirst() && inputs.getLast()),
        NOT(1, (List<Boolean> inputs) -> !inputs.getFirst()),
        EQUALS(2, (List<Boolean> inputs) -> inputs.getFirst().equals(inputs.getLast())),
        CONDITIONAL(2, (List<Boolean> inputs) -> conditional(inputs.getFirst(), inputs.getLast())),
        CONSTANT(1, List::getFirst); // Should always be last element in Enum.

        public final int argc;
        public final LogicalTransformer transformer;

        /**
         * A boolean operator.
         * @param argc The amount of expected input booleans
         * @param transformer The boolean logic transformer
         */
        Operators(int argc, LogicalTransformer transformer) {

            this.argc = argc;
            this.transformer = transformer;
        }


    }

    /**
     * Functional interface for lambda methods of type {@code (List<Boolean>) -> boolean}.
     */
    @FunctionalInterface
    private interface LogicalTransformer {
        boolean transform(List<Boolean> inputs);
    }
}
