package ui;

import model.Person;
import service.PersonService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleMenu {
    private final PersonService personService;
    private final Scanner scanner;

    public ConsoleMenu() {
        this.personService = new PersonService();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean exit = false;

        while (!exit) {
            showMenu();
            int option = readInt("Seleccione una opción: ");

            try {
                switch (option) {
                    case 1 -> createPerson();
                    case 2 -> readPerson();
                    case 3 -> listAllPersons();
                    case 4 -> updatePerson();
                    case 5 -> deletePerson();
                    case 6 -> deleteAllPersons();
                    case 7 -> showStatistics();
                    case 0 -> exit = true;
                    default -> System.out.println("❌ Opción inválida. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.err.println("❌ Error: " + e.getMessage());
            }

            if (!exit) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
            }
        }

        System.out.println("\n👋 ¡Hasta luego!");
        scanner.close();
    }

    private void showMenu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║     CRUD DE PERSONAS - SERIALIZADO     ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║  1. ➕ Crear Persona                   ║");
        System.out.println("║  2. 🔍 Buscar Persona por ID           ║");
        System.out.println("║  3. 📋 Listar Todas las Personas       ║");
        System.out.println("║  4. ✏️  Actualizar Persona              ║");
        System.out.println("║  5. 🗑️  Eliminar Persona                ║");
        System.out.println("║  6. 🗑️  Eliminar Todas las Personas     ║");
        System.out.println("║  7. 📊 Estadísticas                    ║");
        System.out.println("║  0. 🚪 Salir                           ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    private void createPerson() {
        System.out.println("\n➕ CREAR NUEVA PERSONA");
        System.out.println("─────────────────────────");

        String name = readNonEmptyString("Nombre: ");
        String email = readEmail("Email: ");
        int age = readIntWithMin("Edad: ", 0);

        Person person = new Person(null, name, email, age);
        // PersonService puede lanzar IllegalArgumentException; se captura en el bucle principal
        Person created = personService.create(person);

        System.out.println("\n✅ Persona creada exitosamente:");
        System.out.println(created);
    }

    private void readPerson() {
        System.out.println("\n🔍 BUSCAR PERSONA");
        System.out.println("──────────────────");

        Long id = readLongWithMin("Ingrese el ID: ", 1);
        Optional<Person> person = personService.read(id);

        if (person.isPresent()) {
            System.out.println("\n✅ Persona encontrada:");
            System.out.println(person.get());
        } else {
            System.out.println("\n❌ No se encontró ninguna persona con el ID: " + id);
        }
    }

    private void listAllPersons() {
        System.out.println("\n📋 LISTA DE PERSONAS");
        System.out.println("════════════════════════════════════════════════════════════");

        List<Person> persons = personService.readAll();

        if (persons.isEmpty()) {
            System.out.println("No hay personas registradas.");
        } else {
            persons.forEach(System.out::println);
            System.out.println("════════════════════════════════════════════════════════════");
            System.out.println("Total: " + persons.size() + " persona(s)");
        }
    }

    private void updatePerson() {
        System.out.println("\n✏️ ACTUALIZAR PERSONA");
        System.out.println("──────────────────────");

        Long id = readLongWithMin("Ingrese el ID de la persona a actualizar: ", 1);
        Optional<Person> existing = personService.read(id);

        if (existing.isEmpty()) {
            System.out.println("❌ No se encontró ninguna persona con el ID: " + id);
            return;
        }

        System.out.println("\nDatos actuales:");
        System.out.println(existing.get());
        System.out.println("\nIngrese los nuevos datos (Enter para mantener el valor actual):");

        String nameInput = readStringOptional("Nombre [" + existing.get().getName() + "]: ");
        String emailInput = readStringOptional("Email [" + existing.get().getEmail() + "]: ");
        String ageStr = readStringOptional("Edad [" + existing.get().getAge() + "]: ");

        String newName = nameInput.isEmpty() ? existing.get().getName() : nameInput;

        String newEmail = existing.get().getEmail();
        if (emailInput.isEmpty()) {
            newEmail = existing.get().getEmail();
        } else {
            if (isValidEmail(emailInput)) {
                newEmail = emailInput;
            } else {
                System.out.println("❌ Email inválido, se mantiene el valor anterior.");
            }
        }

        int newAge = existing.get().getAge();
        if (!ageStr.isEmpty()) {
            try {
                int parsed = Integer.parseInt(ageStr);
                if (parsed < 0) {
                    System.out.println("❌ La edad no puede ser negativa, se mantiene el valor anterior.");
                } else {
                    newAge = parsed;
                }
            } catch (NumberFormatException ex) {
                System.out.println("❌ Edad inválida, se mantiene el valor anterior.");
            }
        }

        Person updated = new Person();
        updated.setId(id);
        updated.setName(newName);
        updated.setEmail(newEmail);
        updated.setAge(newAge);

        personService.update(updated);
        System.out.println("\n✅ Persona actualizada exitosamente:");
        System.out.println(updated);
    }

    private void deletePerson() {
        System.out.println("\n🗑️ ELIMINAR PERSONA");
        System.out.println("────────────────────");

        Long id = readLongWithMin("Ingrese el ID de la persona a eliminar: ", 1);
        Optional<Person> person = personService.read(id);

        if (person.isEmpty()) {
            System.out.println("❌ No se encontró ninguna persona con el ID: " + id);
            return;
        }

        System.out.println("\nPersona a eliminar:");
        System.out.println(person.get());

        String confirm = readString("\n¿Está seguro? (S/N): ");

        if (confirm.equalsIgnoreCase("S") || confirm.equalsIgnoreCase("SI")) {
            boolean deleted = personService.delete(id);
            if (deleted) {
                System.out.println("✅ Persona eliminada exitosamente.");
            } else {
                System.out.println("❌ No se pudo eliminar la persona.");
            }
        } else {
            System.out.println("❌ Operación cancelada.");
        }
    }

    private void deleteAllPersons() {
        System.out.println("\n🗑️ ELIMINAR TODAS LAS PERSONAS");
        System.out.println("────────────────────────────────");

        long count = personService.count();
        System.out.println("Total de personas a eliminar: " + count);

        String confirm = readString("\n⚠️ ¿Está ABSOLUTAMENTE seguro? (SI/NO): ");

        if (confirm.equalsIgnoreCase("SI")) {
            personService.deleteAll();
            System.out.println("✅ Todas las personas han sido eliminadas.");
        } else {
            System.out.println("❌ Operación cancelada.");
        }
    }

    private void showStatistics() {
        System.out.println("\n📊 ESTADÍSTICAS");
        System.out.println("════════════════");

        long count = personService.count();
        System.out.println("Total de personas: " + count);

        if (count > 0) {
            List<Person> persons = personService.readAll();
            double avgAge = persons.stream()
                    .mapToInt(Person::getAge)
                    .average()
                    .orElse(0.0);

            int minAge = persons.stream()
                    .mapToInt(Person::getAge)
                    .min()
                    .orElse(0);

            int maxAge = persons.stream()
                    .mapToInt(Person::getAge)
                    .max()
                    .orElse(0);

            System.out.printf("Edad promedio: %.2f años%n", avgAge);
            System.out.println("Edad mínima: " + minAge + " años");
            System.out.println("Edad máxima: " + maxAge + " años");
        }
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private String readNonEmptyString(String prompt) {
        while (true) {
            String str = readString(prompt);
            if (!str.isEmpty()) return str;
            System.out.println("❌ El valor no puede estar vacío.");
        }
    }

    private String readEmail(String prompt) {
        while (true) {
            String email = readString(prompt);
            if (isValidEmail(email)) return email;
            System.out.println("❌ Email inválido. Ejemplo válido: usuario@dominio.com");
        }
    }

    private boolean isValidEmail(String email) {
        if (email == null) return false;
        String regex = "^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$";
        return email.matches(regex);
    }

    private String readStringOptional(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, ingrese un número válido.");
            }
        }
    }

    private int readIntWithMin(String prompt, int min) {
        while (true) {
            int value = readInt(prompt);
            if (value >= min) return value;
            System.out.println("❌ El valor debe ser mayor o igual a " + min + ".");
        }
    }

    private Long readLong(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, ingrese un número válido.");
            }
        }
    }

    private Long readLongWithMin(String prompt, long min) {
        while (true) {
            Long value = readLong(prompt);
            if (value >= min) return value;
            System.out.println("❌ El valor debe ser mayor o igual a " + min + ".");
        }
    }
}
