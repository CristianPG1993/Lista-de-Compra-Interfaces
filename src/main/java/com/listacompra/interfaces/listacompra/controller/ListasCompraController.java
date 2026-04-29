package com.listacompra.interfaces.listacompra.controller;

import com.listacompra.interfaces.listacompra.model.ListaCompra;
import com.listacompra.interfaces.listacompra.model.Usuario;
import com.listacompra.interfaces.listacompra.service.ListaCompraService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

// Controlador encargado de gestionar las listas de compra del usuario autenticado
public class ListasCompraController {

    // Usuario que ha iniciado sesión
    // Se utiliza para crear y mostrar solo sus listas de compra
    private Usuario usuarioAutenticado;

    // Campo donde el usuario introduce el nombre de la nueva lista
    @FXML
    private TextField txtNombreLista;

    // Tabla donde se muestran las listas de compra del usuario autenticado
    @FXML
    private TableView<ListaCompra> tablaListas;

    // Columna para mostrar el ID de la lista
    @FXML
    private TableColumn<ListaCompra, Integer> colId;

    // Columna para mostrar el nombre de la lista
    @FXML
    private TableColumn<ListaCompra, String> colNombre;

    // Columna para mostrar la fecha de creación de la lista
    @FXML
    private TableColumn<ListaCompra, LocalDate> colFecha;

    // Label para mostrar mensajes de éxito o error
    @FXML
    private Label lblMensaje;

    // Lista observable que conecta las listas de compra con la tabla JavaFX
    private ObservableList<ListaCompra> listasObservables;

    // Mé_todo que se ejecuta al cargar la vista
    @FXML
    private void initialize() {

        // Asociamos cada columna con una propiedad del modelo ListaCompra
        colId.setCellValueFactory(new PropertyValueFactory<>("idLista"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompra"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
    }

    // Recibe el usuario autenticado desde MainController
    public void setUsuarioAutenticado(Usuario usuarioAutenticado) {

        // Guardamos el usuario autenticado
        this.usuarioAutenticado = usuarioAutenticado;

        // Cargamos sus listas de compra
        cargarListas();
    }

    // Carga en la tabla las listas de compra del usuario autenticado
    private void cargarListas() {

        // Si no hay usuario autenticado, no se pueden cargar listas
        if (usuarioAutenticado == null) {
            mostrarMensaje("No hay usuario autenticado", false);
            return;
        }

        // Creamos el servicio de listas de compra
        ListaCompraService listaCompraService = new ListaCompraService();

        // Convertimos la lista normal en una ObservableList para JavaFX
        listasObservables = FXCollections.observableArrayList(
                listaCompraService.obtenerListasPorDni(usuarioAutenticado.getDni())
        );

        // Asociamos la lista observable a la tabla
        tablaListas.setItems(listasObservables);
    }

    // Crea una nueva lista de compra para el usuario autenticado
    @FXML
    private void onCrearLista() {

        // Si no hay usuario autenticado, no se puede crear la lista
        if (usuarioAutenticado == null) {
            mostrarMensaje("No hay usuario autenticado", false);
            return;
        }

        // Creamos el servicio de listas de compra
        ListaCompraService listaCompraService = new ListaCompraService();

        // Creamos la lista usando el DNI del usuario autenticado
        String resultado = listaCompraService.crearListaCompra(
                usuarioAutenticado.getDni(),
                txtNombreLista.getText()
        );

        // Si el servicio devuelve OK, la lista se ha creado correctamente
        if (resultado.equals("OK")) {

            // Mostramos mensaje de éxito
            mostrarMensaje("Lista añadida correctamente", true);

            // Limpiamos el campo del formulario
            txtNombreLista.clear();

            // Recargamos la tabla para mostrar la nueva lista
            cargarListas();

        } else {

            // Mostramos el error devuelto por el servicio
            mostrarMensaje(resultado, false);
        }
    }

    // Elimina la lista seleccionada en la tabla
    @FXML
    private void onEliminarLista() {

        // Obtenemos la lista seleccionada actualmente
        ListaCompra listaCompraSeleccionada = tablaListas.getSelectionModel().getSelectedItem();

        // Si no hay lista seleccionada, mostramos error
        if (listaCompraSeleccionada == null) {
            mostrarMensaje("Selecciona una lista para eliminar", false);
            return;
        }

        // Creamos el servicio de listas de compra
        ListaCompraService listaCompraService = new ListaCompraService();

        // Eliminamos la lista usando el DNI del usuario autenticado y el ID de la lista
        String resultado = listaCompraService.eliminarListaCompraPorId(
                usuarioAutenticado.getDni(),
                listaCompraSeleccionada.getIdLista()
        );

        // Si el servicio devuelve OK, la lista se ha eliminado correctamente
        if (resultado.equals("OK")) {

            // Mostramos mensaje de éxito
            mostrarMensaje("Lista eliminada correctamente", true);

            // Recargamos la tabla para que desaparezca la lista eliminada
            cargarListas();

        } else {

            // Mostramos el error devuelto por el servicio
            mostrarMensaje(resultado, false);
        }
    }

    // Limpia el formulario de creación de listas
    @FXML
    private void onLimpiarFormulario() {

        // Limpiamos el campo de nombre
        txtNombreLista.clear();

        // Limpiamos el mensaje y sus estilos
        lblMensaje.setText("");
        lblMensaje.getStyleClass().removeAll("mensaje-exito", "mensaje-error");

        // Devolvemos el foco al campo de nombre
        txtNombreLista.requestFocus();
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