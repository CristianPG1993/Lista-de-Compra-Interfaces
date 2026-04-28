package com.listacompra.interfaces.listacompra.controller;


import com.listacompra.interfaces.listacompra.model.Producto;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;

public class ProductosController {

    // Campo para introducir el nombre del producto
    @FXML
    private TextField txtNombre;

    // Campo para introducir el precio del producto
    @FXML
    private TextField txtPrecio;

    // Campo para introducir la categoria del producto
    @FXML
    private TextField txtCategoria;

    // Tabla donde se mostrarán los productos registrados
    @FXML
    private TableView<Producto> tablaProductos;

    // Columna para mostrar el ID del producto.
    @FXML
    private TableColumn<Producto, Integer> colId;

    // Columna para mostrar el nombre del producto.
    @FXML
    private TableColumn<Producto, String> colNombre;

    // Columna para mostrar el precio del producto.
    @FXML
    private TableColumn<Producto, BigDecimal> colPrecio;

    // Columna para mostrar la categoría del producto.
    @FXML
    private TableColumn<Producto, String> colCategoria;

    // Botón para añadir un producto.
    @FXML
    private Button btnAnadir;

    // Botón para limpiar el formulario.
    @FXML
    private Button btnLimpiar;

    // Botón para eliminar el producto seleccionado.
    @FXML
    private Button btnEliminar;

    // Label para mostrar mensajes de éxito o error.
    @FXML
    private Label lblMensaje;

    @FXML
    public void onAnadirProducto() {
    }

    @FXML
    public void onLimpiarFormulario() {
    }

    @FXML
    public void onEliminarProducto() {
    }
}
