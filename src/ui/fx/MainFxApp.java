package ui.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFxApp extends Application {

    static {
        // Suprimir warnings de CSS de JavaFX
        Logger.getLogger("javafx.scene.CssStyleHelper").setLevel(Level.SEVERE);
        Logger.getLogger("com.sun.javafx.css.StyleManager").setLevel(Level.SEVERE);
    }

    @Override
    public void start(Stage primaryStage) {
        PersonCrudView view = new PersonCrudView();

        Scene scene = new Scene(view.getRoot(), 1000, 700);

        // Intentar cargar el archivo CSS con múltiples métodos
        boolean cssLoaded = false;

        // Método 1: Recurso relativo a la clase
        try {
            URL cssUrl = getClass().getResource("styles.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
                cssLoaded = true;
                System.out.println("✅ CSS cargado correctamente desde recurso de clase");
            }
        } catch (Exception e) {
            System.err.println("⚠️ Método 1 falló: " + e.getMessage());
        }

        // Método 2: Recurso desde ClassLoader
        if (!cssLoaded) {
            try {
                URL cssUrl = Thread.currentThread().getContextClassLoader()
                    .getResource("ui/fx/styles.css");
                if (cssUrl != null) {
                    scene.getStylesheets().add(cssUrl.toExternalForm());
                    cssLoaded = true;
                    System.out.println("✅ CSS cargado correctamente desde ClassLoader");
                }
            } catch (Exception e) {
                System.err.println("⚠️ Método 2 falló: " + e.getMessage());
            }
        }

        // Método 3: Archivo externo (si existe en la raíz del proyecto)
        if (!cssLoaded) {
            try {
                String cssPath = "file:src/ui/fx/styles.css";
                scene.getStylesheets().add(cssPath);
                cssLoaded = true;
                System.out.println("✅ CSS cargado correctamente desde archivo externo");
            } catch (Exception e) {
                System.err.println("⚠️ Método 3 falló: " + e.getMessage());
            }
        }

        // Si no se pudo cargar CSS, aplicar estilos básicos inline
        if (!cssLoaded) {
            System.err.println("⚠️ No se pudo cargar el archivo CSS. Usando estilos básicos.");
            applyInlineStyles(scene);
        }

        // Configurar la ventana
        primaryStage.setTitle("📊 CRUD de Personas - Sistema Moderno");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(650);

        // Centrar en pantalla
        primaryStage.centerOnScreen();

        primaryStage.show();
    }

    /**
     * Aplica estilos básicos inline como respaldo si no se carga el CSS
     */
    private void applyInlineStyles(Scene scene) {
        String inlineStyle = 
            ".root { -fx-background-color: #2b2d42; }" +
            ".button { -fx-background-color: #495057; -fx-text-fill: white; " +
            "          -fx-background-radius: 5; -fx-padding: 8 15; }" +
            ".button:hover { -fx-background-color: #5a6268; }" +
            ".text-field { -fx-background-color: #1a1b2e; -fx-text-fill: white; " +
            "              -fx-border-color: #3d4663; -fx-border-radius: 5; }" +
            ".table-view { -fx-background-color: #2b2d42; }" +
            ".table-row-cell { -fx-background-color: #2b2d42; }" +
            ".table-row-cell:odd { -fx-background-color: #343750; }" +
            ".table-row-cell:selected { -fx-background-color: #00b4d8; }" +
            ".label { -fx-text-fill: #ffffff; }";

        scene.getRoot().setStyle(inlineStyle);
    }
}
