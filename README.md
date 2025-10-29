# BiblioNet Android App

## 📖 Descripción

**BiblioNet** es una interfaz funcional de aplicación móvil desarrollada para Android usando Kotlin y Jetpack Compose. Funciona como un cliente de gestión de biblioteca, permitiendo a los usuarios explorar un catálogo de libros, gestionar préstamos y realizar reservas. [cite_start]Este proyecto fue creado como parte de la **Evaluación Parcial 2** para la asignatura **DSY1105, Desarrollo de Aplicaciones Móviles**. [cite: 5, 6, 8, 12]

---

## 👥 Integrantes

* [cite_start]Bryan Caniullán [cite: 55]
* [cite_start]Leopoldo Payacán (Leo) [cite: 56]

---

## ✨ Características Principales

* [cite_start]**Autenticación de Usuario:** Inicio de sesión seguro para usuarios registrados. [cite: 63]
* [cite_start]**Catálogo de Libros:** Muestra una lista de libros con portadas (cargadas con Coil), títulos y autores. [cite: 65]
* [cite_start]**Detalles del Libro:** Muestra información detallada de un libro seleccionado, incluyendo género, ISBN y estado de disponibilidad. [cite: 67]
* **Sistema de Préstamos:** Permite a los usuarios solicitar en préstamo libros disponibles. [cite_start]El estado del libro se actualiza. [cite: 68]
* [cite_start]**Sistema de Reservas:** Permite a los usuarios reservar libros que están actualmente prestados. [cite: 69] Incluye reglas de negocio:
    * Solo una reserva por libro específico por usuario.
    * Máximo 5 reservas totales por usuario.
* [cite_start]**Mis Préstamos:** Pantalla dedicada que muestra los libros actualmente prestados por el usuario. [cite: 70] Incluye funcionalidad para devolver libros.
* [cite_start]**Mis Reservas:** Pantalla dedicada que muestra las reservas activas del usuario. [cite: 71] Incluye funcionalidad para cancelar reservas.
* [cite_start]**Integración de Recursos Nativos:** Utiliza la **Cámara** y el **Micrófono** del dispositivo. [cite: 33]
* [cite_start]**Mejoras de UI:** Incluye transiciones de pantalla con *fade-in/fade-out* para una navegación más fluida. [cite: 30]

---

## 🛠️ Stack Tecnológico

* **IDE:** Android Studio
* **Lenguaje:** Kotlin
* **UI Toolkit:** Jetpack Compose
* [cite_start]**Arquitectura:** MVVM (Model-View-ViewModel) [cite: 31]
* [cite_start]**Base de Datos:** Room Persistence Library (SQLite) [cite: 31]
* **Operaciones Asíncronas:** Kotlin Coroutines & Flow
* **Navegación:** Navigation Compose (con Accompanist Navigation Animation)
* **Carga de Imágenes:** Coil
* [cite_start]**Control de Versiones:** Git & GitHub [cite: 32]
* [cite_start]**Gestión de Proyecto:** JIRA [cite: 32, 41]

---

## 🏗️ Estructura del Proyecto

[cite_start]El proyecto sigue el patrón arquitectónico MVVM para una clara separación de responsabilidades: [cite: 31]

* **`data`:** Contiene las entidades de Room, DAOs y clases relacionadas con la base de datos.
* **`ui`:** Contiene los elementos de UI de Jetpack Compose (Pantallas, Composables), organizados por funcionalidad (ej. `home`, `booklist`, `bookdetail`, `myloans`, `myreservations`). Incluye un sub-paquete `theme`.
* **`viewModel`:** Contiene los ViewModels responsables de manejar el estado de la UI e interactuar con la capa de datos.

---

## 🚀 Configuración y Ejecución

Sigue estos pasos para configurar y ejecutar el proyecto en tu entorno local.

### Prerrequisitos

* Tener instalado **Android Studio** (versión recomendada Iguana o superior).
* Tener **Git** instalado.

### Pasos

1.  **Clona el repositorio:**
    ```bash
    git clone <url-del-repositorio>
    ```
2.  **Abre el proyecto en Android Studio:**
    * Inicia Android Studio.
    * Selecciona **"Open"** (Abrir).
    * Navega hasta la carpeta del proyecto clonado y selecciónala.
3.  **Sincroniza Gradle:** Espera a que Android Studio descargue las dependencias y sincronice el proyecto.
4.  **Ejecuta la App:** Selecciona un emulador o conecta un dispositivo físico y haz clic en el botón 'Run' (▶️).

*(Nota: La aplicación incluye datos de prueba temporales (Usuario, Autores, Libros) que se insertan en el primer inicio si la base de datos está vacía.)*
