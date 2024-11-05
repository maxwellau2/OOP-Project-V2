package Interfaces;

public interface IEntity{
    String getId();
    // loading utils
    String toCSV();
    static IEntity fromCSV(String csvLine) {
        return null;
    }
    // helper methods
    String toString();
}
