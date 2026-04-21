package com.listacompra.interfaces.listacompra.dao;

import com.listacompra.interfaces.listacompra.database.DatabaseConnection;
import com.listacompra.interfaces.listacompra.model.ItemLista;
import com.listacompra.interfaces.listacompra.model.ListaCompra;
import com.listacompra.interfaces.listacompra.model.Producto;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemListaDao {

    //Mé_todo para insertar un item en una lista de compra
    public static void insertarItemLista(ItemLista item){

        //Query SQL para insertar el item
        String sql = "INSERT INTO itemslista(idProducto, idLista, cantidad, precioUnitario, comprado) " +
                "VALUES (?, ?, ?, ?, ?)";

        try{
            //Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            //Se prepara la consulta
            PreparedStatement ps = connection.prepareStatement(sql);

            //Se asignan los parámetros
            ps.setInt(1, item.getProducto().getIdProducto());
            ps.setInt(2, item.getListaCompra().getIdLista());
            ps.setInt(3, item.getCantidad());
            ps.setBigDecimal(4, item.getPrecioUnitario());
            ps.setBoolean(5, item.isComprado());

            //Se ejecuta la inserción
            ps.executeUpdate();

            System.out.println("Artículo añadido a la lista.");

            //Cierre de recursos
            ps.close();
            connection.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static List<ItemLista> listarItemsPorLista(int idLista){

        List<ItemLista> items = new ArrayList<>();

        String sql = "SELECT il.idItem, il.idLista, il.cantidad, il.precioUnitario, il.comprado, " +
                "p.idProducto, p.nombre, p.precio, p.categoria " +
                "FROM itemslista il " +
                "JOIN productos p ON il.idProducto = p.idProducto " +
                "WHERE il.idLista = ?";

        try{
            Connection connection = DatabaseConnection.conectar();

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, idLista);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                // Se crea el producto con los datos obtenidos del JOIN
                Producto producto = new Producto(
                        rs.getInt("idProducto"),
                        rs.getString("nombre"),
                        rs.getBigDecimal("precio"),
                        rs.getString("categoria")
                );

                // Se crea una lista de compra mínima usando solo el id
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

                items.add(item);
            }

            //Cierre de recursos
            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            //Muestra el error en caso de fallo
            e.printStackTrace();
        }

        //Devuelve la lista con los items
        return items;
    }

    //Mé_todo para marcar un item de la lista como comprado
    public static void marcarComoComprado(int idItem){

        //Query SQL que actualiza el estado del item a comprado (true)
        String sql = "UPDATE itemslista SET comprado = true WHERE idItem = ?";

        try{
            //Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            //Se prepara la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql);

            //Se asigna el id del item al parámetro de la query
            ps.setInt(1, idItem);

            //Se ejecuta la actualización y se obtiene el número de filas afectadas
            int filas = ps.executeUpdate();

            //Se comprueba si el item se ha actualizado correctamente
            if (filas > 0){
                System.out.println("Item marcado como comprado.");
            } else {
                System.out.println("No se encontró el item.");
            }

            //Cierre de recursos
            ps.close();
            connection.close();

        } catch (SQLException e) {
            //Muestra el error en caso de fallo
            e.printStackTrace();
        }
    }

    //Mé_todo para calcular el precio total de una lista de compra
    public static BigDecimal calcularPrecioTotalLista(int idLista){

        //Variable donde se guardará el total
        BigDecimal total = BigDecimal.ZERO;

        //Query SQL para sumar el total de la lista
        String sql = "SELECT SUM(cantidad * precioUnitario) AS total " +
                "FROM itemslista " +
                "WHERE idLista = ?";

        try{
            //Se obtiene la conexión a la base de datos
            Connection connection = DatabaseConnection.conectar();

            //Se prepara la consulta
            PreparedStatement ps = connection.prepareStatement(sql);

            //Se asigna el id de la lista
            ps.setInt(1, idLista);

            //Se ejecuta la consulta
            ResultSet rs = ps.executeQuery();

            //Si hay resultado, se obtiene el total
            if(rs.next()){
                total = rs.getBigDecimal("total");

                //Si la lista está vacía puede devolver null
                if (total == null){
                    total = BigDecimal.ZERO;
                }
            }

            //Cierre de recursos
            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Se devuelve el total calculado
        return total;
    }
}
