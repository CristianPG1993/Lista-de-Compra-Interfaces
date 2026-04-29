package com.listacompra.interfaces.listacompra.controller;

import com.listacompra.interfaces.listacompra.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

// Controlador principal de la aplicación
// Gestiona el menú lateral, la carga de módulos en el panel central
// y mantiene la referencia al usuario autenticado
public class MainController {

    // Usuario que ha iniciado sesión en la aplicación
    private Usuario usuarioAutenticado;

    // Contenedor central donde se cargan dinámicamente las vistas internas
    @FXML
    private StackPane contenidoCentral;

    // Label inferior usado como barra de estado
    @FXML
    private Label lblEstado;

    // Guarda en el controlador principal el usuario que ha iniciado sesión
    public void setUsuarioAutenticado(Usuario usuarioAutenticado) {

        // Guardamos el usuario para que otros módulos puedan trabajar con él
        this.usuarioAutenticado = usuarioAutenticado;

        // Mostramos en la barra de estado qué usuario ha iniciado sesión
        lblEstado.setText("Usuario autenticado: " + usuarioAutenticado.getNombre());
    }

    // Devuelve el usuario que ha iniciado sesión en la aplicación
    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    // Muestra un mensaje en la barra de estado
    public void mostrarMensajeEstado(String mensaje) {
        lblEstado.setText(mensaje);
    }

    // Carga el módulo de usuarios
    @FXML
    private void onMostrarUsuarios() {

        // Actualizamos la barra de estado
        lblEstado.setText("Módulo de usuarios cargado");

        try {
            // Cargamos la vista del módulo de usuarios
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/usuarios-view.fxml"));
            Parent vista = loader.load();

            // Obtenemos el controlador de usuarios y le pasamos este MainController
            UsuariosController usuariosController = loader.getController();
            usuariosController.setMainController(this);

            // Cargamos la vista en el panel central
            cargarVistaEnCentro(vista);

        } catch (IOException e) {
            e.printStackTrace();
            lblEstado.setText("Error al cargar la vista de usuarios");
        }
    }

    // Carga el módulo de productos
    @FXML
    private void onMostrarProductos() {

        // Actualizamos la barra de estado
        lblEstado.setText("Módulo de productos cargado");

        try {
            // Cargamos la vista de productos
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/productos-view.fxml"));
            Parent vista = loader.load();

            // Cargamos la vista en el panel central
            cargarVistaEnCentro(vista);

        } catch (IOException e) {
            e.printStackTrace();
            lblEstado.setText("Error al cargar la vista de productos");
        }
    }

    // Carga el módulo de listas de compra
    @FXML
    private void onMostrarListasCompra() {

        // Actualizamos la barra de estado
        lblEstado.setText("Módulo de listas de compra cargado");

        try {
            // Cargamos la vista de listas de compra
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/listas-compra-view.fxml"));
            Parent vista = loader.load();

            // Obtenemos el controlador de listas y le pasamos el usuario autenticado
            ListasCompraController listasCompraController = loader.getController();
            listasCompraController.setUsuarioAutenticado(usuarioAutenticado);

            // Cargamos la vista en el panel central
            cargarVistaEnCentro(vista);

        } catch (IOException e) {
            e.printStackTrace();
            lblEstado.setText("No se ha podido cargar la vista de listas de compra");
        }
    }

    // Carga el módulo para añadir productos a una lista
    @FXML
    private void onMostrarAnadirProductoLista() {

        // Actualizamos la barra de estado
        lblEstado.setText("Módulo de añadir producto a lista cargado");

        try {
            // Cargamos la vista de añadir producto a lista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/anadir-producto-lista-view.fxml"));
            Parent vista = loader.load();

            // Obtenemos el controlador y le pasamos el usuario autenticado
            AnadirProductosListaController controller = loader.getController();
            controller.setUsuarioAutenticado(usuarioAutenticado);

            // Cargamos la vista en el panel central
            cargarVistaEnCentro(vista);

        } catch (IOException e) {
            e.printStackTrace();
            lblEstado.setText("No se ha podido cargar la vista de añadir producto a lista");
        }
    }

    // Carga el módulo para hacer la compra
    @FXML
    private void onMostrarHacerCompra() {

        // Actualizamos la barra de estado
        lblEstado.setText("Módulo de hacer compra cargado");

        try {
            // Cargamos la vista de hacer compra
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/hacer-compra-view.fxml"));
            Parent vista = loader.load();

            // Obtenemos el controlador y le pasamos el usuario autenticado
            HacerCompraController controller = loader.getController();
            controller.setUsuarioAutenticado(usuarioAutenticado);

            // Cargamos la vista en el panel central
            cargarVistaEnCentro(vista);

        } catch (IOException e) {
            e.printStackTrace();
            lblEstado.setText("No se ha podido cargar la vista de hacer compra");
        }
    }

    // Cierra la aplicación
    @FXML
    private void onSalir() {

        // Limpiamos la referencia al usuario autenticado
        usuarioAutenticado = null;

        // Cerramos la ventana principal de la aplicación
        Stage stage = (Stage) lblEstado.getScene().getWindow();
        stage.close();
    }

    // Mé_todo auxiliar para cargar cualquier vista dentro del panel central
    private void cargarVistaEnCentro(Parent vista) {

        // Eliminamos la vista anterior
        contenidoCentral.getChildren().clear();

        // Centramos la nueva vista dentro del StackPane
        StackPane.setAlignment(vista, Pos.CENTER);

        // Añadimos la nueva vista
        contenidoCentral.getChildren().add(vista);
    }
}