package team.logica_populi.dragonscore.base.logic;

import java.util.*;
import java.util.logging.Logger;

/**
 * Class to store the boolean logic tree
 */
public class BooleanLogicTreeNode {
    private static final Logger logger = Logger.getLogger(BooleanLogicTreeNode.class.getName());

    public final Operators operator;
    public final List<BooleanLogicTreeNode> inputs;
    public final char constantChar;

    private char nextChar = 'A';
    private HashMap<Character, Boolean> cache = new HashMap<>();

    BooleanLogicTreeNode(Operators operator, List<BooleanLogicTreeNode> inputs) {
        this(operator, inputs, '0');
    }

    BooleanLogicTreeNode(Operators operator, List<BooleanLogicTreeNode> inputs, char constantChar) {
        this.operator = operator;
        this.inputs = inputs;
        this.constantChar = constantChar;
    }




    public static HashMap<Character, Boolean> decodeBitmask(int bitmask, List<Character> variables) {
        HashMap<Character, Boolean> result = new HashMap<>();

        for (int i = 0; i < variables.size(); i++) {
            boolean value = (bitmask & (1 << i)) != 0; // Check if the bit at index i is set
            result.put(variables.get(i), value);
        }

        return result;
    }


    /**
     * tests if the provided truth table mapping is truthful.
     * @param table The truth table mapping
     * @param vars The list of constants in the correct order
     * @return is it truthful
     */
    public boolean testTable(Map<Set<Character>, Boolean> table, List<Character> vars) {
        final boolean[] truthful = {true};

        table.forEach((Set<Character> key, Boolean value) -> {
            HashMap<Character, Boolean> newCache = new HashMap<>();

            // Populate newCache based on the set representation
            for (Character var : vars) {
                newCache.put(var, key.contains(var)); // True if the variable is in the set, false otherwise
            }

            cache = newCache;
            boolean v1 = getValue();
            logger.info("Eval: " + v1);
            boolean val = (v1 == value);
            logger.info("New Cache: " + newCache);
            logger.info("Current Cache: " + cache);
            logger.info("Match: " + val);

            truthful[0] = truthful[0] && val;
        });

        return truthful[0];
    }


    /**
     * tests if the provided truth table mapping is truthful and records which rows are not
     * @param table The truth table mapping
     * @param vars The list of constants in the correct order
     * @return the recording
     */
    public Map<Set<Character>, Boolean> gatherResultsTable(Map<Set<Character>, Boolean> table, List<Character> vars) {
        table.forEach((Set<Character> key, Boolean value) -> {
            HashMap<Character, Boolean> newCache = new HashMap<>();

            // Populate newCache based on the set representation
            for (Character var : vars) {
                newCache.put(var, key.contains(var)); // True if the variable is in the set, false otherwise
            }

            cache = newCache;

            boolean val = (getValue() == value);
//            logger.info("New Cache: " + newCache);
//            logger.info("Current Cache: " + cache);
//            logger.info("Match: " + val);

            table.put(key, val);
        });
        return table;
    }

    public boolean getValue() {
        if (operator == Operators.CONSTANT) return cache.get(constantChar);
        List<Boolean> list = new ArrayList<>(inputs.size());
        for (int i=0; i<inputs.size(); i++) {
            BooleanLogicTreeNode input = inputs.get(i);
            input.cache = cache; // propagate cache through tree
            list.add( input.getValue());
        }
        check(operator.argc, list);
        return operator.transformer.transform(list);
    }

    public boolean getValue(char toFlip) {
        if (operator == Operators.CONSTANT) return (constantChar == toFlip) != cache.get(constantChar);
        List<Boolean> list = new ArrayList<>(inputs.size());
        for (int i=0; i<inputs.size(); i++) {
            BooleanLogicTreeNode input = inputs.get(i);
            input.cache = cache; // propagate cache through tree
            list.add(input.getValue(toFlip));
        }
        check(operator.argc, list);
        return operator.transformer.transform(list);
    }

     static void check(int argc, List<Boolean> inputs) {
        if (inputs.size() != argc) throw new IllegalStateException("Expected " + argc + " input booleans, but got " + inputs.size() + ".");
    }

    public static boolean conditional(boolean a, boolean b) {
        if (!a) return true; // F F -> T, F T -> T
        return b; // T F -> F, T T -> T
    }

    /**
     * Gets a random Tree.
     * @return The rot node of the random tree
     */
    public static BooleanLogicTreeNode getRandomTree() {
        return getRandomTree(3);
    }

    /**
     * Gets a random Tree.
     *
     * @param maxDepth The max depth that is allowed
     * @return The rot node of the random tree
     */
    public static BooleanLogicTreeNode getRandomTree(int maxDepth) {
        return getRandomNode(false, 0, 'A', maxDepth);
    }

    /**
     * Gets a random Tree.
     *
     * @param allowConst is const a valid option for this node?
     * @param depth The current recursion depth
     * @param nextChar The next valid character
     * @param maxDepth The max depth that is allowed
     * @return The random node
     */
    private static BooleanLogicTreeNode getRandomNode(boolean allowConst, int depth, char nextChar, int maxDepth) {
        if (depth >= maxDepth || allowConst && Math.random() > 0.5) { // Pick Const
            boolean val = Math.random() > 0.5;
            BooleanLogicTreeNode node = new BooleanLogicTreeNode(Operators.CONSTANT, new ArrayList<>(), nextChar);
            node.getCache().put(nextChar, val);
            node.nextChar = (char) (nextChar+1);
            return node;
        } else {
            Operators type = Operators.values()[(int) Math.floor(Math.random() * (Operators.values().length-1))]; // Does not include last index as that should be const
            List<BooleanLogicTreeNode> nodes = new ArrayList<>(type.argc);
            HashMap<Character, Boolean> c1 = new HashMap<>();
            for (int i=0; i<type.argc; i++) {
                BooleanLogicTreeNode randomNode = getRandomNode(true, depth + 1, nextChar, maxDepth);
                nodes.add(randomNode);
                c1.putAll(randomNode.getCache());
                nextChar = randomNode.nextChar;
            }
            BooleanLogicTreeNode node = new BooleanLogicTreeNode(type, nodes);
            node.getCache().putAll(c1);
            node.nextChar = nextChar;
            return node;
        }
    }

    @Override
    public String toString() {
        switch (operator) {
            case OR -> {
                return "(" + inputs.getFirst() + " \u2228 " + inputs.getLast() + ")";
            }
            case AND -> {
                return "(" + inputs.getFirst() + " \u00B7 " + inputs.getLast() + ")";
            }
            case NOT -> {
                return "~" + inputs.getFirst();
            }
            case EQUALS -> {
                return "(" + inputs.getFirst() + " \u2261 " + inputs.getLast() + ")";
            }
            case CONDITIONAL -> {
                return "(" + inputs.getFirst() + " \u2283 " + inputs.getLast() + ")";
            }
            case CONSTANT -> {
                return String.valueOf(constantChar);
            }
        }
        return "";
    }

    public HashMap<Character, Boolean> getCache() {
        return cache;
    }


    /**
     * Enum for boolean operators
     */
    public enum Operators {
        OR(2, (List<Boolean> inputs) -> inputs.getFirst() || inputs.getLast()),
        AND(2, (List<Boolean> inputs) -> inputs.getFirst() && inputs.getLast()),
        NOT(1, (List<Boolean> inputs) -> !inputs.getFirst()),
        EQUALS(2, (List<Boolean> inputs) -> inputs.getFirst().equals(inputs.getLast())), // Biconditional
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
    public interface LogicalTransformer {
        boolean transform(List<Boolean> inputs);
    }
}


