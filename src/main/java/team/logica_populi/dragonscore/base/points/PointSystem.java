package team.logica_populi.dragonscore.base.points;

import team.logica_populi.dragonscore.base.Lesson;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * TODO: Please Comment me!
 */
public class PointSystem {
    private static final Logger logger = Logger.getLogger(PointSystem.class.getName());

    private final List<LessonRecord> records;

    /**
     * Default constructor
     * TODO: COMMENT ME BETTER!
     */
    public PointSystem() {
        this.records = new ArrayList<>();
    }

    /**
     * Constructor which takes in a list of lesson records
     * @param records - lesson records that has info about each lesson
     */
    public PointSystem(List<LessonRecord> records) {
        this.records = records;
    }

    /**
     * Gets the list of lessonRecords
     * @return Return all the lesson records
     */
    public List<LessonRecord> getLessonRecords(){
        return records;
    }

    /**
     * Overrides toString() to message for showing data from a LessonRecord
     * @return data in text form about the lessons record
     */
    @Override
    public String toString() {
        return "Here is what you are looking for: \n" + getLessonRecords().getLast().getId() + "\n" + getLessonRecords().getLast().getUserName() + "\n" + getLessonRecords().getLast().getTotalPoints();
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
         for (LessonRecord record : records) {
             if (record.getUserName().equals(name) && record.getId().equals(lesson.getId())) {
                 flag = false;
                 record.setTotalPoints(points);
             }
         }
         if (flag) {
             records.add(new LessonRecord(lesson.getId(), name, points));
         }
    }
}
