package team.logica_populi.dragonscore.base.logic;

import java.util.logging.Logger;

/**
 * A non-abstract version of {@code AbstractQuestion}.
 * @see AbstractQuestion The implimentation of Question
 */
public class BaseQuestion extends AbstractQuestion {

    private static final Logger logger = Logger.getLogger(BaseQuestion.class.getName());

    /**
     * Constructor to make a question given the text, correct answer index, and the answer choices.
     *
     * @param question     The text for the question label
     * @param answers      The answer choices for this question
     */
    public BaseQuestion(String question, Answer... answers) {
        super(question, answers);
    }

//    /**
//     * Loads a question from a JSON object.
//     * @param object The object to load from
//     * @return The loaded question or null if the question could not be loaded
//     */
//    @Nullable
//    public static BaseQuestion loadFromJSON(JSONObject object) {
//        String questionString = object.getString("question");
//        if (questionString == null || questionString.isEmpty()) {
//            logger.warning("Failed to load question. Question text empty or null");
//            return null;
//        }
//        BaseQuestion question = new BaseQuestion(questionString);
//        JSONArray answers = object.getJSONArray("answers");
//        if (answers == null || answers.isEmpty()) {
//            logger.warning("Failed to load question. Answers list null or empty");
//            return null;
//        }
//        for (Object answerObject : answers) {
//            if (!(answerObject instanceof JSONObject)) continue;
//            Answer answer = Answer.loadFromJSON((JSONObject) answerObject);
//            if (answer == null) {
//                logger.warning("Failed to load question. An answer was null.");
//                return null;
//            }
//            question.getAnswers().add(answer);
//        }
//        if (question.getAnswers().isEmpty()) {
//            logger.info("Loaded question has no answers.");
//        }
//        return question;
//    }
}
