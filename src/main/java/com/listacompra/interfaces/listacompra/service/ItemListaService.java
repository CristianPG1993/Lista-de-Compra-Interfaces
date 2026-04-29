package com.listacompra.interfaces.listacompra.service;

import com.listacompra.interfaces.listacompra.dao.ItemListaDao;
import com.listacompra.interfaces.listacompra.dao.ListaCompraDao;
import com.listacompra.interfaces.listacompra.dao.ProductoDao;
import com.listacompra.interfaces.listacompra.model.ItemLista;
import com.listacompra.interfaces.listacompra.model.ListaCompra;
import com.listacompra.interfaces.listacompra.model.Producto;

import java.math.BigDecimal;
import java.util.List;

public class ItemListaService {

    // Mé_todo que añade un producto a una lista de compra.
    // Recibe directamente el id de la lista, el id del producto y la cantidad.
    public String anadirProductoALista(int idLista, int idProducto, int cantidad) {

        // Validamos que la lista seleccionada sea válida
        if (idLista <= 0) {
            return "Selecciona una lista válida.";
        }

        // Validamos que el producto seleccionado sea válido
        if (idProducto <= 0) {
            return "Selecciona un producto válido.";
        }

        // Validamos que la cantidad sea mayor que cero
        if (cantidad <= 0) {
            return "La cantidad debe ser mayor que 0.";
        }

        // Buscamos la lista en la base de datos
        ListaCompra listaCompra = ListaCompraDao.buscarListaCompraPorId(idLista);

        // Si no existe la lista, devolvemos error
        if (listaCompra == null) {
            return "No se encontró la lista seleccionada.";
        }

        // Buscamos el producto en la base de datos
        Producto producto = ProductoDao.buscarProductoPorId(idProducto);

        // Si no existe el producto, devolvemos error
        if (producto == null) {
            return "No se encontró el producto seleccionado.";
        }

        // Creamos el item que se añadirá a la lista
        ItemLista itemLista = new ItemLista(
                producto,
                listaCompra,
                cantidad,
                producto.getPrecio(),
                false
        );

        // Insertamos el item en la base de datos
        ItemListaDao.insertarItemLista(itemLista);

        // Devolvemos OK para que JavaFX pueda mostrar mensaje de éxito
        return "OK";
    }

    // Devuelve todos los items/productos asociados a una lista de compra
    public List<ItemLista> listarItemsPorLista(int idLista) {

        // Validamos que el ID de la lista sea correcto
        if (idLista <= 0) {
            return List.of();
        }

        // Delegamos la consulta al DAO
        return ItemListaDao.listarItemsPorLista(idLista);
    }

    // Calcula el precio total de una lista de compra
    public BigDecimal calcularTotalLista(int idLista) {

        // Validamos que el ID de la lista sea correcto
        if (idLista <= 0) {
            return BigDecimal.ZERO;
        }

        // Delegamos el cálculo al DAO
        return ItemListaDao.calcularPrecioTotalLista(idLista);
    }
}