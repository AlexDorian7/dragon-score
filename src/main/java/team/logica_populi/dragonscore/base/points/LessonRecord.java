package team.logica_populi.dragonscore.base.points;


public class LessonRecord {
    private String id;
    private String userName;
    private int totalPoints;


    /**
     *
     * @param id
     */
    public LessonRecord(String id){
        this(id, "", 0);
    }

    /**
     *
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
