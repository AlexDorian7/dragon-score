package team.logica_populi.dragonscore.logic.generators;

import team.logica_populi.dragonscore.base.Term;

import java.util.Random;

public class ExampleTermGenerator implements TermGenerator{

    public ExampleTermGenerator() {

    }

    @Override
    public Term getNextTerm() {
        // randomly get term or terms from the word bank
        String[] nouns = {"water", "dog", "cat", "tree", "house", "car"};

        Random random = new Random();

        int randIndex = random.nextInt(nouns.length);

        String term = nouns[randIndex];

        // return it to be put in the proposition format
        return null;
    }
}
