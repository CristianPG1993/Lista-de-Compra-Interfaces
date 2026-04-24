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

public class EliminarUsuarioController {

    // Usuario que ha iniciado sesión en la aplicación.
    // Se usará para impedir que un usuario elimine cuentas de otros usuarios.
    private Usuario usuarioAutenticado;

    // Campo donde el usuario introduce el DNI que quiere buscar.
    @FXML
    private TextField txtDniBuscar;

    // Labels donde mostraremos los datos del usuario encontrado.
    @FXML
    private Label lblNombre;

    @FXML
    private Label lblApellido;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblDni;

    // Botones de acción de la vista.
    @FXML
    private Button btnEliminar;

    // Label para mostrar mensajes de éxito o error.
    @FXML
    private Label lblMensaje;

    // Recibe desde el controlador anterior el usuario autenticado.
    public void setUsuarioAutenticado(Usuario usuarioAutenticado) {

        // Guardamos el usuario autenticado en el atributo de clase.
        this.usuarioAutenticado = usuarioAutenticado;

        // Cargamos automáticamente su DNI en el campo de búsqueda.
        txtDniBuscar.setText(usuarioAutenticado.getDni());

        // Bloqueamos el campo para que no pueda escribir otro DNI.
        txtDniBuscar.setEditable(false);

        // Mostramos sus datos en pantalla.
        lblNombre.setText("Nombre: " + usuarioAutenticado.getNombre());
        lblApellido.setText("Apellido: " + usuarioAutenticado.getApellido());
        lblEmail.setText("Email: " + usuarioAutenticado.getEmail());
        lblDni.setText("DNI: " + usuarioAutenticado.getDni());

        // Mostramos mensaje informativo.
        lblMensaje.setText("Solo puede eliminar su propio usuario.");
    }

    @FXML
    private void onEliminarUsuario() {

        // Comprobamos que exista un usuario autenticado antes de eliminar.
        if (usuarioAutenticado == null) {

            // Mostramos mensaje de error si por algún motivo no llegó el usuario autenticado.
            lblMensaje.setText("No hay usuario autenticado.");

            // Quitamos el estilo de éxito si estaba aplicado.
            lblMensaje.getStyleClass().removeAll("mensaje-exito");

            // Aplicamos el estilo de error.
            lblMensaje.getStyleClass().add("mensaje-error");

            // Detenemos la ejecución.
            return;
        }

        // Creamos una instancia del servicio de usuarios.
        UsuarioService usuarioService = new UsuarioService();

        // Eliminamos únicamente el usuario autenticado usando su DNI.
        String resultado = usuarioService.eliminarUsuario(usuarioAutenticado.getDni());

        // Si el servicio devuelve OK, la eliminación ha sido correcta.
        if (resultado.equals("OK")) {

            // Mostramos mensaje de éxito.
            lblMensaje.setText("Usuario eliminado correctamente.");

            // Quitamos estilo de error si estaba aplicado.
            lblMensaje.getStyleClass().removeAll("mensaje-error");

            // Aplicamos estilo de éxito.
            lblMensaje.getStyleClass().add("mensaje-exito");

            // Bloqueamos el botón para evitar pulsaciones repetidas.
            btnEliminar.setDisable(true);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login-view.fxml"));
                Parent vista = loader.load();

                txtDniBuscar.getScene().setRoot(vista);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            // Si el servicio devuelve otro texto, mostramos el error.
            lblMensaje.setText(resultado);

            // Quitamos estilo de éxito si estaba aplicado.
            lblMensaje.getStyleClass().removeAll("mensaje-exito");

            // Aplicamos estilo de error.
            lblMensaje.getStyleClass().add("mensaje-error");
        }
    }

}