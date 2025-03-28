package team.logica_populi.dragonscore.base.points;

import java.util.List;
import team.logica_populi.dragonscore.base.points.LessonRecord;

public class PointSystem {
    private List<LessonRecord> lessons;

    /**
     * Default constructor
     * @param records
     */
    public PointSystem(List<LessonRecord> records){
        this.lessons = records;
    }

    public List<LessonRecord> getLessonRecords(){
        return lessons;
    }

}
