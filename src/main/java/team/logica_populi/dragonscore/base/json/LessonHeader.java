package team.logica_populi.dragonscore.base.json;

import team.logica_populi.dragonscore.base.ResourceLocation;

/**
 * Data Storage class to hold data about a lesson without containing the entire lesson's data
 *
 * @param id          The id of the referenced lesson.
 * @param name        The name of the referenced lesson.
 * @param description The description of the referenced lesson.
 * @param location    The resource location of the referenced lesson.
 */
public record LessonHeader(String id, String name, String description, ResourceLocation location) {
    /**
     * Constructs a default {@code LessonHeader}
     *
     * @param id          The lesson's id
     * @param name        The lesson's name
     * @param description The lesson's description
     * @param location    The path to the lesson's data file
     */
    public LessonHeader {
    }

    @Override
    public String toString() {
        return name;
    }
}
