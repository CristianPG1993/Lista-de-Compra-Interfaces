package com.listacompra.interfaces.listacompra.controller;

import com.listacompra.interfaces.listacompra.service.UsuarioService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

// Controlador encargado de gestionar la pantalla de registro de usuarios
public class RegistroUsuarioController {

    // Campo donde el usuario introduce el DNI
    @FXML
    private TextField txtDni;

    // Campo donde el usuario introduce el nombre
    @FXML
    private TextField txtNombre;

    // Campo donde el usuario introduce el apellido
    @FXML
    private TextField txtApellido;

    // Campo donde el usuario introduce el email
    @FXML
    private TextField txtEmail;

    // Campo donde el usuario introduce la contraseña
    @FXML
    private PasswordField txtPassword;

    // Label usado para mostrar mensajes de éxito o error
    @FXML
    private Label lblMensaje;

    // Mé_todo que se ejecuta automáticamente al cargar la vista
    @FXML
    private void initialize() {

        // Limpiamos el mensaje inicial
        lblMensaje.setText("");

        // Quitamos el foco inicial de los campos para que se vean los promptText
        Platform.runLater(() -> lblMensaje.requestFocus());
    }

    // Mé_todo ejecutado al pulsar el botón de registrar usuario
    @FXML
    private void onRegistrarUsuario() {

        // Creamos el servicio de usuarios para usar la lógica de negocio
        UsuarioService usuarioService = new UsuarioService();

        // Intentamos crear el usuario con los datos introducidos en el formulario
        String resultado = usuarioService.crearUsuario(
                txtDni.getText(),
                txtNombre.getText(),
                txtApellido.getText(),
                txtEmail.getText(),
                txtPassword.getText()
        );

        if (resultado.equals("OK")) {

            // Mostramos mensaje de éxito en pantalla
            mostrarMensaje("Usuario creado correctamente", true);

            // Limpiamos el formulario después de registrar correctamente
            txtDni.clear();
            txtNombre.clear();
            txtApellido.clear();
            txtEmail.clear();
            txtPassword.clear();

            // Devolvemos el foco al campo DNI
            txtDni.requestFocus();

        } else {

            // Mostramos el mensaje de error devuelto por el servicio
            mostrarMensaje(resultado, false);
        }
    }

    // Mé_todo ejecutado al pulsar el botón de volver
    @FXML
    private void onVolver() {

        try {
            // Cargamos la vista de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login-view.fxml"));
            Parent vista = loader.load();

            // Sustituimos la vista actual por el login
            txtDni.getScene().setRoot(vista);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Mé_todo auxiliar para mostrar mensajes de éxito o error
    private void mostrarMensaje(String mensaje, boolean exito) {

        // Mostramos el texto recibido
        lblMensaje.setText(mensaje);

        // Eliminamos estilos anteriores para evitar mezclar colores
        lblMensaje.getStyleClass().removeAll("mensaje-exito", "mensaje-error");

        // Aplicamos el estilo correspondiente
        if (exito) {
            lblMensaje.getStyleClass().add("mensaje-exito");
        } else {
            lblMensaje.getStyleClass().add("mensaje-error");
        }
    }
}