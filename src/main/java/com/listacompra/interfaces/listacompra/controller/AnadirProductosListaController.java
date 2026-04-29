package com.listacompra.interfaces.listacompra.controller;

import com.listacompra.interfaces.listacompra.model.ListaCompra;
import com.listacompra.interfaces.listacompra.model.Producto;
import com.listacompra.interfaces.listacompra.model.Usuario;
import com.listacompra.interfaces.listacompra.service.ItemListaService;
import com.listacompra.interfaces.listacompra.service.ListaCompraService;
import com.listacompra.interfaces.listacompra.service.ProductoService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

// Controlador encargado de añadir productos a una lista de compra
public class AnadirProductosListaController {

    // ComboBox donde se muestran las listas de compra del usuario autenticado
    @FXML
    private ComboBox<ListaCompra> cmbListas;

    // ComboBox donde se muestran los productos disponibles
    @FXML
    private ComboBox<Producto> cmbProductos;

    // Campo donde el usuario introduce la cantidad del producto
    @FXML
    private TextField txtCantidad;

    // Label para mostrar mensajes de éxito o error
    @FXML
    private Label lblMensaje;

    // Usuario que ha iniciado sesión
    // Se utiliza para cargar solo sus listas de compra
    private Usuario usuarioAutenticado;

    // Mé_todo que se ejecuta automáticamente al cargar la vista FXML
    @FXML
    private void initialize() {

        // Configuramos cómo se mostrarán los objetos dentro de los ComboBox
        configurarComboBoxes();
    }

    // Recibe el usuario autenticado desde MainController
    public void setUsuarioAutenticado(Usuario usuarioAutenticado) {

        // Guardamos el usuario autenticado
        this.usuarioAutenticado = usuarioAutenticado;

        // Cargamos las listas del usuario y los productos disponibles
        cargarDatosFormulario();
    }

    // Carga las listas del usuario autenticado y los productos disponibles en los ComboBox
    private void cargarDatosFormulario() {

        // Comprobamos que exista un usuario autenticado
        if (usuarioAutenticado == null) {
            mostrarMensaje("No hay usuario autenticado", false);
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
        cmbListas.setConverter(new StringConverter<ListaCompra>() {

            @Override
            public String toString(ListaCompra lista) {

                // Si la lista es null, devolvemos texto vacío para evitar errores
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

    // Añade el producto seleccionado a la lista seleccionada
    @FXML
    private void onAnadirProductoLista() {

        // Obtenemos la lista seleccionada en el ComboBox
        ListaCompra listaSeleccionada = cmbListas.getSelectionModel().getSelectedItem();

        // Obtenemos el producto seleccionado en el ComboBox
        Producto productoSeleccionado = cmbProductos.getSelectionModel().getSelectedItem();

        // Validamos que se haya seleccionado una lista
        if (listaSeleccionada == null) {
            mostrarMensaje("Selecciona una lista", false);
            return;
        }

        // Validamos que se haya seleccionado un producto
        if (productoSeleccionado == null) {
            mostrarMensaje("Selecciona un producto", false);
            return;
        }

        // Variable donde guardaremos la cantidad convertida a número entero
        int cantidad;

        // Intentamos convertir el texto de cantidad a int
        try {
            cantidad = Integer.parseInt(txtCantidad.getText());

        } catch (NumberFormatException e) {
            mostrarMensaje("La cantidad debe ser un número entero", false);
            return;
        }

        // Creamos el servicio encargado de añadir productos a listas
        ItemListaService itemListaService = new ItemListaService();

        // Añadimos el producto seleccionado a la lista seleccionada
        String resultado = itemListaService.anadirProductoALista(
                listaSeleccionada.getIdLista(),
                productoSeleccionado.getIdProducto(),
                cantidad
        );

        // Si el servicio devuelve OK, la operación fue correcta
        if (resultado.equals("OK")) {

            // Mostramos mensaje de éxito
            mostrarMensaje("Producto añadido a la lista correctamente", true);

            // Limpiamos solo la cantidad para poder seguir añadiendo productos rápidamente
            txtCantidad.clear();

        } else {

            // Mostramos el error devuelto por el servicio
            mostrarMensaje(resultado, false);
        }
    }

    // Limpia solo el campo de cantidad y el mensaje
    @FXML
    private void onLimpiarFormulario() {

        // Limpiamos solo el campo de cantidad
        txtCantidad.clear();

        // Limpiamos el mensaje y sus estilos
        lblMensaje.setText("");
        lblMensaje.getStyleClass().removeAll("mensaje-exito", "mensaje-error");

        // Devolvemos el foco al campo cantidad
        txtCantidad.requestFocus();
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