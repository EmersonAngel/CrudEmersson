import ui.ConsoleMenu;
import ui.fx.MainFxApp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    static {
        // Suprimir warnings de JavaFX sobre configuración de módulos
        System.setProperty("javafx.verbose", "false");
        Logger.getLogger("javafx").setLevel(Level.SEVERE);
        Logger.getLogger("com.sun.javafx").setLevel(Level.SEVERE);
    }

    public static void main(String[] args) {
        boolean gui = args != null && args.length > 0 && "gui".equalsIgnoreCase(args[0]);

        if (gui) {
            try {
                System.out.println("🚀 Iniciando interfaz gráfica JavaFX...");
                javafx.application.Application.launch(MainFxApp.class, args);
            } catch (Exception e) {
                System.err.println("❌ No se pudo iniciar JavaFX: " + e.getMessage());
                System.err.println("Asegúrate de tener JavaFX configurado correctamente.");
            }
        } else {
            System.out.println("🚀 Iniciando aplicación CRUD con serialización (consola)...\n");
            System.out.println("💡 Tip: Usa el argumento 'gui' para abrir la interfaz gráfica.\n");
            ConsoleMenu menu = new ConsoleMenu();
            menu.start();
        }
    }
}