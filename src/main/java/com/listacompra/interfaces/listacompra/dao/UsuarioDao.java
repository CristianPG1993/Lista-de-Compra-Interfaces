package com.listacompra.interfaces.listacompra.dao;

import com.listacompra.interfaces.listacompra.database.DatabaseConnection;
import com.listacompra.interfaces.listacompra.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Clase DAO encargada de realizar operaciones CRUD sobre la tabla usuarios
public class UsuarioDao {

    // Mé_todo para añadir un usuario nuevo a la base de datos
    public static void insertarUsuario(Usuario usuario) {

        // Query SQL para insertar un usuario usando parámetros seguros
        String sql = "INSERT INTO usuarios(dni, nombre, apellido, email, password) VALUES (?, ?, ?, ?, ?)";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            // Se asignan los valores del objeto Usuario a los parámetros de la query
            ps.setString(1, usuario.getDni());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getPassword());

            // Se ejecuta la inserción en la base de datos
            ps.executeUpdate();

            // Se cierran los recursos
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mé_todo para listar todos los usuarios de la base de datos
    public static List<Usuario> listarUsuarios() {

        // Lista donde se almacenarán los usuarios recuperados de la base de datos
        List<Usuario> usuarios = new ArrayList<>();

        // Query SQL para obtener todos los usuarios
        String sql = "SELECT id, dni, nombre, apellido, email, password FROM usuarios";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se crea la consulta
            Statement stmt = connection.createStatement();

            // Se ejecuta la consulta
            ResultSet rs = stmt.executeQuery(sql);

            // Se recorren todos los usuarios encontrados
            while (rs.next()) {

                // Se crea un objeto Usuario con los datos de cada fila
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("password")
                );

                // Se añade el usuario a la lista
                usuarios.add(usuario);
            }

            // Se cierran los recursos
            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Se devuelve la lista de usuarios
        return usuarios;
    }

    // Mé_todo para buscar un usuario por ID
    public static Usuario buscarUsuarioPorId(int id) {

        // Usuario que se devolverá, null si no se encuentra
        Usuario usuario = null;

        // Query SQL para buscar un usuario por ID
        String sql = "SELECT id, dni, nombre, apellido, email, password FROM usuarios WHERE id = ?";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            // Se asigna el ID recibido al parámetro de la query
            ps.setInt(1, id);

            // Se ejecuta la consulta
            ResultSet rs = ps.executeQuery();

            // Si existe un usuario con ese ID, se crea el objeto Usuario
            if (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }

            // Se cierran los recursos
            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Se devuelve el usuario encontrado o null si no existe
        return usuario;
    }

    // Mé_todo para buscar un usuario por DNI
    public static Usuario buscarUsuarioPorDni(String dni) {

        // Usuario que se devolverá, null si no se encuentra
        Usuario usuario = null;

        // Query SQL para buscar un usuario por DNI
        String sql = "SELECT id, dni, nombre, apellido, email, password FROM usuarios WHERE dni = ?";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            // Se asigna el DNI al parámetro de la query
            ps.setString(1, dni);

            // Se ejecuta la consulta
            ResultSet rs = ps.executeQuery();

            // Si existe un usuario con ese DNI, se crea el objeto Usuario
            if (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }

            // Se cierran los recursos
            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Se devuelve el usuario encontrado o null si no existe
        return usuario;
    }

    // Mé_todo para buscar un usuario por email
    public static Usuario buscarUsuarioPorEmail(String email) {

        // Usuario que se devolverá, null si no se encuentra
        Usuario usuario = null;

        // Query SQL para buscar un usuario por email
        String sql = "SELECT id, dni, nombre, apellido, email, password FROM usuarios WHERE email = ?";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            // Se asigna el email al parámetro de la query
            ps.setString(1, email);

            // Se ejecuta la consulta
            ResultSet rs = ps.executeQuery();

            // Si existe un usuario con ese email, se crea el objeto Usuario
            if (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }

            // Se cierran los recursos
            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Se devuelve el usuario encontrado o null si no existe
        return usuario;
    }

    // Mé_todo para actualizar un usuario existente
    public static void actualizarUsuario(Usuario usuario) {

        // Query SQL para actualizar los datos del usuario
        String sql = "UPDATE usuarios SET dni = ?, nombre = ?, apellido = ?, email = ?, password = ? WHERE id = ?";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            // Se asignan los nuevos valores del usuario
            ps.setString(1, usuario.getDni());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getPassword());
            ps.setInt(6, usuario.getId());

            // Se ejecuta la actualización
            ps.executeUpdate();

            // Se cierran los recursos
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mé_todo para eliminar un usuario por ID
    public static void eliminarUsuario(int id) {

        // Query SQL para eliminar un usuario
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            // Se asigna el ID al parámetro de la query
            ps.setInt(1, id);

            // Se ejecuta la eliminación
            ps.executeUpdate();

            // Se cierran los recursos
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}