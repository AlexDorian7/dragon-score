package team.logica_populi.dragonscore.base.points;

import team.logica_populi.dragonscore.base.registries.EncryptionRegistry;

public class LessonRecord {
    private final String id;
    private final String username;
    private String totalPoints;
    private static final EncryptionRegistry encryptionRegistry = new EncryptionRegistry();

    /**
     * Default constructor
     */
    public LessonRecord(){
        this("", "", encryptionRegistry.encrypt("0"));
    }

    /**
     * Constructor to set up LessonRecord with parameters
     * @param id lesson id
     * @param username username of the user of lesson
     */
    public LessonRecord(String id, String username){
        this(id, username, encryptionRegistry.encrypt("0"));
    }

    /**
     * Constructor to set up LessonRecord with parameters
     * @param id lessons id
     * @param username username of the user of lesson
     * @param points the points that user have collected in the lesson
     */
    public LessonRecord(String id, String username, String points){
        this.id = id;
        this.username = username;
        this.totalPoints = encryptionRegistry.encrypt(points);
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
    public String getTotalPoints(){
        // Decrypt totalPoints data here
        return encryptionRegistry.decrypt(totalPoints);
    }

}
