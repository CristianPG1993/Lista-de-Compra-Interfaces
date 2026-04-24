package com.listacompra.interfaces.listacompra.dao;

import com.listacompra.interfaces.listacompra.database.DatabaseConnection;
import com.listacompra.interfaces.listacompra.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    //Mé_todo para añadir un usuario nuevo a la base de datos
    public static void insertarUsuario(Usuario usuario){

        // Query SQL para insertar un usuario (uso de ? para evitar SQL Injection)
        String sql = "INSERT INTO usuarios(dni, nombre, apellido, email, password) VALUES (?, ?, ?, ?, ?)";

        try{
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

            // Se cierran los recursos para evitar fugas de memoria
            ps.close();
            connection.close();

        } catch (SQLException e) {
            // Muestra el error completo para facilitar la depuración
            e.printStackTrace();
        }
    }

    // Mé_todo para listar todos los usuarios de la base de datos
    public static List<Usuario> listarUsuarios(){

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM usuarios";

        try{
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se crea la consulta
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Recorremos todos los usuarios
            while (rs.next()){

                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("password")
                );

                // Añadimos el usuario a la lista
                usuarios.add(usuario);
            }

            // Cerramos recursos
            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    //Me_todo para buscar el usuario por ID
    public static Usuario buscarUsuarioPorId(int id){

        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try{
            Connection connection = DatabaseConnection.conectar();

            PreparedStatement ps = connection.prepareStatement(sql);

            //Se pasa el id recibido al parámetro de la query
            ps.setInt(1, id);

            //Ejecutar SELECT correctamente
            ResultSet rs = ps.executeQuery();

            //Encuentra el usuario y lo devuelve
            if (rs.next()){

                usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("password")
                );

            }

            //Cerrar recursos
            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    //Mé_todo para buscar un usuario en la base de datos a partir de su DNI
    public static Usuario buscarUsuarioPorDni(String dni){

        //Objeto que se devolverá (null si no se encuentra el usuario)
        Usuario usuario = null;

        //Query SQL para buscar el usuario por DNI
        String sql = "SELECT * FROM usuarios WHERE dni = ?";

        try{
            //Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            //Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            //Se asigna el DNI al parámetro de la query
            ps.setString(1, dni);

            //Se ejecuta la consulta
            ResultSet rs = ps.executeQuery();

            //Si existe un usuario con ese DNI, se crea el objeto Usuario
            if(rs.next()){

                usuario = new Usuario(
                        rs.getInt("id"),          //id del usuario
                        rs.getString("dni"),      //dni del usuario
                        rs.getString("nombre"),   //nombre
                        rs.getString("apellido"), //apellido
                        rs.getString("email"),    //email
                        rs.getString("password")  //contraseña
                );
            }

            //Cierre de recursos para evitar fugas de memoria
            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            //Muestra el error en caso de fallo
            e.printStackTrace();
        }

        //Se devuelve el usuario encontrado o null si no existe
        return usuario;
    }

    //Mé_todo para actualizar el usuario
    public static void actualizarUsuario(Usuario usuario){

        String sql = "UPDATE usuarios SET dni = ?, nombre = ?, apellido = ?, email = ?, password = ? WHERE id = ?";

        try{
            Connection connection = DatabaseConnection.conectar();

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, usuario.getDni());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getPassword());
            ps.setInt(6, usuario.getId());


            int filas = ps.executeUpdate();

            //Comprobar si se ha actualizado correctamente el usuario.
            if (filas > 0){

            }else {
            }

            //Cierre de recursos
            ps.close();
            connection.close();


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Mé_todo para borrar un usuario por id
    public static void  eliminarUsuario(int id){
        // Query SQL para eliminar un usuario
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try{
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            // Se asigna el id al parámetro de la query
            ps.setInt(1, id);

            // Se ejecuta la eliminación y se obtiene el número de filas afectadas
            int filas = ps.executeUpdate();

            // Se comprueba si se ha eliminado algún usuario
            if(filas > 0){
                System.out.println("Usuario eliminado correctamente.");
            } else {
                System.out.println("No se ha encontrado el usuario.");
            }

            // Se cierran los recursos para evitar fugas de memoria
            ps.close();
            connection.close();

        } catch (SQLException e) {
            // Muestra el error completo para facilitar la depuración
            e.printStackTrace();
        }
    }
}
