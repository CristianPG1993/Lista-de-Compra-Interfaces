package com.listacompra.interfaces.listacompra.dao;

import com.listacompra.interfaces.listacompra.database.DatabaseConnection;
import com.listacompra.interfaces.listacompra.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Clase DAO encargada de realizar operaciones CRUD sobre la tabla productos
public class ProductoDao {

    // Mé_todo para insertar un producto en la base de datos
    public static void insertarProducto(Producto producto) {

        // Query SQL para insertar un nuevo producto
        String sql = "INSERT INTO productos(nombre, precio, categoria) VALUES (?, ?, ?)";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            // Se asignan los valores del objeto Producto a los parámetros de la query
            ps.setString(1, producto.getNombre());
            ps.setBigDecimal(2, producto.getPrecio());
            ps.setString(3, producto.getCategoria());

            // Se ejecuta la inserción
            ps.executeUpdate();

            // Se cierran los recursos
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mé_todo para listar todos los productos de la base de datos
    public static List<Producto> listarProductos() {

        // Lista donde se almacenarán todos los productos recuperados
        List<Producto> productos = new ArrayList<>();

        // Query SQL para obtener todos los productos
        String sql = "SELECT idProducto, nombre, precio, categoria FROM productos";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se crea un Statement para ejecutar la consulta
            Statement stmt = connection.createStatement();

            // Se ejecuta la query y se obtiene el resultado
            ResultSet rs = stmt.executeQuery(sql);

            // Se recorren todas las filas devueltas por la consulta
            while (rs.next()) {

                // Se crea un objeto Producto con los datos de cada fila
                Producto producto = new Producto(
                        rs.getInt("idProducto"),
                        rs.getString("nombre"),
                        rs.getBigDecimal("precio"),
                        rs.getString("categoria")
                );

                // Se añade el producto a la lista
                productos.add(producto);
            }

            // Se cierran los recursos
            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Se devuelve la lista completa de productos
        return productos;
    }

    // Mé_todo para buscar un producto por su ID
    public static Producto buscarProductoPorId(int idProducto) {

        // Producto que se devolverá, null si no se encuentra
        Producto producto = null;

        // Query SQL para buscar un producto concreto por ID
        String sql = "SELECT idProducto, nombre, precio, categoria " +
                "FROM productos " +
                "WHERE idProducto = ?";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            // Se asigna el ID al parámetro de la query
            ps.setInt(1, idProducto);

            // Se ejecuta la consulta
            ResultSet rs = ps.executeQuery();

            // Si se encuentra una fila, se crea el objeto Producto
            if (rs.next()) {
                producto = new Producto(
                        rs.getInt("idProducto"),
                        rs.getString("nombre"),
                        rs.getBigDecimal("precio"),
                        rs.getString("categoria")
                );
            }

            // Se cierran los recursos
            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Se devuelve el producto encontrado o null
        return producto;
    }

    // Mé_todo para actualizar un producto existente en la base de datos
    public static void actualizarProducto(Producto producto) {

        // Query SQL para actualizar los datos de un producto
        String sql = "UPDATE productos " +
                "SET nombre = ?, precio = ?, categoria = ? " +
                "WHERE idProducto = ?";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            // Se asignan los nuevos valores del producto
            ps.setString(1, producto.getNombre());
            ps.setBigDecimal(2, producto.getPrecio());
            ps.setString(3, producto.getCategoria());
            ps.setInt(4, producto.getIdProducto());

            // Se ejecuta la actualización
            ps.executeUpdate();

            // Se cierran los recursos
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mé_todo para eliminar un producto de la base de datos por su ID
    public static void eliminarProducto(int idProducto) {

        // Query SQL para eliminar el producto
        String sql = "DELETE FROM productos WHERE idProducto = ?";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            // Se asigna el ID del producto al parámetro de la query
            ps.setInt(1, idProducto);

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