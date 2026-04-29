package com.listacompra.interfaces.listacompra.controller;

import com.listacompra.interfaces.listacompra.model.Usuario;
import com.listacompra.interfaces.listacompra.service.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

// Controlador encargado de actualizar los datos del usuario autenticado
public class ActualizarUsuarioController {

    // Campo donde se muestra el DNI del usuario autenticado
    @FXML
    private TextField txtDniBuscar;

    // Campo donde se muestra o modifica el nombre
    @FXML
    private TextField txtNombre;

    // Campo donde se muestra o modifica el apellido
    @FXML
    private TextField txtApellido;

    // Campo donde se muestra o modifica el email
    @FXML
    private TextField txtEmail;

    // Campo donde se muestra o modifica la contraseña
    @FXML
    private PasswordField txtPassword;

    // Label usado para mostrar mensajes de éxito o error
    @FXML
    private Label lblMensaje;

    // Usuario que ha iniciado sesión
    // Se usa para impedir que se actualicen datos de otros usuarios
    private Usuario usuarioAutenticado;

    // Recibe el usuario autenticado desde UsuariosController
    public void setUsuarioAutenticado(Usuario usuarioAutenticado) {

        // Guardamos el usuario autenticado en el atributo de clase
        this.usuarioAutenticado = usuarioAutenticado;

        // Cargamos automáticamente sus datos en el formulario
        txtDniBuscar.setText(usuarioAutenticado.getDni());
        txtNombre.setText(usuarioAutenticado.getNombre());
        txtApellido.setText(usuarioAutenticado.getApellido());
        txtEmail.setText(usuarioAutenticado.getEmail());
        txtPassword.setText(usuarioAutenticado.getPassword());

        // Bloqueamos el DNI para que no pueda actualizar datos de otro usuario
        txtDniBuscar.setEditable(false);

        // Mostramos mensaje informativo
        mostrarMensaje("Solo puede actualizar sus propios datos", true);
    }

    // Guarda los cambios realizados sobre el usuario autenticado
    @FXML
    private void onGuardarCambios() {

        // Validamos que exista un usuario autenticado
        if (usuarioAutenticado == null) {
            mostrarMensaje("No hay usuario autenticado", false);
            return;
        }

        // Creamos el servicio de usuarios
        UsuarioService usuarioService = new UsuarioService();

        // Actualizamos usando siempre el DNI del usuario autenticado
        String resultado = usuarioService.actualizarUsuario(
                usuarioAutenticado.getDni(),
                txtNombre.getText(),
                txtApellido.getText(),
                txtEmail.getText(),
                txtPassword.getText()
        );

        // Si el servicio devuelve OK, la actualización ha sido correcta
        if (resultado.equals("OK")) {

            // Actualizamos también el objeto en memoria
            usuarioAutenticado.setNombre(txtNombre.getText());
            usuarioAutenticado.setApellido(txtApellido.getText());
            usuarioAutenticado.setEmail(txtEmail.getText());
            usuarioAutenticado.setPassword(txtPassword.getText());

            mostrarMensaje("Usuario actualizado correctamente", true);

        } else {
            mostrarMensaje(resultado, false);
        }
    }

    // Restaura los datos actuales guardados en el usuario autenticado
    @FXML
    private void onLimpiarFormulario() {

        // Si no hay usuario autenticado, no se puede restaurar nada
        if (usuarioAutenticado == null) {
            mostrarMensaje("No hay usuario autenticado", false);
            return;
        }

        // Restauramos los datos del usuario autenticado
        txtDniBuscar.setText(usuarioAutenticado.getDni());
        txtNombre.setText(usuarioAutenticado.getNombre());
        txtApellido.setText(usuarioAutenticado.getApellido());
        txtEmail.setText(usuarioAutenticado.getEmail());
        txtPassword.setText(usuarioAutenticado.getPassword());

        // Limpiamos el mensaje y sus estilos
        lblMensaje.setText("");
        lblMensaje.getStyleClass().removeAll("mensaje-exito", "mensaje-error");

        // Devolvemos el foco al campo nombre
        txtNombre.requestFocus();
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