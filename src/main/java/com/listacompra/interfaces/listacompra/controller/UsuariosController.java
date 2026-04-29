package com.listacompra.interfaces.listacompra.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

// Controlador del módulo de usuarios
// Gestiona las acciones disponibles dentro de la vista usuarios-view.fxml
public class UsuariosController {

    // Contenedor raíz de la vista de usuarios
    // Se usa para obtener el StackPane central donde se cargan las subviews
    @FXML
    private VBox rootUsuarios;

    // Referencia al controlador principal
    // Se usa para acceder al usuario autenticado cuando sea necesario
    private MainController mainController;

    // Carga la vista para actualizar los datos de un usuario
    @FXML
    private void onActualizarUsuario() {

        try {
            // Cargamos la vista de actualización de usuario
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/actualizar_usuario-view.fxml"));
            Parent vista = loader.load();

            // Obtenemos el controlador de la actualización de usuario
            ActualizarUsuarioController actualizarUsuarioController = loader.getController();

            // Si tenemos acceso al MainController, pasamos el usuario autenticado
            if (mainController != null){
                actualizarUsuarioController.setUsuarioAutenticado(mainController.getUsuarioAutenticado());
            }

            // Mostramos la vista dentro del panel central
            cargarVistaEnCentro(vista);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Carga la vista para eliminar el usuario autenticado
    @FXML
    private void onEliminarUsuario() {

        try {
            // Cargamos la vista de eliminación de usuario
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/eliminar-usuario-view.fxml"));
            Parent vista = loader.load();

            // Obtenemos el controlador de eliminación
            EliminarUsuarioController eliminarController = loader.getController();

            // Si tenemos acceso al MainController, pasamos el usuario autenticado
            if (mainController != null) {
                eliminarController.setUsuarioAutenticado(mainController.getUsuarioAutenticado());
            }

            // Mostramos la vista dentro del panel central
            cargarVistaEnCentro(vista);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Recibe la referencia del controlador principal
    public void setMainController(MainController mainController) {

        // Guardamos la referencia para consultar el usuario autenticado
        this.mainController = mainController;
    }

    // Mé_todo auxiliar para cargar una vista dentro del panel central
    private void cargarVistaEnCentro(Parent vista) {

        // Obtenemos el StackPane central a partir del padre del VBox actual
        StackPane contenedorCentral = (StackPane) rootUsuarios.getParent();

        // Eliminamos la vista anterior
        contenedorCentral.getChildren().clear();

        // Colocamos la nueva vista centrada arriba
        StackPane.setAlignment(vista, Pos.TOP_CENTER);

        // Añadimos la nueva vista
        contenedorCentral.getChildren().add(vista);
    }
}