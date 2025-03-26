package team.logica_populi.dragonscore.base.registries;

import org.jetbrains.annotations.Nullable;
import team.logica_populi.dragonscore.base.DataFile;
import team.logica_populi.dragonscore.base.term.Term;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * Singleton registry for loading and storing Terms and lists of Terms
 */
public class TermRegistry {

    private static final Logger logger = Logger.getLogger(TermRegistry.class.getName());

    private static final TermRegistry instance = new TermRegistry();

    private HashMap<String, List<Term>> cache;

    /**
     * Default constructor for TermRegistry
     */
    private TermRegistry() {
        cache = new HashMap<>();
    }

    /**
     * Gets a registered term list based on its name.
     * @param name The name of the term list
     * @return The registered term list or null if not registered
     */
    @Nullable
    public List<Term> getTermList(String name) {
        if (!cache.containsKey(name)) return null;
        return cache.get(name);
    }

    /**
     * Gets a random term from the registered list with given name.
     * @param name The name of the registered list
     * @return A random term in the list, or null if the list does not exist
     */
    @Nullable
    public Term getRandomTerm(String name) {
        List<Term> list = getTermList(name);
        if (list == null) return null;
        return list.get((int) (Math.random()*list.size()));
    }

    /**
     * Get the singleton instance of this class.
     * @return The instance
     */
    public static TermRegistry getInstance() {
        return instance;
    }

    /**
     * Loads the terms from a data file
     * @param dataFile The data file to load from
     */
    public void loadDataFile(DataFile dataFile) {
        cache = dataFile.getTerms();
    }

    @Override
    public String toString() {
        return cache.toString();
    }
}
