package com.listacompra.interfaces.listacompra.service;

import com.listacompra.interfaces.listacompra.dao.ProductoDao;
import com.listacompra.interfaces.listacompra.model.Producto;

import java.math.BigDecimal;
import java.util.List;

// Clase que contiene la lógica de negocio relacionada con productos
public class ProductoService {

    // Mé_todo que crea un nuevo producto en la base de datos
    // Devuelve un String para que la interfaz gráfica pueda mostrar el resultado
    public String crearProducto(String nombre, BigDecimal precio, String categoria) {

        // Validamos que el nombre no sea nulo ni esté vacío
        if (nombre == null || nombre.isEmpty()) {
            return "El nombre no puede estar vacío";
        }

        // Validamos que el precio no sea nulo
        if (precio == null) {
            return "El precio no puede estar vacío";
        }

        // Validamos que el precio sea mayor que cero
        if (precio.compareTo(BigDecimal.ZERO) <= 0) {
            return "El precio debe ser mayor que 0";
        }

        // Validamos que la categoría no sea nula ni esté vacía
        if (categoria == null || categoria.isEmpty()) {
            return "La categoría no puede estar vacía";
        }

        // Creamos el objeto Producto con los datos validados
        Producto producto = new Producto(nombre, precio, categoria);

        // Insertamos el producto en la base de datos
        ProductoDao.insertarProducto(producto);

        // Devolvemos OK para indicar que la operación fue correcta
        return "OK";
    }

    // Mé_todo que devuelve todos los productos almacenados en la base de datos
    public List<Producto> listarProductos() {

        // Delegamos la consulta al DAO
        return ProductoDao.listarProductos();
    }

    // Mé_todo que elimina un producto usando directamente su ID
    public String eliminarProductoPorId(int idProducto) {

        // Validamos que el ID recibido sea válido
        if (idProducto <= 0) {
            return "Producto no válido";
        }

        // Buscamos el producto en la base de datos para comprobar que existe
        Producto producto = ProductoDao.buscarProductoPorId(idProducto);

        // Si no existe ningún producto con ese ID, devolvemos mensaje de error
        if (producto == null) {
            return "No se encontró el producto";
        }

        // Eliminamos el producto usando el DAO
        ProductoDao.eliminarProducto(idProducto);

        // Devolvemos OK para que el controlador pueda mostrar mensaje de éxito
        return "OK";
    }
}