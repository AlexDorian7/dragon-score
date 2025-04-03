package team.logica_populi.dragonscore.base.points;

import org.jetbrains.annotations.Nullable;
import team.logica_populi.dragonscore.base.registries.EncryptionRegistry;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TODO: Please comment me!
 */
public class LessonRecord {
    private static final Logger logger = Logger.getLogger(LessonRecord.class.getName());

    private final String id;
    private final String username;
    private int totalPoints;

    /**
     * Default constructor
     */
    public LessonRecord() {
        this("", "", 0);
    }

    /**
     * Constructor to set up LessonRecord with parameters
     * @param id lesson id
     * @param username username of the user of lesson
     */
    public LessonRecord(String id, String username) {
        this(id, username, 0);
    }

    /**
     * Constructor to set up LessonRecord with parameters
     * @param id lessons id
     * @param username username of the user of lesson
     * @param points the points that user have collected in the lesson
     */
    public LessonRecord(String id, String username, int points) {
        this.id = id;
        this.username = username;
        this.totalPoints = points;
    }

    /**
     *  Gets the lesson id
     * @return the lesson id
     */
    public String getId(){
        return id;
    }

    /**
     * Gets the username
     * @return username of the lesson
     */
    public String getUserName(){
        return username;
    }

    /**
     * Gets the total points in the current lesson
     * @return total points the lesson
     */
    public int getTotalPoints(){
        return totalPoints;
    }

    /**
     * Create and Encrypt a record
     * @return The encrypted record
     */
    public String makeRecord() {
        return EncryptionRegistry.getInstance().encrypt(username + ": " + id + ": " + totalPoints);
    }

    /**
     * Create a LessonRecord from encrypted data.
     * @param data The encrypted data
     * @return The loaded LessonRecord or null if something failed
     */
    @Nullable
    public static LessonRecord loadRecord(String data) {
        String recordStr = EncryptionRegistry.getInstance().decrypt(data);
        String[] split = recordStr.split(": ");
        try {
            return new LessonRecord(split[1], split[0], Integer.parseInt(split[2]));
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to load Lesson Record!", e);
            return null;
        }
    }

}
