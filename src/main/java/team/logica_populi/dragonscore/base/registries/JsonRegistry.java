package team.logica_populi.dragonscore.base.registries;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.Nullable;
import team.logica_populi.dragonscore.base.DataFile;
import team.logica_populi.dragonscore.base.ResourceLocation;
import team.logica_populi.dragonscore.base.form.Form;
import team.logica_populi.dragonscore.base.json.*;
import team.logica_populi.dragonscore.base.logic.Answer;
import team.logica_populi.dragonscore.base.logic.generators.QuestionGenerator;
import team.logica_populi.dragonscore.base.points.PointSystem;
import team.logica_populi.dragonscore.base.points.SubmissionSystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * A singleton to handle Json for Dragon Score.
 */
public class JsonRegistry {

    private static final Logger logger = Logger.getLogger(JsonRegistry.class.getName());

    private static final JsonRegistry instance = new JsonRegistry();

    private final Gson gson;

    private final GsonBuilder builder;

    private DataFile dataFile;

    private PointSystem pointSystem;

    private SubmissionSystem submissionSystem;

    /**
     * Constructor to set up Gson and register helpers for it.
     */
    private JsonRegistry() {
        builder = new GsonBuilder();
        builder.registerTypeAdapter(Answer.class, new AnswerSerializer());
        builder.registerTypeAdapter(Answer.class, new AnswerDeserializer());
        builder.registerTypeAdapter(QuestionGenerator.class, new QuestionGeneratorSerializer());
        builder.registerTypeAdapter(QuestionGenerator.class, new QuestionGeneratorDeserializer());
        builder.registerTypeAdapter(Form.class, new FormSerializer());
        builder.registerTypeAdapter(Form.class, new FormDeserializer());
        builder.registerTypeAdapter(PointSystem.class, new PointSystemSerializer());
        builder.registerTypeAdapter(PointSystem.class, new PointSystemDeserializer());
        builder.registerTypeAdapter(ResourceLocation.class, new ResourceLocationSerializer());
        builder.registerTypeAdapter(ResourceLocation.class, new ResourceLocationDeserializer());
        builder.registerTypeAdapter(SubmissionSystem.class, new SubmissionCodeSerializer());
        builder.registerTypeAdapter(SubmissionSystem.class, new SubmissionCodeDeserializer());

        gson = builder.create();
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

    /**
     * Loads a data file from the provided input stream.
     * The provided data must be valid data file JSON.
     * @param stream The stream to load from
     * @param set Set the data file as the main data file
     * @return The loaded {@link DataFile}
     */
    public DataFile loadDataFile(InputStream stream, boolean set) {
        try {
            return loadDataFile(new String(stream.readAllBytes()), set);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads a data file from the provided JSON data.
     * @param data The JSON data to load
     * @param set Set the data file as the main data file
     * @return The loaded {@link DataFile}
     */
    public DataFile loadDataFile(String data, boolean set) {
        logger.finest("Attempting to parse:\n" + data);
        DataFile dataFile = gson.fromJson(data, DataFile.class);
        dataFile.loadRequires();
        if (set) {
            this.dataFile = dataFile;
            TermRegistry.getInstance().loadDataFile(dataFile);
        }
        return dataFile;
    }

    /**
     * Loads Lesson records file from input stream.
     * The provided file must be in valid JSON format.
     * @param stream The stream to load from
     * @param set Set the data file as the main data file
     * @return The loaded {@link PointSystem}
     */
    public PointSystem loadPointSystem(InputStream stream, boolean set) {
        try {
            return loadPointSystem(new String(stream.readAllBytes()), set);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the lesson records data from provided JSON data.
     * @param data The JSON data to load.
     * @param set Set the data file as the main data file
     * @return The loaded {@link PointSystem}
     */
    public PointSystem loadPointSystem(String data, boolean set) {
        PointSystem pointSystem = gson.fromJson(data, PointSystem.class);
        if (set) {
            this.pointSystem = pointSystem;
        }
        return pointSystem;
    }
    /**
     * Loads Submission records file from input stream.
     * The provided file must be in valid JSON format.
     * @param stream The stream to load from
     * @param set Set the data file as the main data file
     * @return The loaded {@link SubmissionSystem}
     */
    public SubmissionSystem loadSubmissionSystem(InputStream stream, boolean set) {
        try {
            return loadSubmissionSystem(new String(stream.readAllBytes()), set);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the submissions code records data from provided JSON data.
     * @param data The JSON data to load.
     * @param set Set the data file as the main data file
     * @return The loaded {@link SubmissionSystem}
     */
    public SubmissionSystem loadSubmissionSystem(String data, boolean set) {
        SubmissionSystem submissionSystem = gson.fromJson(data, SubmissionSystem.class);
        if (set) {
            this.submissionSystem = submissionSystem;
        }
        return submissionSystem;
    }


    /**
     * Gets the currently loaded {@link PointSystem}
     * @return The loaded {@link PointSystem} file, or null if none have been loaded.
     */
    @Nullable
    public PointSystem getPointSystem() {
        return pointSystem;
    }

    /**
     * Gets the currently loaded {@link SubmissionSystem}
     * @return The loaded {@link SubmissionSystem} file, or null if none have been loaded.
     */
    @Nullable
    public SubmissionSystem getSubmissionSystem() {
        return submissionSystem;
    }

    /**
     * Gets the currently loaded {@link DataFile}.
     * @return The loaded data file, or null if none have been loaded.
     */
    @Nullable
    public DataFile getDataFile() {
        return dataFile;
    }

    /**
     * Creates a new point system
     * @return The newly created point system
     */
    public PointSystem createNewPointSystem() {
        pointSystem = new PointSystem();
        return pointSystem;
    }

    /**
     * Creates a new submission system
     * @return The newly created submission system
     */
    public SubmissionSystem createSubmissionSystem() {
        submissionSystem = new SubmissionSystem();
        return submissionSystem;
    }
}
