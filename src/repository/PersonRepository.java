package repository;

import model.Person;
import util.FileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonRepository implements Repository<Person, Long> {
    private static final String FILE_NAME = "persons.dat";
    private final FileManager<Person> fileManager;
    private Long currentId;

    public PersonRepository() {
        this.fileManager = new FileManager<>(FILE_NAME);
        this.currentId = calculateMaxId();
    }

    private Long calculateMaxId() {
        return findAll().stream()
                .map(Person::getId)
                .filter(id -> id != null)
                .max(Long::compareTo)
                .orElse(0L);
    }

    @Override
    public Person save(Person entity) {
        List<Person> persons = new ArrayList<>(fileManager.loadAll());

        if (entity.getId() == null) {
            entity.setId(++currentId);
        }

        persons.add(entity);
        fileManager.saveAll(persons);
        return entity;
    }

    @Override
    public Optional<Person> findById(Long id) {
        return fileManager.loadAll().stream()
                .filter(p -> p.getId() != null && p.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Person> findAll() {
        return fileManager.loadAll();
    }

    @Override
    public Person update(Person entity) {
        List<Person> persons = new ArrayList<>(fileManager.loadAll());

        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getId() != null && persons.get(i).getId().equals(entity.getId())) {
                persons.set(i, entity);
                fileManager.saveAll(persons);
                return entity;
            }
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        List<Person> persons = new ArrayList<>(fileManager.loadAll());
        boolean removed = persons.removeIf(p -> p.getId() != null && p.getId().equals(id));
        if (removed) {
            fileManager.saveAll(persons);
        }
        return removed;
    }

    @Override
    public void deleteAll() {
        fileManager.saveAll(List.of());
        currentId = 0L;
    }

    @Override
    public long count() {
        return fileManager.loadAll().size();
    }
}
