package team.logica_populi.dragonscore.base.logic.generators;

import javafx.util.Pair;
import team.logica_populi.dragonscore.base.logic.Question;
import team.logica_populi.dragonscore.base.logic.TrueFalseQuestion;
import team.logica_populi.dragonscore.base.logic.YesNoQuestion;
import team.logica_populi.dragonscore.base.registries.JsonRegistry;
import team.logica_populi.dragonscore.base.term.Term;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StarLogicWordGenerator implements QuestionGenerator {
    @Override
    public Question getNextQuestion() {
        assert JsonRegistry.getInstance().getDataFile() != null;
        List<Term> words = JsonRegistry.getInstance().getDataFile().getTerms().get("nouns");
        List<Term> singleWords = JsonRegistry.getInstance().getDataFile().getTerms().get("pro_nouns");

        int i = (int) Math.floor(Math.random() * words.size());
        int i1 = (int) Math.floor(Math.random() * words.size());
        int i2 = (int) Math.floor(Math.random() * words.size());
        int i3 = (int) Math.floor(Math.random() * singleWords.size());

        while (i == i1 || i1 == i2 || i2 == i) { // We not have this in a loop to make sure none of them end up the same
            if (i1 == i) {
                i1 = (i1+1)%words.size();
            }
            if (i2 == i1) {
                i2 = (i2+1)%words.size();
            }
            if (i2 == i) {
                i2 = (i2+2)%words.size();
            }
        }



        if (Math.random() < 0.5) { // This question will use all / none
            Term word1 = words.get(i); // Get the three words
            Term word2 = words.get(i1);
            Term word3 = words.get(i2);

            StarType type1 = StarType.values()[(int) Math.floor(Math.random() * 4)];
            StarType type2 = StarType.values()[(int) Math.floor(Math.random() * 4)];
            StarType type3 = StarType.values()[(int) Math.floor(Math.random() * 4)];

            int l1 = 0, l2 = 0, l3 = 0, right = 0;
            Pair<Boolean, Boolean> starred1 = isStarred(type1, false);
            Pair<Boolean, Boolean> starred2 = isStarred(type2, false);
            Pair<Boolean, Boolean> starred3 = isStarred(type3, true);

            if (Math.random() < 0.5) { // 1 is 2   2 is 3   1 is 3
                if (starred1.getKey()) l1++;
                if (starred1.getValue()) {
                    l2++;
                    right++;
                }
                if (starred2.getKey()) l2++;
                if (starred2.getValue()) {
                    l3++;
                    right++;
                }
                if (starred3.getKey()) l1++;
                if (starred3.getValue()) {
                    l3++;
                    right++;
                }
                boolean correct = l1 == 1 && l2 == 1 && l3 == 1 && right == 1;
                String questStr = "<p>Is this valid?</p><pre style='tab-size:4;'>\n\t" + makeForm(type1, word1, word2) + "\n\t"
                        + makeForm(type2, word2, word3) + "\n\u2234\t"
                        + makeForm(type3, word1, word3) + "</pre>";
                return new YesNoQuestion(questStr, correct);
            } else { // 1 is 2   1 is 3   2 is 3
                if (starred1.getKey()) l1++;
                if (starred1.getValue()) {
                    l2++;
                    right++;
                }
                if (starred2.getKey()) l1++;
                if (starred2.getValue()) {
                    l3++;
                    right++;
                }
                if (starred3.getKey()) l2++;
                if (starred3.getValue()) {
                    l3++;
                    right++;
                }
                boolean correct = l1 == 1 && l2 == 1 && l3 == 1 && right == 1;
                String questStr = "<p>Is this valid?</p><pre style='tab-size:4;'>\n\t" + makeForm(type1, word1, word2) + "\n\t"
                        + makeForm(type2, word1, word3) + "\n\u2234\t"
                        + makeForm(type3, word2, word3) + "</pre>";
                return new YesNoQuestion(questStr, correct);
            }

        } else { // this question will use is / is not
            Term word1 = words.get(i); // Get the three words
            Term word2 = words.get(i1);
            Term word3 = words.get(i2);

            Term word4 = singleWords.get(i3);

            StarType type1 = StarType.values()[(int) Math.floor(Math.random() * 4)];
            StarType type2 = StarType.values()[(int) Math.floor(Math.random() * 4)];
            StarType type4 = StarType.values()[(int) Math.floor(Math.random() * 2)+4];
            StarType type5 = StarType.values()[(int) Math.floor(Math.random() * 2)+4];

            int l1 = 0, l2 = 0, l3 = 0, right = 0;
            Pair<Boolean, Boolean> starred1 = isStarred(type4, false);
            Pair<Boolean, Boolean> starred2 = isStarred(type1, false);
            Pair<Boolean, Boolean> starred3 = isStarred(type2, false);
            Pair<Boolean, Boolean> starred4 = isStarred(type5, true);

            // 4 is 1   1 is 2   2 is 3   4 is 3
            if (starred1.getValue()) {
                l1++;
                right++;
            }
            if (starred2.getKey()) {
                l1++;
            }
            if (starred2.getValue()) {
                l2++;
                right++;
            }
            if (starred3.getKey()) {
                l2++;
            }
            if (starred3.getValue()) {
                l3++;
                right++;
            }
            if (starred4.getValue()) {
                l3++;
                right++;
            }
            boolean correct = l1 == 1 && l2 == 1 && l3 == 1 && right == 1;
            String questStr = "<p>Is this valid?</p><pre style='tab-size:4;'>" + "\n\t" + makeForm(type4, word4, word1) + "\n\t"
                    + makeForm(type1, word1, word2) + "\n\t"
                    + makeForm(type2, word2, word3) + "\n\u2234\t"
                    + makeForm(type5, word4, word3) + "</pre>";
            return new YesNoQuestion(questStr, correct);
        }
    }

    /**
     * Preforms StarTest Logic and returns if things are starred.
     * @param type The type of star test
     * @param conclusion Is this the conclusion line
     * @return Is the first letter starred, is the second letter starred
     */
    private Pair<Boolean, Boolean> isStarred(StarType type, boolean conclusion) {
        switch (type) {
            case ALL -> {
                if (conclusion) return new Pair<>(false, true);
                return new Pair<>(true, false);
            }
            case NONE -> {
                if (conclusion) return new Pair<>(false, false);
                return new Pair<>(true, true);
            }
            case SOME, IS -> {
                if (conclusion) return new Pair<>(true, true);
                return new Pair<>(false, false);
            }
            case NOT_SOME, IS_NOT -> {
                if (conclusion) return new Pair<>(true, false);
                return new Pair<>(false, true);
            }
        }
        return null; // Should not be possible to get here.
    }

    /**
     * Turns the star type into a string
     * @param type The type
     * @param term1 The first term
     * @param term2 The second term
     * @return The human string
     */
    private String makeForm(StarType type, Term term1, Term term2) {
        switch (type) {
            case ALL -> {
                return "All " + term1.getWord() + "s are " + term2.getWord() + "s";
            }
            case NONE -> {
                return "No " + term1.getWord() + "s are " + term2.getWord() + "s";
            }
            case SOME -> {
                return "Some " + term1.getWord() + "s are " + term2.getWord() + "s";
            }
            case NOT_SOME -> {
                return "Some " + term1.getWord() + "s are not " + term2.getWord() + "s";
            }
            case IS -> {
                return term1.getWord() + " is a " + term2.getWord();
            }
            case IS_NOT -> {
                return term1.getWord() + " is not a " + term2.getWord();
            }
        }
        return "THIS SHOULD NOT BE POSSIBLE!!!";
    }

    /**
     * Star Type Enum for managing types used in star test
     */
    private enum StarType {
        ALL,
        NONE,
        SOME,
        NOT_SOME,
        IS,
        IS_NOT
    }
}
