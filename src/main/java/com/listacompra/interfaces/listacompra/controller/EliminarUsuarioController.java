package com.listacompra.interfaces.listacompra.controller;

import com.listacompra.interfaces.listacompra.model.Usuario;
import com.listacompra.interfaces.listacompra.service.UsuarioService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

// Controlador encargado de eliminar el usuario autenticado
public class EliminarUsuarioController {

    // Usuario que ha iniciado sesión en la aplicación
    // Se usa para impedir que un usuario elimine cuentas de otros usuarios
    private Usuario usuarioAutenticado;

    // Campo donde se muestra el DNI del usuario autenticado
    @FXML
    private TextField txtDniBuscar;

    // Labels donde se muestran los datos del usuario autenticado
    @FXML
    private Label lblNombre;

    @FXML
    private Label lblApellido;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblDni;

    // Botón usado para confirmar la eliminación
    @FXML
    private Button btnEliminar;

    // Label para mostrar mensajes de éxito o error
    @FXML
    private Label lblMensaje;

    // Recibe el usuario autenticado desde UsuariosController
    public void setUsuarioAutenticado(Usuario usuarioAutenticado) {

        // Guardamos el usuario autenticado
        this.usuarioAutenticado = usuarioAutenticado;

        // Cargamos automáticamente su DNI y bloqueamos el campo
        txtDniBuscar.setText(usuarioAutenticado.getDni());
        txtDniBuscar.setEditable(false);

        // Mostramos sus datos en pantalla
        lblNombre.setText("Nombre: " + usuarioAutenticado.getNombre());
        lblApellido.setText("Apellido: " + usuarioAutenticado.getApellido());
        lblEmail.setText("Email: " + usuarioAutenticado.getEmail());
        lblDni.setText("DNI: " + usuarioAutenticado.getDni());

        // Mostramos mensaje informativo
        mostrarMensaje("Solo puede eliminar su propio usuario", true);
    }

    // Elimina el usuario autenticado y vuelve al login
    @FXML
    private void onEliminarUsuario() {

        // Comprobamos que exista un usuario autenticado antes de eliminar
        if (usuarioAutenticado == null) {
            mostrarMensaje("No hay usuario autenticado", false);
            return;
        }

        // Creamos el servicio de usuarios
        UsuarioService usuarioService = new UsuarioService();

        // Eliminamos únicamente el usuario autenticado usando su DNI
        String resultado = usuarioService.eliminarUsuario(usuarioAutenticado.getDni());

        // Si el servicio devuelve OK, la eliminación ha sido correcta
        if (resultado.equals("OK")) {

            // Mostramos mensaje de éxito y bloqueamos el botón
            mostrarMensaje("Usuario eliminado correctamente", true);
            btnEliminar.setDisable(true);

            // Volvemos al login porque la cuenta ya no existe
            volverAlLogin();

        } else {

            // Si el servicio devuelve otro texto, mostramos el error
            mostrarMensaje(resultado, false);
        }
    }

    // Carga de nuevo la pantalla de login
    private void volverAlLogin() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login-view.fxml"));
            Parent vista = loader.load();

            txtDniBuscar.getScene().setRoot(vista);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensaje("No se pudo volver al login", false);
        }
    }

    // Mé_todo auxiliar para mostrar mensajes de éxito o error
    private void mostrarMensaje(String mensaje, boolean exito) {

        // Mostramos el texto recibido
        lblMensaje.setText(mensaje);

        // Eliminamos estilos anteriores
        lblMensaje.getStyleClass().removeAll("mensaje-exito", "mensaje-error");

        // Aplicamos el estilo correspondiente
        if (exito) {
            lblMensaje.getStyleClass().add("mensaje-exito");
        } else {
            lblMensaje.getStyleClass().add("mensaje-error");
        }
    }
}