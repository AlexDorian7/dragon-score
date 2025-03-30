package team.logica_populi.dragonscore.base.points;

import java.util.List;
import java.util.logging.Logger;

import team.logica_populi.dragonscore.base.registries.EncryptionRegistry;

public class PointSystem {
    private static final Logger logger = Logger.getLogger(PointSystem.class.getName());
    private List<LessonRecord> lessons;
    private static final EncryptionRegistry encryptionRegistry = new EncryptionRegistry();

    /**
     * Default constructor
     */
    public PointSystem(){
    }

    /**
     * Constructor which takes in a list of lesson records
     * @param records - lesson records that has info about each lesson
     */
    public PointSystem(List<LessonRecord> records){
        this.lessons = records;
    }

    /**
     * Gets the list of lessonRecords
     * @return Return all the lesson records
     */
    public List<LessonRecord> getLessonRecords(){
        return lessons;
    }


    public List<LessonRecord> encryptPoints(){
        for (LessonRecord lesson : lessons) {
            if (lesson.getTotalPoints().equals("0")) {
                String encryptedPoints = encryptionRegistry.encrypt(lesson.getTotalPoints());
                logger.info(encryptedPoints);
            }
        }

        return lessons;
    }

    /**
     * Overrides toString() to message for showing data from a LessonRecord
     * @return data in text form about the lessons record
     */
    @Override
    public String toString(){
        return "Here is what you are looking for: \n" + getLessonRecords().getLast().getId() + "\n" + getLessonRecords().getLast().getUserName() + "\n" + getLessonRecords().getLast().getTotalPoints();
    }

}
