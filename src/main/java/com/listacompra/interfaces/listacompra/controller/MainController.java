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

public class MainController {

    // Usuario que ha iniciado sesión en la aplicación.
    private Usuario usuarioAutenticado;

    @FXML
    private StackPane contenidoCentral;

    @FXML
    private Label lblEstado;

    // Guarda en el controlador principal el usuario que ha iniciado sesión.
    public void setUsuarioAutenticado(Usuario usuarioAutenticado) {

        // Asignamos el usuario recibido al atributo de la clase.
        this.usuarioAutenticado = usuarioAutenticado;

        // Mostramos en la barra de estado qué usuario ha iniciado sesión.
        lblEstado.setText("Usuario autenticado: " + usuarioAutenticado.getNombre());
    }

    // Devuelve el usuario que ha iniciado sesión en la aplicación.
    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    @FXML
    private void onMostrarUsuarios(){

        lblEstado.setText("Módulo de usuario cargado");

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/usuarios-view.fxml"));
            Parent vista = loader.load();

            // Obtenemos el controlador de la vista usuarios-view.fxml.
            UsuariosController usuariosController = loader.getController();

            // Pasamos este MainController al UsuariosController.
            usuariosController.setMainController(this);

            contenidoCentral.getChildren().clear();
            StackPane.setAlignment(vista, Pos.CENTER);
            contenidoCentral.getChildren().add(vista);
        } catch (IOException e) {
            e.printStackTrace();
            lblEstado.setText("Error al cargar la vista de usuarios");
        }
    }

    public void mostrarMensajeEstado(String mensaje) {
        lblEstado.setText(mensaje);
    }


    @FXML
    private void onSalir() {

        // Limpiamos la referencia al usuario autenticado.
        // Aunque la aplicación se cierre, dejamos el estado interno limpio.
        usuarioAutenticado = null;

        // Cerramos la ventana principal de la aplicación.
        Stage stage = (Stage) lblEstado.getScene().getWindow();
        stage.close();
    }
}