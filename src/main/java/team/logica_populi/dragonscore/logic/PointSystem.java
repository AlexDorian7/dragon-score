package team.logica_populi.dragonscore.logic;

import org.json.JSONObject;

import java.util.logging.Logger;

public class PointSystem {
    private static final Logger logger = Logger.getLogger(PointSystem.class.getName());
    private final String id;
    private String name;
    private int selected_points;
    private int current_points;

    public PointSystem(String id){
        this(id, "", 0, 0);
    }

    public PointSystem(String id, int selected_points){
        this(id, "", selected_points, 0);
    }

    public PointSystem(String id, String name, int selected_points, int current_points){
        this.id = id;
        this.name = name;
        this.selected_points = selected_points;
        this.current_points = current_points;
    }

    public String getId() {return id;}

    public String getName() {return name;}

    public int getSelected_points() {return selected_points;}

    public int getCurrent_points() {return current_points;}

    /**
     * Set the total points for the lesson
     * @param object get the currents points stored
     * @return new total points inside the lesson
     */
    public int setPoints(JSONObject object){
        String id = object.getString("id");

        if(id == null || id.isEmpty()){
            logger.warning("Failed to load point system. id is empty or null");
            return 0;
        }

        int points = object.getInt("total_points");
        int newPoints = points + this.selected_points;

        object.put("total_points", newPoints);
        current_points = newPoints;

        return current_points;
    }

    /**
     * Gets the current total points in the lesson
     * @param object the object to load
     * @return the loaded data or returns 0
     */
    public int getTotalPoints(JSONObject object) {
        String id = object.getString("id");

        if(id == null || id.isEmpty()){
            logger.warning("Failed to load point system. id is empty or null");
            return 0;
        }

        this.current_points = object.getInt("total_points");

        return current_points;
    }


    /**
     * Hash or encrypt the point
     */
    private static String encrypt_points(){
        return "0";
    }

    /**
     * Load the Point system from JSON object.
     * @param object the object to load from
     * @return the loaded data or returns null if something fails
     */
    public static PointSystem loadFromJSON(JSONObject object){
        String id = object.getString("id");
        if(id == null || id.isEmpty()){
            logger.warning("Failed to load point system. id is empty or null");
            return null;
        }

        String name = object.getString("name");
        if(name == null)  name = "";

        int current_points = object.getInt("total_points");

        return new PointSystem(id, name, 0, current_points);

    }

    @Override
    public String toString() {
        return id + ": " + name + "\n> " + "These are the total points: " + current_points + "\n";
    }
}
