package com.listacompra.interfaces.listacompra.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class UsuariosController {

    @FXML
    private VBox rootUsuarios;
    @FXML
    private Button btnActualizarUsuario;

    // Referencia al controlador principal para acceder al usuario autenticado.
    private MainController mainController;


    @FXML
    private void onActualizarUsuario() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/actualizar_usuario-view.fxml"));

            Parent vista = loader.load();

            StackPane contenedorCentral = (StackPane) rootUsuarios.getParent();
            contenedorCentral.getChildren().clear();
            StackPane.setAlignment(vista, Pos.TOP_CENTER);
            contenedorCentral.getChildren().add(vista);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void onEliminarUsuario() {

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/eliminar-usuario-view.fxml"));

            Parent vista = loader.load();

            // Obtenemos el controlador de la vista eliminar-usuario-view.fxml.
            EliminarUsuarioController eliminarController = loader.getController();

            // Si tenemos acceso al MainController, pasamos al controlador de eliminación
            // el usuario que inició sesión en la aplicación.
            if (mainController != null) {
                eliminarController.setUsuarioAutenticado(mainController.getUsuarioAutenticado());
            }

            StackPane contenedorCentral = (StackPane) rootUsuarios.getParent();
            contenedorCentral.getChildren().clear();
            StackPane.setAlignment(vista, Pos.TOP_CENTER);
            contenedorCentral.getChildren().add(vista);

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    // Recibe la referencia del controlador principal.
    public void setMainController(MainController mainController) {

        // Guardamos la referencia para poder consultar el usuario autenticado más adelante.
        this.mainController = mainController;
    }

}