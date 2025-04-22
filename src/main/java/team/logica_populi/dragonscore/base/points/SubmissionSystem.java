package team.logica_populi.dragonscore.base.points;

import com.google.gson.Gson;
import team.logica_populi.dragonscore.base.Lesson;
import team.logica_populi.dragonscore.base.ResourceLocation;
import team.logica_populi.dragonscore.base.registries.JsonRegistry;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.*;

/**
 * PLEASE COMMENT ME
 */
public class SubmissionSystem { private static final Logger logger = Logger.getLogger(SubmissionSystem.class.getName());

    private final HashMap<String, HashMap<String, String>> submissionRecords;

    /**
     * PLEASE COMMENT ME
     */
    public SubmissionSystem(){
        this(new HashMap<>());
    }

    /**
     * PLEASE COMMENT ME
     * @param submissionRecords
     */
    public SubmissionSystem(HashMap<String, HashMap<String, String>> submissionRecords) {
        this.submissionRecords = submissionRecords;
    }

    /**
     * PLEASE COMMENT ME
     * @return
     */
    public HashMap<String, HashMap<String, String>> getSubmissions(){
        return submissionRecords;
    }

    /**
     * Overrides toString() to message for showing data from a LessonRecord
     * @return data in text form about the lessons record
     */
    @Override
    public String toString() {
        return submissionRecords.toString();
    }

    public void setSubmission(String name, Lesson lesson, SubmissionCode code) {
        Gson gson = JsonRegistry.getInstance().getGson();

        if (submissionRecords.containsKey(name)) { // Records does contain a record for this user
            submissionRecords.get(name).put(lesson.getId(), code.getCode());
        } else { // If the records do not yet contain one for this user
            HashMap<String, String> map = new HashMap<>();
            map.put(lesson.getId(), code.getCode());
            submissionRecords.put(name, map);
        }
        // Write to the file.
        ResourceLocation location = new ResourceLocation("dynamic:submissions.dat");
        location.write(gson.toJson(submissionRecords));
    }}
