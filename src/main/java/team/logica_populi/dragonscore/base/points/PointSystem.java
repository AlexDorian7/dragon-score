package team.logica_populi.dragonscore.base.points;

import java.util.List;
import java.util.logging.Logger;

/**
 * TODO: Please Comment me!
 */
public class PointSystem {
    private static final Logger logger = Logger.getLogger(PointSystem.class.getName());
    private List<LessonRecord> lessons;

    /**
     * Default constructor
     */
    public PointSystem() {
    }

    /**
     * Constructor which takes in a list of lesson records
     * @param records - lesson records that has info about each lesson
     */
    public PointSystem(List<LessonRecord> records) {
        this.lessons = records;
    }

    /**
     * Gets the list of lessonRecords
     * @return Return all the lesson records
     */
    public List<LessonRecord> getLessonRecords(){
        return lessons;
    }

    /**
     * Overrides toString() to message for showing data from a LessonRecord
     * @return data in text form about the lessons record
     */
    @Override
    public String toString() {
        return "Here is what you are looking for: \n" + getLessonRecords().getLast().getId() + "\n" + getLessonRecords().getLast().getUserName() + "\n" + getLessonRecords().getLast().getTotalPoints();
    }

}
