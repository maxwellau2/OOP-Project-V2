package Abstract;

import Interfaces.IEntity;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class Repository<T extends IEntity> {
    protected List<T> entities = new ArrayList<>();
    protected String csvPath;

    public Repository(String csvPath) {
        this.csvPath = csvPath;
    }

    protected List<String> readCSV(String path) {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage(), e);
        }
    }

    public T create(T entity) {
        // check if entity is in entities
        for (T t : entities) {
            if (t.equals(entity)) {
                return null;
            }
        }
        entities.add(entity);
//        System.out.println("Entity created: " + entity);
        this.store();
        return entity;
    }

    public T read(String id) {
        Optional<T> entity = entities.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
        if (entity.isPresent()) {
//            System.out.println("Entity found: " + entity.get());
            return entity.get();
        } else {
//            System.out.println("Entity not found with ID: " + id);
            return null;
        }
    }

    public List<T> filterById(String id) {
        return this.getByFilter((T entry) -> entry.getId().equals(id));
    }

    public List<T> getByFilter(Predicate<T> predicate){
        return this.entities.stream().filter(predicate).toList();
    }

    public T update(T entity) {
        String id = entity.getId();
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId().equals(id)) {
                entities.set(i, entity);
//                System.out.println("Entity updated: " + entity);
                this.store();
                return entity;
            }
        }

//        System.out.println("Entity not found for update with ID: " + id);
        return null;
    }

    public T delete(T item) {
        boolean removed = entities.removeIf(e -> e.getId().equals(item.getId()));
        if (removed) {
            this.store();
            return item;
        }
        return null;
    }

    public List<T> getAll() {
        return new ArrayList<>(entities);
    }

    protected abstract T fromCSV(String csv);

    protected String getLastId(){

        if (entities==null || entities.isEmpty()) {
            return "0";
        }
        int lastEntry = entities.size() - 1;
        // look at the id of the last entry
        if (lastEntry < 0) return "0"; // edge case for empty csv
        if (entities.get(lastEntry) == null) return "0";
        return entities.get(lastEntry).getId();
    }

    public String generateId() {
        String lastId = this.getLastId();
        // Find the index where the numeric part starts using a for loop
        int i;
        for (i = 0; i < lastId.length(); i++) {
            if (Character.isDigit(lastId.charAt(i))) {
                break;
            }
        }
        // Separate the prefix and the numeric part
        String prefix = lastId.substring(0, i);
        int number = Integer.parseInt(lastId.substring(i));
        // Increment the numeric part
        number++;
        // Format the new ID with leading zeros (adjust %03d based on required width)
        return String.format("%s%03d", prefix, number);
    }

    protected abstract String getHeader();

    public boolean load(){
        entities.clear();
        try {
            List<String> lines = readCSV(this.csvPath);
            // flag is used to help skip first line (the csv headers)
            int flag = 0;
            for (String line : lines) {
                if (flag == 1){
                    T entity = fromCSV(line);
//                    System.out.println("Entity loaded: " + entity);
                    entities.add(entity);
                }
                else{
                    flag = 1;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Store data to a CSV file
    public boolean store() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvPath))) {
            bw.write(getHeader());
            bw.newLine();
            for (T entity : entities) {
                bw.write(entity.toCSV());
                bw.newLine();
            }
//            System.out.println("Data stored to CSV.");
            return true;
        } catch (IOException e) {
            System.err.println("Error storing data to CSV: " + e.getMessage());
            return false;
        }
    }

    public void display(){
        if (entities.size() <= 0) {
            System.out.println("No entities found.");
        }
        for (T entity : entities) {
            System.out.println(entity);
        }
    }
}
