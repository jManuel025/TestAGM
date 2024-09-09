# Prueba consumo API

La aplicación permite visualizar personajes de la serie "Rick and Morty", cargarlos desde una API, mostrarlos en una lista y guardarlos en una base de datos local.

## Instrucciones para la compilación y ejecución del proyecto.

### 1. Clona el Repositorio

1. Abre Android Studio.
2. En la pantalla de bienvenida, selecciona **Get from Version Control**. Si ya tienes un proyecto abierto, ve a **File** > **New** > **Project from Version Control**.
3. En el diálogo que aparece, selecciona **Git** en el menú desplegable.
4. Ingresa la URL del repositorio (https://github.com/jManuel025/TestAGM.git)
5. Selecciona el directorio donde deseas clonar el repositorio en el campo **Directory**.
6. Haz clic en **Clone**.

### 2. Abre el Proyecto

1. Una vez que se haya clonado el repositorio, Android Studio abrirá automáticamente el proyecto.
2. Si el proyecto no se abre automáticamente, puedes ir a **File** > **Open** y seleccionar la carpeta del proyecto que acabas de clonar.

### 3. Sincroniza el Proyecto

1. Android Studio debería detectar automáticamente el archivo `build.gradle` y solicitarte sincronizar el proyecto. Si no lo hace, ve a **File** > **Sync Project with Gradle Files**.
2. Asegúrate de que todas las dependencias y configuraciones se descarguen correctamente.

### 4. Configura el Emulador o Conecta un Dispositivo

1. Si aún no tienes un emulador configurado, ve a **Tools** > **AVD Manager** para crear uno.
2. Alternativamente, conecta un dispositivo físico a tu computadora con la depuración USB habilitada.

### 5. Construye y Ejecuta el Proyecto

1. En la barra de herramientas superior, selecciona el dispositivo o emulador en el que deseas ejecutar la aplicación.
2. Haz clic en el botón de **Run** para construir y ejecutar el proyecto.

## Solución

La aplicación usa las siguientes tecnologías.

1. **Jetpack Compose**: Utilizado para construir la interfaz de usuario de manera declarativa y reactiva. Permite una implementación limpia y mantenible de las pantallas y los componentes de la UI.

2. **Hilt**: Proporciona una solución para la inyección de dependencias basada en Dagger.

3. **Realm**: Empleado para la persistencia de datos local.

4. **Retrofit**: Utilizado para la comunicación con la API remota.

5. **Coil**: Empleado para la carga y visualización de imágenes.

6. **Coroutines y Flow**: Utilizados para manejar operaciones asíncronas y flujos de datos reactivos.

### Arquitectura

- **ViewModel**: Se utiliza `CharactersViewModel` para gestionar el estado de la UI y manejar la lógica de negocio.
- **Repository Pattern**: `CharactersRepository` actúa como un intermediario entre la capa de datos (RemoteDataSource y LocalDataSource) y la capa de presentación (ViewModel).
- **Data Sources**: `RemoteDataSource` y `LocalDataSource` facilita la gestión de datos.

### UI y Composición

- **State Management**: El estado de la UI se maneja a través de `StateFlow` en los ViewModels, y las actualizaciones de estado se reflejan automáticamente en la UI gracias a la integración con Compose.

### Manejo de Errores

- **Placeholder States**: Se utilizan `PlaceholderState` para mostrar mensajes y estados adecuados cuando hay errores o cuando no hay datos disponibles.
- **Result Wrapper**: Se emplea una clase sellada `Result` para manejar los diferentes estados de las operaciones (éxito, error, carga). Para proporcionar un enfoque uniforme para el manejo de respuestas tanto desde la API como desde la base de datos local.

### Experiencia de Usuario

- **Cargar más datos**: La aplicación carga datos de manera paginada a medida que el usuario se desplaza por la lista.
- **Guardar personajes**: Los usuarios pueden guardar personajes en una base de datos local.
