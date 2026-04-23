package com.listacompra.interfaces.listacompra.service;

import com.listacompra.interfaces.listacompra.dao.UsuarioDao;
import com.listacompra.interfaces.listacompra.model.Usuario;

// Clase que implementa la lógica de negocio relacionada con usuarios.
// Se encarga de validar los datos introducidos y delegar las operaciones al DAO.
public class UsuarioService {

    // Mé_todo encargado de autenticar a un usuario en el sistema.
    // Recibe el DNI y la contraseña introducidos en el login.
    // Devuelve un objeto Usuario si la autenticación es correcta o null si falla.
    public Usuario autenticarUsuario(String dni, String password) {

        // Validamos que el DNI no sea nulo ni esté vacío.
        // Si no hay DNI, no se puede realizar la autenticación.
        if (dni == null || dni.isEmpty()) {
            return null;
        }

        // Validamos que la contraseña no sea nula ni esté vacía.
        // Sin contraseña no es posible verificar la identidad del usuario.
        if (password == null || password.isEmpty()) {
            return null;
        }

        // Normalizamos el DNI eliminando espacios en blanco y convirtiéndolo a mayúsculas.
        // Esto evita errores si el usuario introduce el DNI con espacios o en minúsculas.
        dni = dni.trim().toUpperCase();

        // Buscamos el usuario en la base de datos a través del DAO usando el DNI.
        Usuario usuario = UsuarioDao.buscarUsuarioPorDni(dni);

        // Si no existe ningún usuario con ese DNI, la autenticación falla.
        if (usuario == null) {
            return null;
        }

        // Comprobamos si la contraseña introducida coincide con la almacenada.
        // Si no coincide, se considera un intento de login inválido.
        if (!usuario.getPassword().equals(password)) {
            return null;
        }

        // Si todas las comprobaciones son correctas, devolvemos el usuario autenticado.
        return usuario;
    }

    // Mé_todo que crea un nuevo usuario en la base de datos
    // Incluye validaciones de formato y duplicidad
    public void crearUsuario(String dni, String nombre, String apellido, String email, String password) {

        // Validar que el DNI no esté vacío
        if (dni.isEmpty()) {
            System.out.println("El DNI no puede estar vacío.");
            return;
        }

        // Validar formato del DNI (8 números + letra)
        if (!dni.matches("^[0-9]{8}[a-zA-Z]$")) {
            System.out.println("Formato de DNI no válido.");
            return;
        }

        // Comprobar que no exista ya un usuario con ese DNI
        if (UsuarioDao.buscarUsuarioPorDni(dni) != null) {
            System.out.println("Ya existe un usuario con ese DNI.");
            return;
        }

        // Validar nombre (solo letras y espacios)
        if (nombre.isEmpty() || !nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            System.out.println("Nombre no válido.");
            return;
        }

        // Validar apellido (solo letras y espacios)
        if (apellido.isEmpty() || !apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            System.out.println("Apellido no válido.");
            return;
        }

        // Validar formato de email
        if (email.isEmpty() || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            System.out.println("Email no válido.");
            return;
        }

        // Validar contraseña (mínimo 6 caracteres y al menos un número)
        if (password.length() < 6 || !password.matches(".*\\d.*")) {
            System.out.println("Password no válida.");
            return;
        }

        // Crear objeto Usuario con los datos validados
        Usuario usuario = new Usuario(dni, nombre, apellido, email, password);

        // Insertar el usuario en la base de datos
        UsuarioDao.insertarUsuario(usuario);

        // Mostrar mensaje de confirmación
        System.out.println("Usuario creado correctamente.");
    }

    // Mé_todo que actualiza los datos de un usuario existente
    // Se identifica al usuario mediante su DNI
    public void actualizarUsuario(String dni, String nombre, String apellido, String email, String password) {

        // Buscar usuario en la base de datos
        Usuario usuario = UsuarioDao.buscarUsuarioPorDni(dni);

        // Si no existe, cancelar operación
        if (usuario == null) {
            System.out.println("No se encontró ningún usuario con ese DNI.");
            return;
        }

        // Actualizar los campos del usuario
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setPassword(password);

        // Guardar cambios en la base de datos
        UsuarioDao.actualizarUsuario(usuario);
    }

    // Mé_todo que elimina un usuario a partir de su DNI
    public void eliminarUsuario(String dni) {

        // Buscar usuario en la base de datos
        Usuario usuario = UsuarioDao.buscarUsuarioPorDni(dni);

        // Si no existe, cancelar operación
        if (usuario == null) {
            System.out.println("No se encontró ningún usuario con ese DNI.");
            return;
        }

        // Eliminar usuario utilizando su ID
        UsuarioDao.eliminarUsuario(usuario.getId());
    }
}