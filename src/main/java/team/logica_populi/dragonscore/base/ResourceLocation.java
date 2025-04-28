package team.logica_populi.dragonscore.base;

import com.google.gson.JsonElement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A resource location represents a path in the format {@code namespace:path}.
 * It provides an interface for working with both files and resources that can be specified using the resource location path system.
 * A resource location will be turned into a path of the following form {@code ./namespace/path}.
 */
public class ResourceLocation {
    private static final Logger logger = Logger.getLogger(ResourceLocation.class.getName());

    private final String namespace;
    private final String path;

    /**
     * Create a Resource Location using a separate namespace and path.
     * A resource location represents te path to a file or jar resource. It has many helper methods to read, write, and create.
     * @param namespace The namespace for this resource location
     * @param path THe path for this resource location
     */
    public ResourceLocation(String namespace, String path) {
        namespace = namespace.replaceAll("\\.", "").replaceAll("/", "").replaceAll("\\\\", "");
        this.namespace = namespace;
        while (path.startsWith(".") || path.startsWith("/") || path.startsWith("\\")) {  // remove starting . / \
            path = path.substring(1);
        }
        this.path = path;
    }

    /**
     * Create a Resource Location using a string formatted as {@code namespace:path}.
     * @param resourceLocation The formatted string
     */
    public ResourceLocation(String resourceLocation) {
        String[] split = resourceLocation.split(":");
        if (split.length == 1) {
            this.namespace = "DragonScore";
            while (resourceLocation.startsWith(".") || resourceLocation.startsWith("/") || resourceLocation.startsWith("\\")) {  // remove starting . / \
                resourceLocation = resourceLocation.substring(1);
            }
            this.path = resourceLocation;
        } else if (split.length == 2) {
            String namespace = split[0].replaceAll("\\.", "").replaceAll("/", "").replaceAll("\\\\", "");
            this.namespace = split[0];
            String path = split[1];
            while (path.startsWith(".") || path.startsWith("/") || path.startsWith("\\")) { // remove starting . / \
                path = path.substring(1);
            }
            this.path = path;
        } else {
            throw new IllegalArgumentException("Can be at most one : delimiter in resource location string!");
        }
    }

    @Override
    public String toString() {
        return namespace + ":" + path;
    }

    /**
     * Get this resource location as a path object.
     * @return The path for this resource location.
     */
    public File getAsFile() {
        return new File(namespace + "/" + path);
    }

    /**
     * Tries to get the resource this resource location is pointing to.
     * The search order will first search the current working directory (Allows for file overriding)
     * then inside the jar resources.
     * @return The resource contents as a string.
     */
    public String tryGetResource() {
        return tryGetResource("");
    }

    /**
     * Tries to get the resource this resource location is pointing to.
     * The search order will first search the current working directory (Allows for file overriding)
     * then inside the jar resources.
     * @param pathPrefix A prefix to put after the namespace but before the path.
     * @return The resource contents as a string
     */
    public String tryGetResource(String pathPrefix) {
        File file = new File(namespace + "/" + pathPrefix + (pathPrefix.isEmpty() ? "" : "/") + path);
        logger.info(file.getPath());
        if (file.exists() && file.canRead()) {
            try {
                return Files.readString(file.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            InputStream resource = getClass().getResourceAsStream("/" + file.getPath().replaceAll("\\\\", "/"));
            try {
                String data = new String(Objects.requireNonNull(resource).readAllBytes());
                resource.close();
                return data;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Create the file for this resource location if it does not exist
     * @return true if the file was created
     */
    public boolean createIfNotExists() {
        File file = getAsFile();
        if (!file.exists()) {
            try {
                boolean mkdirs = file.getParentFile().mkdirs();
                if (!mkdirs) {
                    logger.fine("Did not make parent dirs for: " + this);
                }
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    logger.warning("A file with this name already exists for: " + this);
                }
                return newFile;
            } catch (IOException e) {
                logger.log(Level.WARNING, "Failed to create file for: " + this, e);
            }
        }
        return false;
    }

    /**
     * Writes data to the file for this Resource Location.
     * This will create the file if it does not exist.
     *
     * @param data The data to write
     * @return
     */
    public void write(String data) {
        createIfNotExists();
        File file = getAsFile();
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to write to: " + this, e);
        }
    }

    /**
     * Does this fle exist?
     * @return true if the file for this resource location exists
     */
    public boolean exists() {
        return getAsFile().exists();
    }
}
