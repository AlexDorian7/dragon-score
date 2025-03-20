package team.logica_populi.dragonscore.logic.generators;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionGeneratorRegistry {

    private static final Logger logger = Logger.getLogger(QuestionGeneratorRegistry.class.getName());

    private static final QuestionGeneratorRegistry instance = new QuestionGeneratorRegistry();

    private final HashMap<String, Class<? extends QuestionGenerator>> cache = new HashMap<>();



    /**
     * Default constructor for the singleton {@code QuestionGeneratorInstance}
     */
    private QuestionGeneratorRegistry() {

    }

    /**
     * Gets a question generator from its id.
     * @param id The id of the question generator to get
     * @return The {@code QuestionGenerator} instance or null if something failed.
     */
    @Nullable
    public QuestionGenerator getQuestionGenerator(String id) {
        Class<?> generatorClass;
        if (cache.containsKey(id)) {
            generatorClass = cache.get(id);
        } else {
            try {
                generatorClass = getClass().getClassLoader().loadClass(id);
            } catch (ClassNotFoundException e) {
                logger.log(Level.WARNING, "Failed to find class", e);
                return null;
            }
            if (!QuestionGenerator.class.isAssignableFrom(generatorClass)) return null; // Check if the loaded class implements QuestionGenerator
            //noinspection unchecked
            cache.put(id, (Class<? extends QuestionGenerator>) generatorClass);
        }
        try {
            QuestionGenerator questionGenerator = (QuestionGenerator) generatorClass.getDeclaredConstructor().newInstance();
            if (!questionGenerator.getId().equals(id)) logger.info("Loaded generator does not match provided id of " + id); // Id does not match what was passed into this method. Maybe this generator overloaded the getId method.
            return questionGenerator;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.log(Level.WARNING, "Something failed trying to create instance of generator class.", e);
            return null;
        }
    }

    /**
     * Gets the singleton instance for the {@code QuestionGeneratorRegistry} class.
     * @return The singleton instance
     */
    public static QuestionGeneratorRegistry getInstance() {
        return instance;
    }
}
