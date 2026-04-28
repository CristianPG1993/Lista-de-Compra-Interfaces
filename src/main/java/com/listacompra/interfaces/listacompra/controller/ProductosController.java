package com.listacompra.interfaces.listacompra.controller;


import com.listacompra.interfaces.listacompra.model.Producto;
import com.listacompra.interfaces.listacompra.service.ProductoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

    //Lista observable que conectará los productos con la tabla JavaFX
    private ObservableList<Producto> productosObservable;

    @FXML
    private void initialize(){

        // Asociamos cada columna de la tabla con una propiedad del modelo Producto
        colId.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        // Cargamos los productos existentes al abrir la vista
        cargarProductos();
    }

    private void cargarProductos(){

        // Creamos una instancia del servicio Productos
        ProductoService productoService = new ProductoService();

        // Convertimos la lista normal del servicio en una lista observable para JavaFX
        productosObservable = FXCollections.observableArrayList(productoService.listarProductos());

        // Mostramos en consola cuántos productos llegan desde la base de datos.
        System.out.println("Productos cargados: " + productosObservable.size());

        // Asociamos la lista observable a la tabla
        tablaProductos.setItems(productosObservable);
    }

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
