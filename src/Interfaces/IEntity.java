package Interfaces;

/**
 * The {@code IEntity} interface defines the contract for entity objects,
 * specifying methods for obtaining an entity's unique identifier,
 * converting the entity to and from CSV format, and representing it as a string.
 */
public interface IEntity {

    /**
     * Retrieves the unique identifier of the entity.
     *
     * @return the unique identifier of the entity as a {@code String}.
     */
    String getId();

    /**
     * Converts the entity to a CSV (Comma-Separated Values) formatted {@code String}.
     *
     * @return a CSV representation of the entity.
     */
    String toCSV();

    /**
     * Constructs an {@code IEntity} object from a CSV-formatted {@code String}.
     *
     * @param csvLine a {@code String} containing the CSV representation of the entity.
     * @return an {@code IEntity} object, or {@code null} if the method is not implemented.
     */
    static IEntity fromCSV(String csvLine) {
        return null;
    }

    /**
     * Provides a string representation of the entity.
     *
     * @return a string representation of the entity.
     */
    String toString();
}
