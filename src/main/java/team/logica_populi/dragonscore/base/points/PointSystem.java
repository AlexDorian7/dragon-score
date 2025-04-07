package team.logica_populi.dragonscore.base.points;

import com.google.gson.Gson;
import team.logica_populi.dragonscore.base.Lesson;
import team.logica_populi.dragonscore.base.registries.JsonRegistry;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

/**
 * TODO: Please Comment me!
 */
public class PointSystem {
    private static final Logger logger = Logger.getLogger(PointSystem.class.getName());

    private final HashMap<String, HashMap<String, Integer>> records;

    /**
     * Default constructor
     * TODO: COMMENT ME BETTER!
     */
    public PointSystem() {
        this(new HashMap<String, HashMap<String, Integer>>());
    }

    /**
     * Constructor which takes in a list of lesson records
     * @param records - lesson records that has info about each lesson
     */
    public PointSystem(HashMap<String, HashMap<String, Integer>> records) {
        this.records = records;
    }

    /**
     * Gets the list of lessonRecords
     * @return Return all the lesson records
     */
    public HashMap<String, HashMap<String, Integer>> getLessonRecords(){
        return records;
    }

    /**
     * Overrides toString() to message for showing data from a LessonRecord
     * @return data in text form about the lessons record
     */
    @Override
    public String toString() {
        return records.toString();
    }

    private void mergePointSystem(PointSystem other){
    }

    /**
     * Finds the correct record and sets the points for that record.
     * <p>
     * Might want to look into storing records in a way that would make this algorithm faster. Maybe Nested Hash maps?
     * @param name The name of the user
     * @param lesson The current lesson
     * @param points The amount of points to set
     */
    public void setPoints(String name, Lesson lesson, int points) {
        boolean flag = true;
         for(Map.Entry<String, HashMap<String, Integer>> e : records.entrySet()){
            if(!e.getKey().isEmpty() && name.equals(e.getKey())){
                flag = false;
                HashMap<String, Integer> map = new HashMap<>();
                map.put(lesson.getId(), points);
                records.put(name,map);
            }
            logger.info(e.getKey());
        }
         if (flag) {
             try {
                 HashMap<String, Integer> map = new HashMap<>();
                 map.put(lesson.getId(), points);
                 records.put(name, map);

                 Gson gson = JsonRegistry.getInstance().getGson();
                 Writer writer = Files.newBufferedWriter(Paths.get("src/main/resources/data/pointsystem.example.json"));
                 gson.toJson(records, writer);
                 writer.close();
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
         }
    }
}
