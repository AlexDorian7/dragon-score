package team.logica_populi.dragonscore.logic.generators;

import team.logica_populi.dragonscore.logic.Answer;
import team.logica_populi.dragonscore.logic.BaseQuestion;
import team.logica_populi.dragonscore.logic.Question;

/**
 * An example question generator to construct addition problems
 */
public class ExampleQuestionGenerator implements QuestionGenerator {

    /**
     * Create a new instance of the example question generator.
     * More complex generators might need to actually do work in here.
     */
    public ExampleQuestionGenerator() {

    }

    @Override
    public Question getNextQuestion() {
        // Create two random numbers 0-100
        int number0 = (int) (Math.random()*100);
        int number1 = (int) (Math.random()*100);

        // Create three more
        int incorrectAnswer0 = (int) (Math.random()*200);
        int incorrectAnswer1 = (int) (Math.random()*200);
        int incorrectAnswer2 = (int) (Math.random()*200);

        // Define the correct number
        int correctAnswer = number0 + number1;

        // Make the answer choices
        Answer answer0 = new Answer(String.valueOf(incorrectAnswer0));
        Answer answer1 = new Answer(String.valueOf(incorrectAnswer1));
        Answer answer2 = new Answer(String.valueOf(incorrectAnswer2));
        Answer answer3 = new Answer(String.valueOf(correctAnswer));

        // Return the new question
        return new BaseQuestion("What is " + number0 + " + " + number1 + "?", 3, answer0, answer1, answer2, answer3);
    }
}
