package com.listacompra.interfaces.listacompra.controller;

// Importaciones necesarias para JavaFX
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

// Importamos el servicio y el modelo
import com.listacompra.interfaces.listacompra.service.UsuarioService;
import com.listacompra.interfaces.listacompra.model.Usuario;
import javafx.stage.Stage;

import java.io.IOException;

// Clase controller encargada de gestionar la lógica de la pantalla de login
public class LoginController {

    // Referencia al contenedor principal (VBox) del login.
    // Se usa para controlar el foco inicial de la ventana.
    @FXML
    private VBox rootLogin;

    // Campo de texto donde el usuario introduce el DNI.
    @FXML
    private TextField txtDni;

    // Campo de contraseña donde el usuario introduce su password.
    @FXML
    private PasswordField txtPassword;

    // Servicio encargado de la lógica de negocio de usuarios.
    // Se utiliza para autenticar al usuario contra la base de datos.
    private final UsuarioService usuarioService = new UsuarioService();

    // Mé_todo que se ejecuta automáticamente cuando se carga el FXML.
    // Sirve para inicializar la vista.
    @FXML
    public void initialize() {

        // Platform.runLater asegura que el código se ejecute
        // después de que la interfaz esté completamente cargada.
        Platform.runLater(() -> {
            if (rootLogin != null) {
                // Quitamos el foco del TextField inicial (DNI)
                // para que se vea el promptText correctamente.
                rootLogin.requestFocus();
            }
        });
    }

    // Mé_todo que se ejecuta al pulsar el botón "Iniciar sesión".
    @FXML
    private void iniciarSesion() {

        // 1. Obtener los datos introducidos por el usuario
        String dni = txtDni.getText();
        String password = txtPassword.getText();

        // 2. Llamar al servicio para autenticar al usuario
        Usuario usuario = usuarioService.autenticarUsuario(dni, password);

        // 3. Comprobar el resultado de la autenticación
        if (usuario == null) {

            // Mostrar alerta de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de login");
            alert.setHeaderText(null);
            alert.setContentText("DNI o contraseña incorrectos");

            alert.showAndWait();

        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login correcto");
            alert.setHeaderText(null);
            alert.setContentText("Bienvenido " + usuario.getNombre());

            alert.showAndWait();

            try {

                // 1. Cargar la nueva vista
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main-view.fxml"));
                Scene nuevaEscena = new Scene(loader.load());

                // 2. Obtener el Stage actual
                Stage stage = (Stage) txtDni.getScene().getWindow();

                // 3. Cambiar escena
                stage.setScene(nuevaEscena);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Mé_todo que se ejecuta al pulsar el botón "Crear usuario".
    @FXML
    private void crearUsuario() {

        // De momento solo mostramos un mensaje de prueba.
        // Más adelante aquí abriremos la pantalla de registro.
        System.out.println("Abrir pantalla de creación de usuario");
    }
}