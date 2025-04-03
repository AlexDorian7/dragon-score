package team.logica_populi.dragonscore.base.registries;

import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

/**
 * Registry for handling middle man communications.
 * Also stores state information about the overall state of the program and the users state through it.
 */
public class DragonHandler {
    private static final Logger logger = Logger.getLogger(DragonHandler.class.getName());

    private static DragonHandler currentSession;

    private final String name;
    private int points = 0;

    /**
     * Default constructor.
     * @param name The name of the user currently using the program
     */
    private DragonHandler(String name) {
        this.name = name;
    }

    /**
     * Makes a new Session from the provided name.
     * @param name The user's name
     * @return The new session
     */
    public static DragonHandler newSession(String name) {
        if (currentSession != null)
            logger.fine("Replacing old session with new one.");
        currentSession = new DragonHandler(name);
        return currentSession;
    }

    /**
     * Gets the current session.
     * @return The current session or null if none
     */
    @Nullable
    public static DragonHandler getCurrentSession() {
        return currentSession;
    }

    /**
     * Gets the name of the user for this current session.
     * @return The user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Adds points to the current point total.
     * @param points The amount of points to add
     */
    protected void addPoints(int points) {
        this.points += points;
    }

    /**
     * Sets the current amount of points tied to this session.
     * @param points The new point count
     */
    protected void setPoints(int points) {
        this.points = points;
    }

    /**
     * Get the current amount of points tied to this session.
     * @return The current point count
     */
    public int getPoints() {
        return points;
    }


}
