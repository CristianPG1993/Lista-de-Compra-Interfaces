package com.listacompra.interfaces.listacompra.controller;

import com.listacompra.interfaces.listacompra.service.UsuarioService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistroUsuarioController {

    @FXML
    private TextField txtDni;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblMensaje;
    @FXML private Button btnVolver;



    @FXML
    public void onRegistrarUsuario() {

        //Creamos el servicio para utilizar el mé_todo crearUsuario
        UsuarioService usuarioService = new UsuarioService();

        String resultado = usuarioService.crearUsuario(
                txtDni.getText(),
                txtNombre.getText(),
                txtApellido.getText(),
                txtEmail.getText(),
                txtPassword.getText()
        );

        if (resultado.equals("OK")) {

            lblMensaje.setText("Usuario creado correctamente");
            lblMensaje.getStyleClass().removeAll("mensaje-error");
            lblMensaje.getStyleClass().add("mensaje-exito");

        } else {
            lblMensaje.setText(resultado);
            lblMensaje.getStyleClass().removeAll("mensaje-exito");
            lblMensaje.getStyleClass().add("mensaje-error");
        }
    }

    @FXML
    public void onVolver() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login-view.fxml"));
            Parent vista = loader.load();

            txtDni.getScene().setRoot(vista);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        lblMensaje.setText("");

        Platform.runLater(() -> lblMensaje.requestFocus());
    }
}
