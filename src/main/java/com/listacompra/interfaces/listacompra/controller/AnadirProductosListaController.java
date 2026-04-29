package com.listacompra.interfaces.listacompra.controller;

import com.listacompra.interfaces.listacompra.model.ListaCompra;
import com.listacompra.interfaces.listacompra.model.Producto;
import com.listacompra.interfaces.listacompra.model.Usuario;
import com.listacompra.interfaces.listacompra.service.ItemListaService;
import com.listacompra.interfaces.listacompra.service.ListaCompraService;
import com.listacompra.interfaces.listacompra.service.ProductoService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class AnadirProductosListaController {

    // ComboBox donde se mostrarán las listas de compra del usuario autenticado
    @FXML
    private ComboBox<ListaCompra> cmbListas;

    // ComboBox donde se mostrarán los productos disponibles
    @FXML
    private ComboBox<Producto> cmbProductos;

    // Campo donde el usuario introduce la cantidad del producto
    @FXML
    private TextField txtCantidad;

    // Botón para añadir el producto seleccionado a la lista seleccionada
    @FXML
    private Button btnAnadir;

    // Botón para limpiar el formulario.
    @FXML
    private Button btnLimpiar;

    // Label para mostrar mensajes de éxito o error.
    @FXML
    private Label lblMensaje;

    // Usuario que ha iniciado sesión, se utiliza para cear y mostrar solo las listas del usuario
    private Usuario usuarioAutenticado;

    // Recibe el usuario autenticado desde MainController
    public void setUsuarioAutenticado(Usuario usuarioAutenticado){

        this.usuarioAutenticado = usuarioAutenticado;

        cargarDatosFormulario();

    }

    // Mé_todo que se ejecuta automáticamente al cargar la vista FXML
    @FXML
    private void initialize() {

        // Configuramos cómo se mostrarán los objetos dentro de los ComboBox
        configurarComboBoxes();
    }

    // Carga las listas del usuario autenticado y los productos disponibles en los ComboBox
    private void cargarDatosFormulario() {

        // Comprobamos que exista un usuario autenticado
        if (usuarioAutenticado == null) {

            // Mostramos mensaje de error si no hay usuario autenticado
            lblMensaje.setText("No hay usuario autenticado.");
            lblMensaje.getStyleClass().removeAll("mensaje-exito");
            lblMensaje.getStyleClass().add("mensaje-error");
            return;
        }

        // Creamos los servicios necesarios
        ListaCompraService listaCompraService = new ListaCompraService();
        ProductoService productoService = new ProductoService();

        // Cargamos en el combo las listas del usuario autenticado
        cmbListas.setItems(
                FXCollections.observableArrayList(
                        listaCompraService.obtenerListasPorDni(usuarioAutenticado.getDni())
                )
        );

        // Cargamos en el combo todos los productos disponibles
        cmbProductos.setItems(
                FXCollections.observableArrayList(
                        productoService.listarProductos()
                )
        );
    }

    // Configura cómo se muestran las listas y los productos dentro de los ComboBox
    private void configurarComboBoxes() {

        // Indicamos cómo debe mostrarse cada ListaCompra dentro del ComboBox
        // En lugar de mostrar to_do el objeto, mostramos solo el nombre de la lista
        cmbListas.setConverter(new StringConverter<ListaCompra>() {

            @Override
            public String toString(ListaCompra lista) {

                // Si la lista es null, devolvemos texto vacío para evitar errores.
                if (lista == null) {
                    return "";
                }

                // Mostramos solo el nombre de la lista en el ComboBox
                return lista.getNombreCompra();
            }

            @Override
            public ListaCompra fromString(String string) {

                // No necesitamos convertir texto a ListaCompra manualmente
                return null;
            }
        });

        // Indicamos cómo debe mostrarse cada Producto dentro del ComboBox
        // En lugar de mostrar to_do el objeto, mostramos solo el nombre del producto
        cmbProductos.setConverter(new StringConverter<Producto>() {

            @Override
            public String toString(Producto producto) {

                // Si el producto es null, devolvemos texto vacío para evitar errores
                if (producto == null) {
                    return "";
                }

                // Mostramos solo el nombre del producto en el ComboBox
                return producto.getNombre();
            }

            @Override
            public Producto fromString(String string) {

                // No necesitamos convertir texto a Producto manualmente
                return null;
            }
        });
    }

    @FXML
    private void onAnadirProductoLista() {

        // Obtenemos la lista seleccionada en el ComboBox
        ListaCompra listaSeleccionada = cmbListas.getSelectionModel().getSelectedItem();

        // Obtenemos el producto seleccionado en el ComboBox
        Producto productoSeleccionado = cmbProductos.getSelectionModel().getSelectedItem();

        // Validamos que se haya seleccionado una lista
        if (listaSeleccionada == null) {

            // Mostramos mensaje de error
            lblMensaje.setText("Selecciona una lista.");

            // Quitamos estilo de éxito si estaba aplicado
            lblMensaje.getStyleClass().removeAll("mensaje-exito");

            // Aplicamos estilo de error
            lblMensaje.getStyleClass().add("mensaje-error");

            return;
        }

        // Validamos que se haya seleccionado un producto
        if (productoSeleccionado == null) {

            // Mostramos mensaje de error
            lblMensaje.setText("Selecciona un producto.");

            // Quitamos estilo de éxito si estaba aplicado
            lblMensaje.getStyleClass().removeAll("mensaje-exito");

            // Aplicamos estilo de error
            lblMensaje.getStyleClass().add("mensaje-error");

            // Cortamos la ejecución.
            return;
        }

        // Variable donde guardaremos la cantidad convertida a número entero.
        int cantidad;

        // Intentamos convertir el texto de cantidad a int.
        try {

            cantidad = Integer.parseInt(txtCantidad.getText());

        } catch (NumberFormatException e) {

            // Si el usuario escribe texto o deja el campo vacío, mostramos error.
            lblMensaje.setText("La cantidad debe ser un número entero.");

            // Quitamos estilo de éxito si estaba aplicado.
            lblMensaje.getStyleClass().removeAll("mensaje-exito");

            // Aplicamos estilo de error.
            lblMensaje.getStyleClass().add("mensaje-error");

            // Cortamos la ejecución.
            return;
        }

        // Creamos el servicio encargado de añadir productos a listas.
        ItemListaService itemListaService = new ItemListaService();

        // Añadimos el producto seleccionado a la lista seleccionada.
        String resultado = itemListaService.anadirProductoALista(
                listaSeleccionada.getIdLista(),
                productoSeleccionado.getIdProducto(),
                cantidad
        );

        // Si el servicio devuelve OK, la operación fue correcta.
        if (resultado.equals("OK")) {

            // Mostramos mensaje de éxito.
            lblMensaje.setText("Producto añadido a la lista correctamente.");

            // Quitamos estilo de error si estaba aplicado.
            lblMensaje.getStyleClass().removeAll("mensaje-error");

            // Aplicamos estilo de éxito.
            lblMensaje.getStyleClass().add("mensaje-exito");

            // Limpiamos solo la cantidad para poder seguir añadiendo productos rápidamente.
            txtCantidad.clear();

        } else {

            // Si el servicio devuelve otro texto, mostramos el error.
            lblMensaje.setText(resultado);

            // Quitamos estilo de éxito si estaba aplicado.
            lblMensaje.getStyleClass().removeAll("mensaje-exito");

            // Aplicamos estilo de error.
            lblMensaje.getStyleClass().add("mensaje-error");
        }
    }

    @FXML
    private void onLimpiarFormulario() {

    }
}
