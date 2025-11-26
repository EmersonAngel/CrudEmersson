# CRUD con Archivos Serializados - Java 21 + JavaFX

Sistema CRUD completo con persistencia mediante serialización de archivos, interfaz de consola y JavaFX.

## 🚀 Características

- ✅ CRUD completo (Create, Read, Update, Delete)
- ✅ Persistencia con archivos serializados (.dat)
- ✅ Arquitectura en capas (Model, Repository, Service, UI)
- ✅ Uso de genéricos para extensibilidad
- ✅ Validaciones completas con try/catch
- ✅ Dos interfaces: Consola y JavaFX
- ✅ Gestión de dependencias con Maven

## 📦 Requisitos

- **JDK 21** o superior
- **Maven** 3.6+ (opcional pero recomendado)
- **IntelliJ IDEA** 2023.2+ (o cualquier IDE compatible)

---

## 🛠️ Configuración del Proyecto en IntelliJ IDEA

### **Método 1: Con Maven (RECOMENDADO)**

1. **Abrir el proyecto como proyecto Maven:**
   - `File > Open` → Selecciona la carpeta del proyecto
   - IntelliJ detectará automáticamente el `pom.xml`
   - Click derecho en `pom.xml` → `Maven > Reload Project`

2. **Esperar a que Maven descargue las dependencias:**
   - Verás en la barra inferior: "Downloading dependencies..."
   - JavaFX se descargará automáticamente

3. **Configurar la ejecución:**
   - `Run > Edit Configurations...`
   - Click en `+` → `Application`
   - **Name**: `Main - Consola`
   - **Main class**: `Main`
   - **JRE**: 21
   - Click `Apply`

4. **Crear configuración para GUI:**
   - Duplica la configuración anterior
   - **Name**: `Main - GUI JavaFX`
   - **Program arguments**: `gui`
   - Click `Apply` y `OK`

5. **Ejecutar:**
   - **Consola**: Selecciona `Main - Consola` y ejecuta
   - **GUI**: Selecciona `Main - GUI JavaFX` y ejecuta

---

### **Método 2: Configuración Manual (Sin Maven)**

Si prefieres no usar Maven:

1. **Descargar JavaFX SDK:**
   - Ve a [openjfx.io](https://openjfx.io/)
   - Descarga **JavaFX 21 SDK** para tu sistema operativo
   - Extrae el archivo (ejemplo: `javafx-sdk-21`)

2. **Agregar JavaFX a las librerías del proyecto:**
   - `File > Project Structure` (Ctrl+Alt+Shift+S)
   - Ve a `Libraries` → Click en `+` → `Java`
   - Navega a la carpeta `lib` dentro de `javafx-sdk-21`
   - Selecciona todos los `.jar` y click `OK`
   - Click `Apply`

3. **Configurar VM Options:**
   - `Run > Edit Configurations...`
   - Selecciona tu configuración `Main`
   - En **VM options**, agrega:
     ```
     --module-path "RUTA_COMPLETA/javafx-sdk-21/lib" --add-modules javafx.controls,javafx.fxml
     ```
   - **Ejemplo en Windows:**
     ```
     --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml
     ```
   - **Ejemplo en Linux/Mac:**
     ```
     --module-path "/home/usuario/javafx-sdk-21/lib" --add-modules javafx.controls,javafx.fxml
     ```

4. **Program arguments** (para GUI): `gui`

---

## 🎯 Cómo Ejecutar

### **Modo Consola:**
