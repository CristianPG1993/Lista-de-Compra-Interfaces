package com.listacompra.interfaces.listacompra.controller;

// Importaciones necesarias para JavaFX
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

    // Referencia al contenedor principal (VBox) del login
    // Se usa para controlar el foco inicial de la ventana
    @FXML
    private VBox rootLogin;

    // Campo de texto donde el usuario introduce el DNI
    @FXML
    private TextField txtDni;

    // Campo de contraseña donde el usuario introduce su password
    @FXML
    private PasswordField txtPassword;

    // Servicio encargado de la lógica de negocio de usuarios.
    // Se utiliza para autenticar al usuario contra la base de datos
    private final UsuarioService usuarioService = new UsuarioService();

    // Mé_todo que se ejecuta automáticamente cuando se carga el FXML
    // Sirve para inicializar la vista
    @FXML
    public void initialize() {

        // Platform.runLater asegura que el código se ejecute
        // después de que la interfaz esté completamente cargada
        Platform.runLater(() -> {
            if (rootLogin != null) {
                // Quitamos el foco del TextField inicial (DNI)
                // para que se vea el promptText correctamente
                rootLogin.requestFocus();
            }
        });
    }

    // Mé_todo que se ejecuta al pulsar el botón "Iniciar sesión"
    @FXML
    private void iniciarSesion() {

        // Obtener los datos introducidos por el usuario
        String dni = txtDni.getText();
        String password = txtPassword.getText();

        // Llamar al servicio para autenticar al usuario
        Usuario usuario = usuarioService.autenticarUsuario(dni, password);

        // Comprobar el resultado de la autenticación, si es null devuelve un error
        if (usuario == null) {

            mostrarAlerta(Alert.AlertType.ERROR, "Error de login", "DNI o contraseña incorrectos");

        }

        // Si el usuario existe, mostramos mensaje de bienvenida
        mostrarAlerta(Alert.AlertType.INFORMATION,"Login correcto", "Bienvenid@ " + usuario.getNombre());

        try {

            // Creamos el FXMLLoader para cargar la vista principal.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main-view.fxml"));

            // Cargamos el FXML y obtenemos la raíz de la vista principal.
            Parent root = loader.load();

            // Obtenemos el controlador asociado a main-view.fxml.
            MainController mainController = loader.getController();

            // Pasamos al MainController el usuario que ha iniciado sesión.
            mainController.setUsuarioAutenticado(usuario);

            // Creamos la nueva escena usando la vista principal cargada.
            Scene nuevaEscena = new Scene(root);

            // Obtener el Stage actual
            Stage stage = (Stage) txtDni.getScene().getWindow();

            // Cambiar escena
            stage.setScene(nuevaEscena);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // Mé_todo que se ejecuta al pulsar el botón "Crear usuario".
    @FXML
    private void crearUsuario() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/registro-usuario-view.fxml"));
            Parent vista = loader.load();

            Stage stage = (Stage) rootLogin.getScene().getWindow();
            stage.getScene().setRoot(vista);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Mé_todo auxiliar para mostrar alertas reutilizables.
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {

        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}