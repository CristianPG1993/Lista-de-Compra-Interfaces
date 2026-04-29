package com.listacompra.interfaces.listacompra.service;

import com.listacompra.interfaces.listacompra.dao.UsuarioDao;
import com.listacompra.interfaces.listacompra.model.Usuario;

// Clase que implementa la lógica de negocio relacionada con usuarios
// Se encarga de validar los datos introducidos y delegar las operaciones al DAO
public class UsuarioService {

    // Mé_todo encargado de autenticar a un usuario en el sistema
    // Recibe el DNI y la contraseña introducidos en el login
    // Devuelve un objeto Usuario si la autenticación es correcta o null si falla
    public Usuario autenticarUsuario(String dni, String password) {

        // Validamos que el DNI no sea nulo ni esté vacío
        if (dni == null || dni.isEmpty()) {
            return null;
        }

        // Validamos que la contraseña no sea nula ni esté vacía
        if (password == null || password.isEmpty()) {
            return null;
        }

        // Normalizamos el DNI eliminando espacios en blanco y convirtiéndolo a mayúsculas
        dni = dni.trim().toUpperCase();

        // Buscamos el usuario en la base de datos a través del DAO usando el DNI
        Usuario usuario = UsuarioDao.buscarUsuarioPorDni(dni);

        // Si no existe ningún usuario con ese DNI, la autenticación falla
        if (usuario == null) {
            return null;
        }

        // Comprobamos si la contraseña introducida coincide con la almacenada
        if (!usuario.getPassword().equals(password)) {
            return null;
        }

        // Si todas las comprobaciones son correctas, devolvemos el usuario autenticado
        return usuario;
    }

    // Mé_todo que crea un nuevo usuario en la base de datos
    // Incluye validaciones de formato y duplicidad
    public String crearUsuario(String dni, String nombre, String apellido, String email, String password) {

        // Validamos que el DNI no sea nulo ni esté vacío
        if (dni == null || dni.isEmpty()) {
            return "El DNI no puede estar vacío";
        }

        // Normalizamos el DNI antes de validar y buscar duplicados
        dni = dni.trim().toUpperCase();

        // Validamos formato del DNI
        if (!dni.matches("^[0-9]{8}[A-Z]$")) {
            return "Formato de DNI no válido";
        }

        // Comprobamos que no exista ya un usuario con ese DNI
        if (UsuarioDao.buscarUsuarioPorDni(dni) != null) {
            return "Ya existe un usuario con ese DNI";
        }

        // Validamos nombre
        if (nombre == null || nombre.isEmpty() || !nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            return "Nombre no válido";
        }

        // Validamos apellido
        if (apellido == null || apellido.isEmpty() || !apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            return "Apellido no válido";
        }

        // Validamos email
        if (email == null || email.isEmpty() || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return "Email no válido";
        }

        // Validamos contraseña
        if (password == null || password.length() < 6 || !password.matches(".*\\d.*")) {
            return "Password no válida";
        }

        // Creamos el objeto Usuario con los datos validados
        Usuario usuario = new Usuario(dni, nombre, apellido, email, password);

        // Insertamos el usuario en la base de datos
        UsuarioDao.insertarUsuario(usuario);

        // Devolvemos OK para que la interfaz muestre el mensaje de éxito
        return "OK";
    }

    // Mé_todo que actualiza los datos de un usuario existente
    // Se busca al usuario por DNI y, si existe, se validan los nuevos datos antes de guardar
    public String actualizarUsuario(String dni, String nombre, String apellido, String email, String password) {

        // Validamos que el DNI no esté vacío porque lo usamos para localizar al usuario
        if (dni == null || dni.isEmpty()) {
            return "El DNI no puede estar vacío";
        }

        // Normalizamos el DNI
        dni = dni.trim().toUpperCase();

        // Buscamos el usuario en la base de datos mediante el DNI
        Usuario usuario = UsuarioDao.buscarUsuarioPorDni(dni);

        // Si no existe ningún usuario con ese DNI, no podemos actualizar
        if (usuario == null) {
            return "No se encontró ningún usuario con ese DNI";
        }

        // Validamos el nombre
        if (nombre == null || nombre.isEmpty() || !nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            return "Nombre no válido";
        }

        // Validamos el apellido
        if (apellido == null || apellido.isEmpty() || !apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            return "Apellido no válido";
        }

        // Validamos el email
        if (email == null || email.isEmpty() || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return "Email no válido";
        }

        // Validamos la contraseña
        if (password == null || password.length() < 6 || !password.matches(".*\\d.*")) {
            return "Password no válida";
        }

        // Actualizamos los datos del objeto usuario recuperado de la base de datos
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setPassword(password);

        // Delegamos la actualización real al DAO
        UsuarioDao.actualizarUsuario(usuario);

        // Devolvemos OK para que la interfaz pueda mostrar un mensaje de éxito
        return "OK";
    }

    // Mé_todo que elimina un usuario a partir de su DNI
    // Devuelve un String para que la interfaz gráfica pueda mostrar un mensaje adecuado
    public String eliminarUsuario(String dni) {

        // Validamos que el DNI no sea nulo ni esté vacío
        if (dni == null || dni.isEmpty()) {
            return "El DNI no puede estar vacío";
        }

        // Normalizamos el DNI eliminando espacios y convirtiéndolo a mayúsculas
        dni = dni.trim().toUpperCase();

        // Buscamos el usuario en la base de datos usando el DNI
        Usuario usuario = UsuarioDao.buscarUsuarioPorDni(dni);

        // Si no existe ningún usuario con ese DNI, no se puede eliminar
        if (usuario == null) {
            return "No se encontró ningún usuario con ese DNI";
        }

        // Si existe, eliminamos el usuario usando su ID
        UsuarioDao.eliminarUsuario(usuario.getId());

        // Devolvemos OK para indicar a la interfaz que la operación ha sido correcta
        return "OK";
    }
}