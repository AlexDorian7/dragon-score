package team.logica_populi.dragonscore.base.points;

public class LessonRecord {
    private final String id;
    private final String username;
    private int totalPoints;

    /**
     * Default constructor
     */
    public LessonRecord(){
        this("", "", 0);
    }

    /**
     * Constructor to set up LessonRecord with parameters
     * @param id lesson id
     * @param username username of the user of lesson
     */
    public LessonRecord(String id, String username){
        this(id, username, 0);
    }

    /**
     * Constructor to set up LessonRecord with parameters
     * @param id lessons id
     * @param username username of the user of lesson
     * @param points the points that user have collected in the lesson
     */
    public LessonRecord(String id, String username, int points){
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

}
