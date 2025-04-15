package team.logica_populi.dragonscore.base.points;

import com.google.gson.Gson;
import team.logica_populi.dragonscore.base.Lesson;
import team.logica_populi.dragonscore.base.ResourceLocation;
import team.logica_populi.dragonscore.base.registries.JsonRegistry;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 *  Point System handles setting points and writing it to a file for storage
 */
public class PointSystem {
    private static final Logger logger = Logger.getLogger(PointSystem.class.getName());

    private final HashMap<String, HashMap<String, Integer>> records;

    /**
     * Default constructor
     * returns a new Hashmap
     */
    public PointSystem() {
        this(new HashMap<>());
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

    /**
     * Finds the correct record and sets the points.dat for that record.
     * <p>
     * Might want to look into storing records in a way that would make this algorithm faster. Maybe Nested Hash maps?
     * @param name The name of the user
     * @param lesson The current lesson
     * @param points The amount of points.dat to set
     */
    public void setPoints(String name, Lesson lesson, int points) {
        Gson gson = JsonRegistry.getInstance().getGson();

        if (records.containsKey(name)) { // Records does contain a record for this user
            records.get(name).put(lesson.getId(), points);
        } else { // If the records do not yet contain one for this user
            HashMap<String, Integer> map = new HashMap<>();
            map.put(lesson.getId(), points);
            records.put(name, map);
        }
        // Write to the file.
        ResourceLocation location = new ResourceLocation("dynamic:points.dat");
        location.write(gson.toJson(records));
    }
}
