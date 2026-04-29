package com.listacompra.interfaces.listacompra.controller;

import com.listacompra.interfaces.listacompra.model.Producto;
import com.listacompra.interfaces.listacompra.service.ProductoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;

// Controlador encargado de gestionar el módulo de productos
public class ProductosController {

    // Campo para introducir el nombre del producto
    @FXML
    private TextField txtNombre;

    // Campo para introducir el precio del producto
    @FXML
    private TextField txtPrecio;

    // Campo para introducir la categoría del producto
    @FXML
    private TextField txtCategoria;

    // Tabla donde se muestran los productos registrados
    @FXML
    private TableView<Producto> tablaProductos;

    // Columna para mostrar el ID del producto
    @FXML
    private TableColumn<Producto, Integer> colId;

    // Columna para mostrar el nombre del producto
    @FXML
    private TableColumn<Producto, String> colNombre;

    // Columna para mostrar el precio del producto
    @FXML
    private TableColumn<Producto, BigDecimal> colPrecio;

    // Columna para mostrar la categoría del producto
    @FXML
    private TableColumn<Producto, String> colCategoria;

    // Label para mostrar mensajes de éxito o error
    @FXML
    private Label lblMensaje;

    // Lista observable que conecta los productos con la tabla JavaFX
    private ObservableList<Producto> productosObservable;

    // Mé_todo que se ejecuta al cargar la vista
    @FXML
    private void initialize() {

        // Asociamos cada columna de la tabla con una propiedad del modelo Producto
        colId.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        // Cargamos los productos existentes al abrir la vista
        cargarProductos();
    }

    // Carga los productos desde el servicio y los muestra en la tabla
    private void cargarProductos() {

        // Creamos una instancia del servicio de productos
        ProductoService productoService = new ProductoService();

        // Convertimos la lista normal del servicio en una lista observable para JavaFX
        productosObservable = FXCollections.observableArrayList(productoService.listarProductos());

        // Asociamos la lista observable a la tabla
        tablaProductos.setItems(productosObservable);
    }

    // Añade un nuevo producto usando los datos del formulario
    @FXML
    private void onAnadirProducto() {

        // Creamos una instancia del servicio de productos
        ProductoService productoService = new ProductoService();

        // Variable donde guardaremos el precio convertido a BigDecimal
        BigDecimal precio;

        // Intentamos convertir el texto del campo precio a BigDecimal
        try {
            precio = new BigDecimal(txtPrecio.getText());

        } catch (NumberFormatException e) {

            // Mostramos mensaje de error si el precio no es válido
            mostrarMensaje("El precio debe ser un número válido", false);
            return;
        }

        // Llamamos al servicio para crear el producto con los datos del formulario
        String resultado = productoService.crearProducto(
                txtNombre.getText(),
                precio,
                txtCategoria.getText()
        );

        // Si el servicio devuelve OK, el producto se ha creado correctamente
        if (resultado.equals("OK")) {

            // Mostramos mensaje de éxito
            mostrarMensaje("Producto añadido correctamente", true);

            // Limpiamos los campos del formulario
            txtNombre.clear();
            txtPrecio.clear();
            txtCategoria.clear();

            // Recargamos la tabla para mostrar el nuevo producto
            cargarProductos();

        } else {

            // Si el servicio devuelve otro texto, es un mensaje de validación
            mostrarMensaje(resultado, false);
        }
    }

    // Limpia los campos del formulario de productos
    @FXML
    private void onLimpiarFormulario() {

        // Limpiamos los campos del formulario
        txtNombre.clear();
        txtPrecio.clear();
        txtCategoria.clear();

        // Limpiamos el mensaje y sus estilos
        lblMensaje.setText("");
        lblMensaje.getStyleClass().removeAll("mensaje-exito", "mensaje-error");

        // Devolvemos el foco al campo nombre
        txtNombre.requestFocus();
    }

    // Elimina el producto seleccionado en la tabla
    @FXML
    private void onEliminarProducto() {

        // Obtenemos el producto seleccionado actualmente en la tabla
        Producto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();

        // Si no hay ningún producto seleccionado, mostramos error
        if (productoSeleccionado == null) {
            mostrarMensaje("Selecciona un producto para eliminar", false);
            return;
        }

        // Creamos una instancia del servicio de productos
        ProductoService productoService = new ProductoService();

        // Eliminamos el producto usando su ID real de base de datos
        String resultado = productoService.eliminarProductoPorId(productoSeleccionado.getIdProducto());

        // Si el servicio devuelve OK, la eliminación ha sido correcta
        if (resultado.equals("OK")) {

            // Mostramos mensaje de éxito
            mostrarMensaje("Producto eliminado correctamente", true);

            // Recargamos la tabla para que desaparezca el producto eliminado
            cargarProductos();

        } else {

            // Si el servicio devuelve otro texto, mostramos el error
            mostrarMensaje(resultado, false);
        }
    }

    // Mé_todo auxiliar para mostrar mensajes de éxito o error
    private void mostrarMensaje(String mensaje, boolean exito) {

        // Mostramos el texto recibido
        lblMensaje.setText(mensaje);

        // Eliminamos estilos anteriores
        lblMensaje.getStyleClass().removeAll("mensaje-exito", "mensaje-error");

        // Aplicamos el estilo correspondiente
        if (exito) {
            lblMensaje.getStyleClass().add("mensaje-exito");
        } else {
            lblMensaje.getStyleClass().add("mensaje-error");
        }
    }
}