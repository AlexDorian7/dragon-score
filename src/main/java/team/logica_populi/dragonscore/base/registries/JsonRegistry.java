package team.logica_populi.dragonscore.base.registries;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import team.logica_populi.dragonscore.base.DataFile;
import team.logica_populi.dragonscore.base.Lesson;
import team.logica_populi.dragonscore.base.form.Form;
import team.logica_populi.dragonscore.base.form.FormField;
import team.logica_populi.dragonscore.base.json.AnswerDeserializer;
import team.logica_populi.dragonscore.base.json.AnswerSerializer;
import team.logica_populi.dragonscore.base.json.QuestionGeneratorDeserializer;
import team.logica_populi.dragonscore.base.json.QuestionGeneratorSerializer;
import team.logica_populi.dragonscore.base.logic.Answer;
import team.logica_populi.dragonscore.base.logic.generators.ExampleQuestionGenerator;
import team.logica_populi.dragonscore.base.logic.generators.QuestionGenerator;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

/**
 * A singleton to handle Json for Dragon Score.
 */
public class JsonRegistry {

    private static final Logger logger = Logger.getLogger(JsonRegistry.class.getName());

    private static final JsonRegistry instance = new JsonRegistry();

    private final Gson gson;

    private final GsonBuilder builder;

    /**
     * Constructor to set up Gson and register helpers for it.
     */
    private JsonRegistry() {
        builder = new GsonBuilder();
        builder.registerTypeAdapter(Answer.class, new AnswerSerializer());
        builder.registerTypeAdapter(Answer.class, new AnswerDeserializer());
        builder.registerTypeAdapter(QuestionGenerator.class, new QuestionGeneratorSerializer());
        builder.registerTypeAdapter(QuestionGenerator.class, new QuestionGeneratorDeserializer());

        gson = builder.create();

        // TEST CODE BELOW
        logger.info(gson.toJson(new ExampleQuestionGenerator().getNextQuestion()));
        ArrayList<QuestionGenerator> list = new ArrayList<>();
        list.add(new ExampleQuestionGenerator());
        logger.info(gson.toJson(new Lesson("test", "Test", "Hello World", new ArrayList<>(), list)));
        FormField field1 = new FormField("n1", "nl");
        HashSet<FormField> fields = new HashSet<>();
        fields.add(field1);
        Form form = new Form("form1", "${n1}", fields);
        logger.info(gson.toJson(form));

        String json = null;
        try {
            json = new String(getClass().getResourceAsStream("/assets/db.example.json").readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info(json);
        DataFile dataFile = gson.fromJson(json, DataFile.class);
        logger.info(gson.toJson(dataFile));
    }

    /**
     * Gets the singleton instance of this class.
     * @return The singleton instance
     */
    public static JsonRegistry getInstance() {
         return instance;
    }

    /**
     * Gets the global Gson Builder.
     * @return The Gson Builder
     */
    public GsonBuilder getBuilder() {
        return builder;
    }

    /**
     * Gets the global Gson instance.
     * @return The Gson instance.
     */
    public Gson getGson() {
        return gson;
    }
}
