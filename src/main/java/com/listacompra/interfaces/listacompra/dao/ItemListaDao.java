package com.listacompra.interfaces.listacompra.dao;

import com.listacompra.interfaces.listacompra.database.DatabaseConnection;
import com.listacompra.interfaces.listacompra.model.ItemLista;
import com.listacompra.interfaces.listacompra.model.ListaCompra;
import com.listacompra.interfaces.listacompra.model.Producto;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Clase DAO encargada de gestionar los productos añadidos a las listas de compra
public class ItemListaDao {

    // Mé_todo para insertar un item en una lista de compra
    public static void insertarItemLista(ItemLista item) {

        // Query SQL para insertar el item
        String sql = "INSERT INTO itemslista(idProducto, idLista, cantidad, precioUnitario, comprado) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            // Se asignan los parámetros del item
            ps.setInt(1, item.getProducto().getIdProducto());
            ps.setInt(2, item.getListaCompra().getIdLista());
            ps.setInt(3, item.getCantidad());
            ps.setBigDecimal(4, item.getPrecioUnitario());
            ps.setBoolean(5, item.isComprado());

            // Se ejecuta la inserción
            ps.executeUpdate();

            // Se cierran los recursos
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mé_todo para listar todos los items de una lista concreta
    public static List<ItemLista> listarItemsPorLista(int idLista) {

        // Lista donde se almacenan los items recuperados
        List<ItemLista> items = new ArrayList<>();

        // Query SQL con JOIN para obtener los items y sus productos asociados
        String sql = "SELECT il.idItem, il.idLista, il.cantidad, il.precioUnitario, il.comprado, " +
                "p.idProducto, p.nombre, p.precio, p.categoria " +
                "FROM itemslista il " +
                "JOIN productos p ON il.idProducto = p.idProducto " +
                "WHERE il.idLista = ?";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            // Se asigna el ID de la lista
            ps.setInt(1, idLista);

            // Se ejecuta la consulta
            ResultSet rs = ps.executeQuery();

            // Se recorren los items encontrados
            while (rs.next()) {

                // Se crea el producto asociado al item
                Producto producto = new Producto(
                        rs.getInt("idProducto"),
                        rs.getString("nombre"),
                        rs.getBigDecimal("precio"),
                        rs.getString("categoria")
                );

                // Se crea una lista de compra mínima usando solo el ID
                ListaCompra listaCompra = new ListaCompra();
                listaCompra.setIdLista(rs.getInt("idLista"));

                // Se crea el item de la lista
                ItemLista item = new ItemLista(
                        rs.getInt("idItem"),
                        producto,
                        listaCompra,
                        rs.getInt("cantidad"),
                        rs.getBigDecimal("precioUnitario"),
                        rs.getBoolean("comprado")
                );

                // Se añade el item a la lista
                items.add(item);
            }

            // Se cierran los recursos
            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Se devuelve la lista con los items
        return items;
    }

    // Mé_todo para marcar un item de la lista como comprado
    public static void marcarComoComprado(int idItem) {

        // Query SQL que actualiza el estado del item a comprado
        String sql = "UPDATE itemslista SET comprado = true WHERE idItem = ?";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            // Se asigna el ID del item al parámetro de la query
            ps.setInt(1, idItem);

            // Se ejecuta la actualización
            ps.executeUpdate();

            // Se cierran los recursos
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mé_todo para calcular el precio total de una lista de compra
    public static BigDecimal calcularPrecioTotalLista(int idLista) {

        // Variable donde se guarda el total calculado
        BigDecimal total = BigDecimal.ZERO;

        // Query SQL para sumar el total de la lista
        String sql = "SELECT SUM(cantidad * precioUnitario) AS total " +
                "FROM itemslista " +
                "WHERE idLista = ?";

        try {
            // Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            // Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            // Se asigna el ID de la lista
            ps.setInt(1, idLista);

            // Se ejecuta la consulta
            ResultSet rs = ps.executeQuery();

            // Si hay resultado, se obtiene el total
            if (rs.next()) {
                total = rs.getBigDecimal("total");

                // Si la lista está vacía, SUM puede devolver null
                if (total == null) {
                    total = BigDecimal.ZERO;
                }
            }

            // Se cierran los recursos
            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Se devuelve el total calculado
        return total;
    }
}