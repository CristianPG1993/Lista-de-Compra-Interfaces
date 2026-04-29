package com.listacompra.interfaces.listacompra.dao;

import com.listacompra.interfaces.listacompra.database.DatabaseConnection;
import com.listacompra.interfaces.listacompra.model.ListaCompra;
import com.listacompra.interfaces.listacompra.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Clase DAO encargada de realizar operaciones CRUD sobre la tabla listascompra
public class ListaCompraDao {

    // Mé_todo para insertar una lista de compra en la base de datos
    public static void insertarListaCompra(ListaCompra listaCompra) {

        // Query SQL para insertar una nueva lista de compra
        String sql = "INSERT INTO listascompra (idUsuario, nombreCompra, fechaCreacion) VALUES (?, ?, ?)";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            // Se asigna el id del usuario asociado a la lista
            ps.setInt(1, listaCompra.getUsuario().getId());

            // Se asigna el nombre de la lista
            ps.setString(2, listaCompra.getNombreCompra());

            // Se convierte LocalDate a java.sql.Date
            ps.setDate(3, Date.valueOf(listaCompra.getFechaCreacion()));

            // Se ejecuta la inserción
            ps.executeUpdate();

            // Se cierran los recursos
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mé_todo para listar todas las listas de compra de la base de datos
    public static List<ListaCompra> listarListasCompras() {

        // Lista donde se guardan todas las listas obtenidas de la base de datos
        List<ListaCompra> listas = new ArrayList<>();

        // Query SQL con JOIN para obtener las listas y su usuario asociado
        String sql = "SELECT lc.idLista, lc.nombreCompra, lc.fechaCreacion, " +
                "u.id, u.dni, u.nombre, u.apellido, u.email, u.password " +
                "FROM listascompra lc " +
                "JOIN usuarios u ON lc.idUsuario = u.id";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se crea la consulta SQL
            Statement stmt = connection.createStatement();

            // Se ejecuta la consulta
            ResultSet rs = stmt.executeQuery(sql);

            // Se recorren todas las filas devueltas por la consulta
            while (rs.next()) {

                // Se crea el objeto Usuario con los datos obtenidos del JOIN
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("password")
                );

                // Se crea el objeto ListaCompra con los datos obtenidos
                ListaCompra listaCompra = new ListaCompra(
                        rs.getInt("idLista"),
                        usuario,
                        rs.getString("nombreCompra"),
                        rs.getDate("fechaCreacion").toLocalDate()
                );

                // Se añade la lista a la colección
                listas.add(listaCompra);
            }

            // Se cierran los recursos
            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Se devuelve la lista completa de listas de compra
        return listas;
    }

    // Mé_todo para listar todas las listas de compra de un usuario concreto
    public static List<ListaCompra> listarListasPorUsuario(int idUsuario) {

        // Lista donde se almacenan las listas obtenidas de la base de datos
        List<ListaCompra> listas = new ArrayList<>();

        // Query SQL con JOIN para obtener listas y datos del usuario
        String sql = "SELECT lc.idLista, lc.nombreCompra, lc.fechaCreacion, " +
                "u.id, u.dni, u.nombre, u.apellido, u.email, u.password " +
                "FROM listascompra lc " +
                "JOIN usuarios u ON lc.idUsuario = u.id " +
                "WHERE u.id = ?";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            // Se asigna el id del usuario al parámetro de la query
            ps.setInt(1, idUsuario);

            // Se ejecuta la consulta
            ResultSet rs = ps.executeQuery();

            // Se recorren todas las filas del resultado
            while (rs.next()) {

                // Se crea el objeto Usuario con los datos obtenidos del JOIN
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("password")
                );

                // Se crea el objeto ListaCompra con los datos obtenidos
                ListaCompra listaCompra = new ListaCompra(
                        rs.getInt("idLista"),
                        usuario,
                        rs.getString("nombreCompra"),
                        rs.getDate("fechaCreacion").toLocalDate()
                );

                // Se añade la lista a la colección
                listas.add(listaCompra);
            }

            // Se cierran los recursos
            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Se devuelve la lista de listas de compra del usuario
        return listas;
    }

    // Mé_todo para buscar una lista de compra por su ID
    public static ListaCompra buscarListaCompraPorId(int idLista) {

        // Objeto que se devolverá, null si no se encuentra la lista
        ListaCompra listaCompra = null;

        // Query SQL con JOIN para obtener la lista y su usuario asociado
        String sql = "SELECT lc.idLista, lc.nombreCompra, lc.fechaCreacion, " +
                "u.id, u.dni, u.nombre, u.apellido, u.email, u.password " +
                "FROM listascompra lc " +
                "JOIN usuarios u ON lc.idUsuario = u.id " +
                "WHERE lc.idLista = ?";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            // Se asigna el id de la lista al parámetro de la query
            ps.setInt(1, idLista);

            // Se ejecuta la consulta
            ResultSet rs = ps.executeQuery();

            // Si se encuentra una fila, se crea el objeto ListaCompra
            if (rs.next()) {

                // Se crea el objeto Usuario con los datos obtenidos del JOIN
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("password")
                );

                // Se crea el objeto ListaCompra con los datos obtenidos
                listaCompra = new ListaCompra(
                        rs.getInt("idLista"),
                        usuario,
                        rs.getString("nombreCompra"),
                        rs.getDate("fechaCreacion").toLocalDate()
                );
            }

            // Se cierran los recursos
            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Se devuelve la lista encontrada o null si no existe
        return listaCompra;
    }

    // Mé_todo para actualizar una lista de compra
    public static void actualizarListaCompra(ListaCompra listaCompra) {

        // Query SQL para actualizar la lista
        String sql = "UPDATE listascompra " +
                "SET idUsuario = ?, nombreCompra = ?, fechaCreacion = ? " +
                "WHERE idLista = ?";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            // Se asignan los parámetros
            ps.setInt(1, listaCompra.getUsuario().getId());
            ps.setString(2, listaCompra.getNombreCompra());
            ps.setDate(3, Date.valueOf(listaCompra.getFechaCreacion()));
            ps.setInt(4, listaCompra.getIdLista());

            // Se ejecuta la actualización
            ps.executeUpdate();

            // Se cierran los recursos
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mé_todo para eliminar una lista de compra por su ID
    public static void eliminarListaCompra(int idLista) {

        // Query SQL para eliminar la lista
        String sql = "DELETE FROM listascompra WHERE idLista = ?";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            // Se asigna el id de la lista
            ps.setInt(1, idLista);

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