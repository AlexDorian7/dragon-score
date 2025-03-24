package team.logica_populi.dragonscore.base.registries;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import team.logica_populi.dragonscore.base.Term;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Singleton registry for loading and storing Terms and lists of Terms
 */
public class TermRegistry {

    private static final Logger logger = Logger.getLogger(TermRegistry.class.getName());

    private static final TermRegistry instance = new TermRegistry();

    private final HashMap<String, List<Term>> cache;

    /**
     * Default constructor for TermRegistry
     */
    private TermRegistry() {
        cache = new HashMap<>();
    }

    /**
     * Loads and registers a term list from the provided JSON array.
     * @param termArray The JSON array to load from
     * @param name The name to register under
     */
    public void loadTermList(JSONArray termArray, String name) {
        if (!cache.containsKey(name))
            cache.put(name, new ArrayList<>());
        List<Term> list = cache.get(name);
        for (Object obj : termArray) {
            if (obj instanceof JSONObject) {
                Term term = Term.loadFromJSON((JSONObject) obj);
                if (term == null) {
                    logger.info("Failed to load term for term list " + name + "!");
                } else {
                    list.add(term);
                }
            }
        }
    }

    /**
     * Loads and registers from a JSON object.
     * @param object The JSON object to load from
     */
    public void loadTermLists(JSONObject object) {
        for (Iterator<String> it = object.keys(); it.hasNext(); ) {
            String key = it.next();
            JSONArray jsonArray = object.getJSONArray(key);
            if (jsonArray == null || jsonArray.isEmpty()) {
                logger.info("Failed to register term list " + key + "!");
            } else {
                loadTermList(jsonArray, key);
            }
        }
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
     * Get the singleton instance of this class.
     * @return The instance
     */
    public static TermRegistry getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return cache.toString();
    }
}
