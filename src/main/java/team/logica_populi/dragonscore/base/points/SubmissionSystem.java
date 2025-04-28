package team.logica_populi.dragonscore.base.points;

import com.google.gson.Gson;
import team.logica_populi.dragonscore.base.Lesson;
import team.logica_populi.dragonscore.base.ResourceLocation;
import team.logica_populi.dragonscore.base.registries.JsonRegistry;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.*;

/**
 * Generates how the submission codes that are stored specific to users when they finish exercises
 */
public class SubmissionSystem { private static final Logger logger = Logger.getLogger(SubmissionSystem.class.getName());

    private final HashMap<String, HashMap<String, String>> submissionRecords;

    /**
     * Default constructor
     * Creates a hashmap
     */
    public SubmissionSystem(){
        this(new HashMap<>());
    }

    /**
     * Constructor of submission
     * @param submissionRecords
     */
    public SubmissionSystem(HashMap<String, HashMap<String, String>> submissionRecords) {
        this.submissionRecords = submissionRecords;
    }

    /**
     * Gets the current submission system
     * @return the current submission system
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

    /**
     * Store the submission code in to file
     * @param name users name
     * @param lesson the current lesson done
     * @param code the submission code
     */
    public void setSubmission(String name, Lesson lesson, SubmissionCode code) {

        if (submissionRecords.containsKey(name)) { // Records does contain a record for this user
            submissionRecords.get(name).put(lesson.getId(), code.getCode());
        } else { // If the records do not yet contain one for this user
            HashMap<String, String> map = new HashMap<>();
            map.put(lesson.getId(), code.getCode());
            submissionRecords.put(name, map);
        }
    }}
