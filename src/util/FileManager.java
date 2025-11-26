package util;

import model.Entity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager<T extends Entity> {
    private final String fileName;

    public FileManager(String fileName) {
        this.fileName = fileName;
        initializeFile();
    }

    private void initializeFile() {
        File file = new File(fileName);
        if (!file.exists()) {
            saveAll(new ArrayList<>());
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> loadAll() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?> list) {
                return (List<T>) list;
            }
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            // Si el archivo no existe o está vacío, devolvemos lista vacía
            return new ArrayList<>();
        }
    }

    public void saveAll(List<T> entities) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(entities);
            oos.flush();
        } catch (IOException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
        }
    }
}
