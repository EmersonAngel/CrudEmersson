package ui.fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Person;
import service.PersonService;

import java.util.Optional;

public class PersonCrudView {

    private final PersonService service = new PersonService();

    private final BorderPane root = new BorderPane();
    private final TableView<Person> table = new TableView<>();
    private final ObservableList<Person> data = FXCollections.observableArrayList();

    // Campos del formulario
    private final TextField nameField = new TextField();
    private final TextField emailField = new TextField();
    private final TextField ageField = new TextField();

    // Botones con íconos
    private final Button createBtn = new Button("✨ Crear");
    private final Button updateBtn = new Button("✏️ Actualizar");
    private final Button deleteBtn = new Button("🗑️ Eliminar");
    private final Button clearBtn = new Button("🧹 Limpiar");
    private final Button refreshBtn = new Button("🔄 Refrescar");

    public PersonCrudView() {
        buildUI();
        bindEvents();
        loadData();
    }

    public Parent getRoot() {
        return root;
    }

    private void buildUI() {
        // Aplicar clase CSS al contenedor principal
        root.getStyleClass().add("main-container");

        // ============================================
        // HEADER CON TÍTULO MODERNO
        // ============================================
        VBox titleContainer = new VBox();
        titleContainer.getStyleClass().add("title-bar");
        titleContainer.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("📊 Gestión de Personas");
        title.getStyleClass().add("title-label");

        Label subtitle = new Label("Sistema CRUD con Serialización de Archivos");
        subtitle.getStyleClass().add("subtitle-label");

        titleContainer.getChildren().addAll(title, subtitle);

        // ============================================
        // TABLA CON DISEÑO MEJORADO
        // ============================================
        TableColumn<Person, Long> idCol = new TableColumn<>("🆔 ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(80);
        idCol.setResizable(false);

        TableColumn<Person, String> nameCol = new TableColumn<>("👤 Nombre");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(220);

        TableColumn<Person, String> emailCol = new TableColumn<>("📧 Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setPrefWidth(300);

        TableColumn<Person, Integer> ageCol = new TableColumn<>("🎂 Edad");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        ageCol.setPrefWidth(100);
        ageCol.setResizable(false);

        table.getColumns().addAll(idCol, nameCol, emailCol, ageCol);
        table.setItems(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Label emptyLabel = new Label("📭 No hay personas registradas");
        emptyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #8d99ae;");
        table.setPlaceholder(emptyLabel);

        VBox tableBox = new VBox(table);
        tableBox.setPadding(new Insets(20, 30, 10, 30));
        VBox.setVgrow(table, Priority.ALWAYS);

        // ============================================
        // FORMULARIO MEJORADO
        // ============================================
        GridPane form = new GridPane();
        form.getStyleClass().add("form-container");
        form.setHgap(15);
        form.setVgap(15);

        // Configurar columnas del GridPane
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(100);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        form.getColumnConstraints().addAll(col1, col2);

        // Labels con estilo
        Label nameLabel = new Label("Nombre:");
        nameLabel.getStyleClass().add("form-label");

        Label emailLabel = new Label("Email:");
        emailLabel.getStyleClass().add("form-label");

        Label ageLabel = new Label("Edad:");
        ageLabel.getStyleClass().add("form-label");

        // Campos de texto con placeholders mejorados
        nameField.setPromptText("Ingrese el nombre completo");
        nameField.setPrefWidth(300);

        emailField.setPromptText("ejemplo@correo.com");
        emailField.setPrefWidth(300);

        ageField.setPromptText("Edad (0-120)");
        ageField.setPrefWidth(300);

        // Añadir al formulario
        form.add(nameLabel, 0, 0);
        form.add(nameField, 1, 0);

        form.add(emailLabel, 0, 1);
        form.add(emailField, 1, 1);

        form.add(ageLabel, 0, 2);
        form.add(ageField, 1, 2);

        // ============================================
        // BOTONES CON ESTILOS Y EFECTOS
        // ============================================
        createBtn.getStyleClass().add("btn-create");
        createBtn.setPrefWidth(120);
        createBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #52b788, #40916c); " +
                          "-fx-text-fill: white; -fx-background-radius: 8; -fx-font-weight: bold;");

        updateBtn.getStyleClass().add("btn-update");
        updateBtn.setPrefWidth(130);
        updateBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #0096c7, #0077b6); " +
                          "-fx-text-fill: white; -fx-background-radius: 8; -fx-font-weight: bold;");

        deleteBtn.getStyleClass().add("btn-delete");
        deleteBtn.setPrefWidth(120);
        deleteBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #ef233c, #d90429); " +
                          "-fx-text-fill: white; -fx-background-radius: 8; -fx-font-weight: bold;");

        clearBtn.getStyleClass().add("btn-clear");
        clearBtn.setPrefWidth(120);
        clearBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #fb8500, #f77f00); " +
                         "-fx-text-fill: white; -fx-background-radius: 8; -fx-font-weight: bold;");

        refreshBtn.getStyleClass().add("btn-refresh");
        refreshBtn.setPrefWidth(130);
        refreshBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #7209b7, #560bad); " +
                           "-fx-text-fill: white; -fx-background-radius: 8; -fx-font-weight: bold;");

        HBox buttonBox = new HBox(12, createBtn, updateBtn, deleteBtn, clearBtn, refreshBtn);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.setPadding(new Insets(15, 0, 0, 0));

        form.add(buttonBox, 0, 3, 2, 1);

        // ============================================
        // CONTADOR DE REGISTROS
        // ============================================
        Label countLabel = new Label("📊 Total de registros: 0");
        countLabel.setStyle("-fx-text-fill: #adb5bd; -fx-font-size: 12px;");
        countLabel.setPadding(new Insets(5, 0, 0, 0));

        // Actualizar contador cuando cambien los datos
        data.addListener((javafx.collections.ListChangeListener<Person>) c -> {
            countLabel.setText("📊 Total de registros: " + data.size());
        });

        form.add(countLabel, 0, 4, 2, 1);

        // ============================================
        // LAYOUT PRINCIPAL
        // ============================================
        root.setTop(titleContainer);
        root.setCenter(tableBox);
        root.setBottom(form);
    }

    private void bindEvents() {
        // Selección en tabla -> rellenar formulario
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                nameField.setText(newSel.getName());
                emailField.setText(newSel.getEmail());
                ageField.setText(String.valueOf(newSel.getAge()));
            }
        });

        createBtn.setOnAction(e -> onCreate());
        updateBtn.setOnAction(e -> onUpdate());
        deleteBtn.setOnAction(e -> onDelete());
        clearBtn.setOnAction(e -> clearForm());
        refreshBtn.setOnAction(e -> loadData());
    }

    private void loadData() {
        try {
            data.setAll(service.readAll());
        } catch (Exception ex) {
            showError("Error al cargar datos", ex.getMessage());
        }
    }

    private void onCreate() {
        try {
            String name = readNonEmpty(nameField.getText(), "El nombre es obligatorio");
            String email = readValidEmail(emailField.getText());
            int age = readIntMin(ageField.getText(), 0, "La edad debe ser un entero >= 0");

            Person p = new Person(null, name, email, age);
            service.create(p);
            loadData();
            clearForm();
            showInfo("Creado", "La persona fue creada correctamente.");
        } catch (IllegalArgumentException ex) {
            showWarn("Validación", ex.getMessage());
        } catch (Exception ex) {
            showError("Error al crear", ex.getMessage());
        }
    }

    private void onUpdate() {
        Person selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarn("Seleccion requerida", "Seleccione una persona de la tabla para actualizar.");
            return;
        }

        try {
            String name = readNonEmpty(nameField.getText(), "El nombre es obligatorio");
            String email = readValidEmail(emailField.getText());
            int age = readIntMin(ageField.getText(), 0, "La edad debe ser un entero >= 0");

            Person updated = new Person(selected.getId(), name, email, age);
            service.update(updated);
            loadData();
            reselectById(selected.getId());
            showInfo("Actualizado", "La persona fue actualizada correctamente.");
        } catch (IllegalArgumentException ex) {
            showWarn("Validación", ex.getMessage());
        } catch (Exception ex) {
            showError("Error al actualizar", ex.getMessage());
        }
    }

    private void onDelete() {
        Person selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarn("Seleccion requerida", "Seleccione una persona de la tabla para eliminar.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmación");
        confirm.setHeaderText("Eliminar persona");
        confirm.setContentText("¿Está seguro de eliminar a: " + selected.getName() + " (ID " + selected.getId() + ")?");

        Optional<ButtonType> res = confirm.showAndWait();
        if (res.isPresent() && res.get() == ButtonType.OK) {
            try {
                boolean ok = service.delete(selected.getId());
                if (ok) {
                    loadData();
                    clearForm();
                    showInfo("Eliminado", "La persona fue eliminada.");
                } else {
                    showWarn("No eliminado", "No se pudo eliminar el registro.");
                }
            } catch (Exception ex) {
                showError("Error al eliminar", ex.getMessage());
            }
        }
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        ageField.clear();
        table.getSelectionModel().clearSelection();
        nameField.requestFocus();
    }

    private void reselectById(Long id) {
        if (id == null) return;
        for (Person p : table.getItems()) {
            if (id.equals(p.getId())) {
                table.getSelectionModel().select(p);
                table.scrollTo(p);
                break;
            }
        }
    }

    // Validaciones
    private String readNonEmpty(String value, String messageIfEmpty) {
        String v = value == null ? "" : value.trim();
        if (v.isEmpty()) throw new IllegalArgumentException(messageIfEmpty);
        return v;
    }

    private String readValidEmail(String email) {
        String v = email == null ? "" : email.trim();
        String regex = "^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$";
        if (!v.matches(regex)) {
            throw new IllegalArgumentException("Email inválido. Ej: usuario@dominio.com");
        }
        return v;
    }

    private int readIntMin(String value, int min, String message) {
        try {
            int parsed = Integer.parseInt(value.trim());
            if (parsed < min) throw new IllegalArgumentException(message);
            return parsed;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(message);
        }
    }

    // Alerts
    private void showInfo(String header, String content) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Información");
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }

    private void showWarn(String header, String content) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Advertencia");
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }

    private void showError(String header, String content) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }
}
