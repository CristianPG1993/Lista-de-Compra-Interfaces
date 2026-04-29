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

        // Asociamos la lista observable a la tabla
        tablaProductos.setItems(productosObservable);
    }

    @FXML
    public void onAnadirProducto() {

        // Creamos una instancia del servicio de productos.
        ProductoService productoService = new ProductoService();

        // Variable donde guardermos el precio convertido a BigDecimal.
        BigDecimal precio;

        // Intentamos convertir el texto del campo precio a BigDecimal
        try{
            precio = new BigDecimal(txtPrecio.getText());

        } catch (NumberFormatException e){

            // Mostramos mensaje de error si el precio no es un número válido.
            lblMensaje.setText("El precio debe ser un número válido.");

            // Quitamos estilo de éxito si estaba aplicado.
            lblMensaje.getStyleClass().removeAll("mensaje-exito");

            // Aplicamos estilo de error.
            lblMensaje.getStyleClass().add("mensaje-error");

            // Cortamos la ejecución porque no podemos crear el producto sin precio válido.
            return;
        }

        // Llamamos al servicio para crear el producto con los datos del formulario
        String resultado = productoService.crearProducto(
                txtNombre.getText(),
                precio,
                txtCategoria.getText()
        );

        // Si el resultado del servicio devuelve OK, el producto se ha creado correctamente
        if(resultado.equals("OK")){

            // Mostramos mensaje de éxito.
            lblMensaje.setText("Producto añadido correctamente.");

            // Quitamos estilo de error si estaba aplicado.
            lblMensaje.getStyleClass().removeAll("mensaje-error");

            // Aplicamos estilo de éxito.
            lblMensaje.getStyleClass().add("mensaje-exito");

            // Limpiamos los campos del formulario.
            txtNombre.clear();
            txtPrecio.clear();
            txtCategoria.clear();

            // Recargamos la tabla para mostrar el nuevo producto.
            cargarProductos();

        } else {

            // Si el servicio devuelve otro texto, es un mensaje de validación.
            lblMensaje.setText(resultado);

            // Quitamos estilo de éxito si estaba aplicado.
            lblMensaje.getStyleClass().removeAll("mensaje-exito");

            // Aplicamos estilo de error.
            lblMensaje.getStyleClass().add("mensaje-error");
        }
    }

    @FXML
    public void onLimpiarFormulario() {

        // Limpia el campo del nombre del producto
        txtNombre.clear();

        // Limpia el campo del precio del producto
        txtPrecio.clear();

        // Limpia el campo de la categoria del producto
        txtCategoria.clear();

        // Limpia cualquier mensaje mostrado anteriormente
        lblMensaje.setText("");

        // Elimina los estilos de éxito o error del mensaje
        lblMensaje.getStyleClass().removeAll("mensaje-exito", "mensaje-error");

        // Devuelve el foco al primer campo del formulario
        txtNombre.requestFocus();;
    }

    @FXML
    public void onEliminarProducto() {

        // Obtenemos el producto seleccionado actualmente en la tabla
        Producto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();

        // Si no hay ningún producto seleccionado, mostramos error
        if (productoSeleccionado == null) {

            // Mostramos mensaje de error
            lblMensaje.setText("Selecciona un producto para eliminar.");

            // Quitamos estilo de éxito si estaba aplicado
            lblMensaje.getStyleClass().removeAll("mensaje-exito");

            // Aplicamos estilo de error
            lblMensaje.getStyleClass().add("mensaje-error");

            // Cortamos la ejecución porque no hay producto seleccionado
            return;
        }

        // Creamos una instancia del servicio de productos
        ProductoService productoService = new ProductoService();

        String resultado = productoService.eliminarProductoPorId(productoSeleccionado.getIdProducto());

        // Si el servicio devuelve OK, la eliminación ha sido correcta
        if (resultado.equals("OK")) {

            // Mostramos mensaje de éxito
            lblMensaje.setText("Producto eliminado correctamente.");

            // Quitamos estilo de error si estaba aplicado
            lblMensaje.getStyleClass().removeAll("mensaje-error");

            // Aplicamos estilo de éxito.
            lblMensaje.getStyleClass().add("mensaje-exito");

            // Recargamos la tabla para que desaparezca el producto eliminado
            cargarProductos();

        } else {

            // Si el servicio devuelve otro texto, es un error de validación o búsqueda
            lblMensaje.setText(resultado);

            // Quitamos estilo de éxito si estaba aplicado
            lblMensaje.getStyleClass().removeAll("mensaje-exito");

            // Aplicamos estilo de error.
            lblMensaje.getStyleClass().add("mensaje-error");
        }
    }
}
