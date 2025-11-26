package service;

import model.Person;
import repository.PersonRepository;

import java.util.List;
import java.util.Optional;

public class PersonService implements CrudService<Person, Long> {
    private final PersonRepository repository;

    public PersonService() {
        this.repository = new PersonRepository();
    }

    @Override
    public Person create(Person entity) {
        validate(entity, true);
        return repository.save(entity);
    }

    @Override
    public Optional<Person> read(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Person> readAll() {
        return repository.findAll();
    }

    @Override
    public Person update(Person entity) {
        if (entity.getId() == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo para actualizar");
        }
        validate(entity, false);
        Optional<Person> existing = repository.findById(entity.getId());
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("No existe una persona con el ID: " + entity.getId());
        }
        return repository.update(entity);
    }

    @Override
    public boolean delete(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public long count() {
        return repository.count();
    }

    private void validate(Person p, boolean creating) {
        if (p == null) throw new IllegalArgumentException("La persona no puede ser nula");
        if (!creating && p.getId() == null)
            throw new IllegalArgumentException("El ID es obligatorio para actualizar");
        if (p.getName() == null || p.getName().trim().isEmpty())
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        if (p.getAge() < 0)
            throw new IllegalArgumentException("La edad no puede ser negativa");
        if (p.getEmail() == null || p.getEmail().trim().isEmpty())
            throw new IllegalArgumentException("El email no puede estar vacío");
    }
}
