package Abstract;

import Interfaces.IEntity;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;

/**
 * Abstract base class for managing entities stored in a CSV file.
 * This class provides common CRUD operations and caching mechanisms
 * for entities implementing the {@link IEntity} interface.
 *
 * @param <T> The type of entity that extends {@link IEntity}.
 */
public abstract class Repository<T extends IEntity> {
    protected List<T> entities = new ArrayList<>();
    protected String csvPath;
    private final int cacheSize = 100; // Specify the maximum size of the LRU cache
    private final Map<String, T> lruCache;


    /**
     * Constructs a new {@code Repository} with the specified CSV file path.
     *
     * @param csvPath The path to the CSV file.
     */
    public Repository(String csvPath) {
        this.csvPath = csvPath;

        // Initialize the LRU cache with access order
        this.lruCache = new LinkedHashMap<>(cacheSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, T> eldest) {
                return size() > cacheSize;
            }
        };
    }

    /**
     * Reads all lines from a CSV file.
     *
     * @param path The path to the CSV file.
     * @return A list of strings, each representing a line in the CSV file.
     */
    protected List<String> readCSV(String path) {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage(), e);
        }
    }


    /**
     * Creates a new entity and adds it to the repository.
     *
     * @param entity The entity to create.
     * @return The created entity, or {@code null} if it already exists.
     */
    public T create(T entity) {
        for (T t : entities) {
            if (t.equals(entity)) {
                return null;
            }
        }
        entities.add(entity);
        this.store();
        return entity;
    }

    /**
     * Retrieves an entity by its unique ID.
     *
     * @param id The ID of the entity to retrieve.
     * @return The entity with the specified ID, or {@code null} if not found.
     */
    public T read(String id) {
        // Check the LRU cache first
        if (lruCache.containsKey(id)) {
            return lruCache.get(id);
        }

        // If not in the cache, search in the main list
        Optional<T> entity = entities.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();

        if (entity.isPresent()) {
            T foundEntity = entity.get();
            lruCache.put(id, foundEntity); // Add to the cache
            return foundEntity;
        } else {
            return null;
        }
    }

    public List<T> filterById(String id) {
        return this.getByFilter((T entry) -> entry.getId().equals(id));
    }

    /**
     * Retrieves all entities matching a specific filter.
     *
     * @param predicate The predicate to filter entities.
     * @return A list of entities matching the filter.
     */
    public List<T> getByFilter(Predicate<T> predicate) {
        return this.entities.stream().filter(predicate).toList();
    }


    /**
     * Updates an existing entity.
     *
     * @param entity The entity to update.
     * @return The updated entity, or {@code null} if not found.
     */
    public T update(T entity) {
        String id = entity.getId();
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId().equals(id)) {
                entities.set(i, entity);
                lruCache.put(id, entity); // Update the cache
                this.store();
                return entity;
            }
        }
        return null;
    }

    /**
     * Deletes an entity from the repository.
     *
     * @param item The entity to delete.
     * @return The deleted entity, or {@code null} if not found.
     */
    public T delete(T item) {
        boolean removed = entities.removeIf(e -> e.getId().equals(item.getId()));
        if (removed) {
            lruCache.remove(item.getId()); // Remove from the cache
            this.store();
            return item;
        }
        return null;
    }

    /**
     * Retrieves all entities in the repository.
     *
     * @return A list of all entities.
     */
    public List<T> getAll() {
        return new ArrayList<>(entities);
    }

    /**
     * Converts a CSV line into an entity.
     *
     * @param csv The CSV line.
     * @return The corresponding entity.
     */
    protected abstract T fromCSV(String csv);

    /**
     * Generates a new unique ID for an entity based on the last ID in the repository.
     *
     * @return A new unique ID.
     */
    protected String getLastId() {
        if (entities == null || entities.isEmpty()) {
            return "0";
        }
        int lastEntry = entities.size() - 1;
        if (lastEntry < 0) return "0"; // Edge case for empty CSV
        if (entities.get(lastEntry) == null) return "0";
        return entities.get(lastEntry).getId();
    }

    /**
     * Retrieves the last ID from the list of entities.
     *
     * @return The last ID as a string.
     */
    public String generateId() {
        String lastId = this.getLastId();
        int i;
        for (i = 0; i < lastId.length(); i++) {
            if (Character.isDigit(lastId.charAt(i))) {
                break;
            }
        }
        String prefix = lastId.substring(0, i);
        int number = Integer.parseInt(lastId.substring(i));
        number++;
        return String.format("%s%03d", prefix, number);
    }

    /**
     * Gets the header for the CSV file.
     *
     * @return The header as a string.
     */
    protected abstract String getHeader();

    /**
     * Loads entities from the CSV file into memory.
     *
     * @return {@code true} if successful, {@code false} otherwise.
     */
    public boolean load() {
        try {
            // Check if the file exists
            if (!Files.exists(Paths.get(csvPath))) {
                // If the file does not exist, create it and write the header
                Files.createFile(Paths.get(csvPath));
                try (BufferedWriter headerWriter = new BufferedWriter(new FileWriter(csvPath))) {
                    headerWriter.write(getHeader());
                    headerWriter.newLine();
                }
//                System.out.println("CSV file created with headers: " + csvPath);
                return true; // File created but no data to load
            }

            // Read all lines from the CSV file
            List<String> lines = readCSV(csvPath);

            // Clear existing entities to avoid duplicates
            entities.clear();

            // Process the lines (skip the header)
            for (int i = 1; i < lines.size(); i++) { // Start from 1 to skip header
                String line = lines.get(i).trim();
                if (!line.isEmpty()) { // Skip empty lines
                    T entity = fromCSV(line);
                    entities.add(entity);
                }
            }

//            System.out.println("Loaded " + entities.size() + " entities from: " + csvPath);
            return true;

        } catch (Exception e) {
            System.err.println("Error loading data from CSV: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Saves all entities to the CSV file.
     *
     * @return {@code true} if successful, {@code false} otherwise.
     */
    public boolean store() {
        // Write to a temporary file to ensure atomicity
        String tempFilePath = csvPath + ".tmp";

        try {
            // Check if the file exists, create it if it doesn't
            if (!Files.exists(Paths.get(csvPath))) {
                Files.createFile(Paths.get(csvPath));
                try (BufferedWriter headerWriter = new BufferedWriter(new FileWriter(csvPath))) {
                    headerWriter.write(getHeader());
                    headerWriter.newLine();
                }
            }

            // Write data to the temporary file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempFilePath))) {
                // Write the header
                bw.write(getHeader());
                bw.newLine();

                // Write all entities
                for (T entity : entities) {
                    bw.write(entity.toCSV());
                    bw.newLine();
                }
            }

            // Replace the original file with the temporary file
            Files.move(Paths.get(tempFilePath), Paths.get(csvPath), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            return true;
        } catch (IOException e) {
            System.err.println("Error storing data to CSV: " + e.getMessage());
            try {
                // Cleanup the temporary file if something goes wrong
                Files.deleteIfExists(Paths.get(tempFilePath));
            } catch (IOException cleanupException) {
                System.err.println("Failed to delete temporary file: " + cleanupException.getMessage());
            }
            return false;
        }
    }



    public boolean deleteById(String id) {
        boolean removed = entities.removeIf(e -> e.getId().equals(id));
        if (removed) {
            lruCache.remove(id); // Remove from the cache
            this.store();
            return true;
        }
        return false;
    }

    /**
     * Displays all entities in the repository.
     */
    public void display() {
        if (entities.size() <= 0) {
            System.out.println("No entities found.");
        }
        for (T entity : entities) {
            System.out.println(entity);
        }
    }
}
