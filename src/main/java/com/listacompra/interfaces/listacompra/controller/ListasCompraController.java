package com.listacompra.interfaces.listacompra.controller;

import com.listacompra.interfaces.listacompra.model.ListaCompra;
import com.listacompra.interfaces.listacompra.model.Usuario;
import com.listacompra.interfaces.listacompra.service.ListaCompraService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;

public class ListasCompraController {

    // Campo donde el usuario introduce el nombre de la nueva lista
    @FXML
    private TextField txtNombreLista;

    // Tabla donde se mostrarán las listas de compra del usuario autenticado
    @FXML
    private TableView<ListaCompra> tablaListas;

    // Columna para mostrar el ID de la lista
    @FXML
    private TableColumn<ListaCompra, Integer> colId;

    //Columna para mostrar el nombre de la lista
    @FXML
    private TableColumn<ListaCompra, String> colNombre;

    //Columna para mostrar la fecha de creación de la lista
    @FXML
    private TableColumn<ListaCompra, Date> colFecha;

    // Botón para crear una nueva lista
    @FXML
    private Button btnCrearLista;

    //Botón para eliminar la lista seleccionada
    @FXML
    private Button btnEliminarLista;

    // Botón para limpiar
    @FXML
    private Button bntLimpiar;

    // Mensaje para mostrar éxito o error
    @FXML
    private Label lblMensaje;

    // Lista observable que conecta las listas de compra con la tabla JavaFX
    private ObservableList<ListaCompra> listasObservables;

    @FXML
    private void initialize(){

        colId.setCellValueFactory(new PropertyValueFactory<>("idLista"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompra"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
    }

    // Usuario que ha iniciado sesión, se utiliza para cear y mostrar solo las listas del usuario
    private Usuario usuarioAutenticado;

    // Recibe el usuario autenticado desde MainController
    public void setUsuarioAutenticado(Usuario usuarioAutenticado){

        this.usuarioAutenticado = usuarioAutenticado;

        cargarListas();

    }

    private void cargarListas(){

        // Si por algún motivo no hay usuario autenticado, no podemos cargar listas
        if (usuarioAutenticado == null) {
            lblMensaje.setText("No hay usuario autenticado.");
            lblMensaje.getStyleClass().removeAll("mensaje-exito");
            lblMensaje.getStyleClass().add("mensaje-error");
            return;
        }

        ListaCompraService listaCompraService = new ListaCompraService();

        listasObservables = FXCollections.observableArrayList(listaCompraService.obtenerListasPorDni(usuarioAutenticado.getDni()));

        tablaListas.setItems(listasObservables);
    }

    @FXML
    private void onCrearLista(){

        // Si por algún motivo no hay usuario autenticado, no podemos cargar listas
        if (usuarioAutenticado == null) {
            lblMensaje.setText("No hay usuario autenticado.");
            lblMensaje.getStyleClass().removeAll("mensaje-exito");
            lblMensaje.getStyleClass().add("mensaje-error");
            return;
        }

        ListaCompraService listaCompraService = new ListaCompraService();

        String resultado = listaCompraService.crearListaCompra(
                usuarioAutenticado.getDni(),
                txtNombreLista.getText()
        );

        // Si el resultado del servicio devuelve OK, el producto se ha creado correctamente
        if(resultado.equals("OK")){

            // Mostramos mensaje de éxito.
            lblMensaje.setText("Lista añadida correctamente.");

            // Quitamos estilo de error si estaba aplicado.
            lblMensaje.getStyleClass().removeAll("mensaje-error");

            // Aplicamos estilo de éxito.
            lblMensaje.getStyleClass().add("mensaje-exito");

            // Limpiamos los campos del formulario.
            txtNombreLista.clear();

            // Recargamos la tabla para mostrar la nueva lista
            cargarListas();

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
    private void onEliminarLista(){

        ListaCompra listaCompraSeleccionada = tablaListas.getSelectionModel().getSelectedItem();

        if (listaCompraSeleccionada == null) {

            // Mostramos mensaje de error
            lblMensaje.setText("Selecciona una lista para eliminar.");

            // Quitamos estilo de éxito si estaba aplicado
            lblMensaje.getStyleClass().removeAll("mensaje-exito");

            // Aplicamos estilo de error
            lblMensaje.getStyleClass().add("mensaje-error");

            // Cortamos la ejecución porque no hay producto seleccionado
            return;
        }

        ListaCompraService listaCompraService = new ListaCompraService();

        String resultado = listaCompraService.eliminarListaCompraPorId(
                usuarioAutenticado.getDni(),
                listaCompraSeleccionada.getIdLista()
        );

        if (resultado.equals("OK")) {

            // Mostramos mensaje de éxito
            lblMensaje.setText("Lista eliminada correctamente.");

            // Quitamos estilo de error si estaba aplicado
            lblMensaje.getStyleClass().removeAll("mensaje-error");

            // Aplicamos estilo de éxito.
            lblMensaje.getStyleClass().add("mensaje-exito");

            // Recargamos la tabla para que desaparezca la tabla eliminada
            cargarListas();

        } else {

            // Si el servicio devuelve otro texto, es un error de validación o búsqueda
            lblMensaje.setText(resultado);

            // Quitamos estilo de éxito si estaba aplicado
            lblMensaje.getStyleClass().removeAll("mensaje-exito");

            // Aplicamos estilo de error.
            lblMensaje.getStyleClass().add("mensaje-error");
        }

    }

    @FXML
    private void onLimpiarFormulario(){

        txtNombreLista.clear();

        lblMensaje.setText("");

        lblMensaje.getStyleClass().removeAll("mensaje-exito", "mensaje-error");

        txtNombreLista.requestFocus();
    }
}
