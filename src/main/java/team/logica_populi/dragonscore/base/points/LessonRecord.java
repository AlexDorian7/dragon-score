package team.logica_populi.dragonscore.base.points;

import java.util.List;

public class LessonRecord {
    private final String id;
    private final String userName;
    private int totalPoints;

    /**
     * Default constructor
     * @param id
     * @param userName
     */
    public LessonRecord(String id, String userName){
        this(id, userName, 0);
    }

    /**
     *
     * @param id
     * @param userName
     * @param points
     */
    public LessonRecord(String id, String userName, int points){
        this.id = id;
        this.userName = userName;
        this.totalPoints = points;
    }

    public String getId(){
        return id;
    }

    public String getUserName(){
        return userName;
    }

    public int getTotalPoints(){
        return totalPoints;
    }

}
