# Lista de la Compra

Aplicación de escritorio para gestionar listas de la compra, desarrollada con JavaFX y PostgreSQL.

## Funcionalidades

- Registro e inicio de sesión de usuarios
- Crear y gestionar múltiples listas de la compra
- Catálogo de productos con nombre, precio y categoría
- Añadir productos a listas con cantidad
- Marcar productos como comprados

## Tecnologías

- **Java 17**
- **JavaFX 17.0.6** (interfaz gráfica con FXML y CSS)
- **PostgreSQL** (base de datos)
- **Maven** (gestión de dependencias)

## Requisitos previos

- Java 17 o superior
- PostgreSQL instalado y en ejecución
- Maven 3

## Configuración de la base de datos

1. Crear una base de datos llamada `listacompra` en PostgreSQL
2. Ejecutar el script `scriptDatos.sql` para crear las tablas y cargar datos de ejemplo
3. Configurar las credenciales en `DatabaseConnection.java` si es necesario (por defecto usa `localhost:5432`, usuario `postgres`)

## Ejecución

Desde el IDE, ejecutar directamente la clase `Launcher.java` como punto de entrada.

También se puede lanzar con Maven:

```bash
mvn clean javafx:run
```

## Arquitectura

El proyecto sigue el patrón **MVC** con tres capas bien definidas:

```
controller/   → Lógica de la interfaz (JavaFX)
service/      → Lógica de negocio
dao/          → Acceso a la base de datos
model/        → Entidades: Usuario, ListaCompra, Producto, ItemLista
```

## Flujo de uso

1. Iniciar la aplicación → pantalla de login
2. Registrarse o iniciar sesión con DNI y contraseña
3. Desde el menú principal: gestionar usuarios, listas y productos
4. Añadir productos a una lista y marcarlos como comprados al hacer la compra
