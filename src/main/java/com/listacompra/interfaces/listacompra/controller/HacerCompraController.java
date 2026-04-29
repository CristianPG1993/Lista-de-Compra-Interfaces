package com.listacompra.interfaces.listacompra.controller;

import com.listacompra.interfaces.listacompra.model.ItemLista;
import com.listacompra.interfaces.listacompra.model.ListaCompra;
import com.listacompra.interfaces.listacompra.model.Usuario;
import com.listacompra.interfaces.listacompra.service.ItemListaService;
import com.listacompra.interfaces.listacompra.service.ListaCompraService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.math.BigDecimal;

public class HacerCompraController {

    // Usuario autenticado recibido desde MainController
    private Usuario usuarioAutenticado;

    // ComboBox donde se muestran las listas de compra del usuario
    @FXML
    private ComboBox<ListaCompra> cmbListas;

    // Tabla donde se muestran los productos de la lista seleccionada
    @FXML
    private TableView<ItemLista> tablaItems;

    // Columna donde se muestra el nombre del producto
    @FXML
    private TableColumn<ItemLista, String> colProducto;

    // Columna donde se muestra la cantidad del producto
    @FXML
    private TableColumn<ItemLista, Integer> colCantidad;

    // Columna donde se muestra el precio del producto
    @FXML
    private TableColumn<ItemLista, BigDecimal> colPrecio;

    // Columna donde se muestra si el producto ha sido comprado
    @FXML
    private TableColumn<ItemLista, Boolean> colComprado;

    // Botón para marcar como comprado el producto seleccionado
    @FXML
    private Button btnMarcarComprado;

    // Botón para actualizar la tabla
    @FXML
    private Button btnRefrescar;

    // Label donde se muestra el total de la lista
    @FXML
    private Label lblTotal;

    // Label para mostrar mensajes de éxito o error
    @FXML
    private Label lblMensaje;

    // Lista observable que conecta los items de la lista con la tabla JavaFX
    private ObservableList<ItemLista> itemsObservable;

    @FXML
    private void initialize(){

        configurarComboBox();

        // Asociamos cada columna de la tabla con una propiedad del modelo ItemLista
        colProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        colComprado.setCellValueFactory(new PropertyValueFactory<>("comprado"));
    }

    private void configurarComboBox() {

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
    }

    // Recibe el usuario autenticado desde MainController
    public void setUsuarioAutenticado(Usuario usuarioAutenticado){

        this.usuarioAutenticado = usuarioAutenticado;

        cargarListasUsuario();

    }

    // Carga en la tabla los productos/items de la lista seleccionada.
    private void cargarItemsLista() {

        // Obtenemos la lista seleccionada en el ComboBox.
        ListaCompra listaSeleccionada = cmbListas.getSelectionModel().getSelectedItem();

        // Si no hay lista seleccionada, no cargamos nada.
        if (listaSeleccionada == null) {
            return;
        }

        // Creamos el servicio de items de lista.
        ItemListaService itemListaService = new ItemListaService();

        // Obtenemos los items de la lista seleccionada y los convertimos a ObservableList.
        itemsObservable = FXCollections.observableArrayList(
                itemListaService.listarItemsPorLista(listaSeleccionada.getIdLista())
        );

        // Asociamos los items a la tabla.
        tablaItems.setItems(itemsObservable);

        // Calculamos el total de la lista seleccionada
        BigDecimal total = itemListaService.calcularTotalLista(listaSeleccionada.getIdLista());

        // Mostramos el total en pantalla
        lblTotal.setText("Total: " + total + " €");
    }

    private void cargarListasUsuario() {

        // Si por algún motivo no hay usuario autenticado, no podemos cargar listas
        if (usuarioAutenticado == null) {
            lblMensaje.setText("No hay usuario autenticado.");
            lblMensaje.getStyleClass().removeAll("mensaje-exito");
            lblMensaje.getStyleClass().add("mensaje-error");
            return;
        }

        // Creamos el servicio de listas de compra
        ListaCompraService listaCompraService = new ListaCompraService();

        // Cargamos en el ComboBox las listas del usuario autenticado
        cmbListas.setItems(
                FXCollections.observableArrayList(
                        listaCompraService.obtenerListasPorDni(usuarioAutenticado.getDni())
                )
        );

        // Cuando el usuario selecciona una lista, cargamos sus productos en la tabla
        cmbListas.setOnAction(event -> cargarItemsLista());
    }

    @FXML
    public void onMarcarComprado() {
    }

    @FXML
    public void onRefrescar() {
    }


}
