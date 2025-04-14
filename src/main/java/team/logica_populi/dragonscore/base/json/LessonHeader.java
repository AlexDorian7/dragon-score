package team.logica_populi.dragonscore.base.json;

/**
 * Data Storage class to hold data about a lesson without containing the entire lesson's data
 */
public class LessonHeader {
    public final String id;
    public final String name;
    public final String description;
    public final String location;

    /**
     * Constructs a default {@code LessonHeader}
     * @param id The lesson's id
     * @param name The lesson's name
     * @param description The lesson's description
     * @param location The path to the lesson's data file
     */
    public LessonHeader(String id, String name, String description, String location) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
    }

    @Override
    public String toString() {
        return name;
    }
}
