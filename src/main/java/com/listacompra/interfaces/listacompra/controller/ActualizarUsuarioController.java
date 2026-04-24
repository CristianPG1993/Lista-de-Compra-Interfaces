package com.listacompra.interfaces.listacompra.controller;

import com.listacompra.interfaces.listacompra.dao.UsuarioDao;
import com.listacompra.interfaces.listacompra.model.Usuario;
import com.listacompra.interfaces.listacompra.service.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ActualizarUsuarioController {

    @FXML
    private TextField txtDniBuscar;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Label lblMensaje;

    @FXML
    private void onBuscarUsuario() {

        // Obtenemos el DNI introducido en el campo de búsqueda.
        String dni = txtDniBuscar.getText();

        // Validamos que el campo DNI no esté vacío.
        if (dni == null || dni.isEmpty()) {

            // Mostramos un mensaje de error en la interfaz.
            lblMensaje.setText("Introduce un DNI para buscar.");

            // Quitamos el estilo de éxito por si estaba aplicado anteriormente.
            lblMensaje.getStyleClass().removeAll("mensaje-exito");

            // Aplicamos el estilo de error.
            lblMensaje.getStyleClass().add("mensaje-error");

            // Cortamos la ejecución del mé_todo porque no se puede buscar sin DNI.
            return;
        }

        // Buscamos el usuario en la base de datos usando el DAO.
        Usuario usuario = UsuarioDao.buscarUsuarioPorDni(dni);

        // Si el DAO devuelve null, significa que no existe ningún usuario con ese DNI.
        if (usuario == null) {

            // Mostramos el mensaje de error en la interfaz.
            lblMensaje.setText("No se encontró ningún usuario con ese DNI.");

            // Quitamos el estilo de éxito si estaba aplicado.
            lblMensaje.getStyleClass().removeAll("mensaje-exito");

            // Aplicamos el estilo de error.
            lblMensaje.getStyleClass().add("mensaje-error");

            // Cortamos la ejecución porque no hay datos que cargar.
            return;
        }

        // Si el usuario existe, cargamos sus datos en los campos del formulario.
        txtNombre.setText(usuario.getNombre());
        txtApellido.setText(usuario.getApellido());
        txtEmail.setText(usuario.getEmail());
        txtPassword.setText(usuario.getPassword());

        // Mostramos mensaje de éxito.
        lblMensaje.setText("Usuario encontrado.");

        // Quitamos el estilo de error si estaba aplicado.
        lblMensaje.getStyleClass().removeAll("mensaje-error");

        // Aplicamos el estilo de éxito.
        lblMensaje.getStyleClass().add("mensaje-exito");
    }

    @FXML
    private void onGuardarCambios() {

        // Creamos una instancia del servicio de usuarios.
        // El servicio contiene las validaciones y delega la actualización al DAO.
        UsuarioService usuarioService = new UsuarioService();

        // Llamamos al mé_todo actualizarUsuario usando los datos actuales del formulario.
        String resultado = usuarioService.actualizarUsuario(
                txtDniBuscar.getText(),
                txtNombre.getText(),
                txtApellido.getText(),
                txtEmail.getText(),
                txtPassword.getText()
        );

        // Si el servicio devuelve OK, la actualización ha sido correcta.
        if (resultado.equals("OK")) {

            // Mostramos mensaje de éxito.
            lblMensaje.setText("Usuario actualizado correctamente.");

            // Quitamos el estilo de error si estaba aplicado.
            lblMensaje.getStyleClass().removeAll("mensaje-error");

            // Aplicamos el estilo de éxito.
            lblMensaje.getStyleClass().add("mensaje-exito");

        } else {

            // Si el servicio devuelve otro texto, es un mensaje de error de validación.
            lblMensaje.setText(resultado);

            // Quitamos el estilo de éxito si estaba aplicado.
            lblMensaje.getStyleClass().removeAll("mensaje-exito");

            // Aplicamos el estilo de error.
            lblMensaje.getStyleClass().add("mensaje-error");
        }
    }

    @FXML
    private void onLimpiarFormulario() {

        // Limpia el campo usado para buscar el usuario por DNI.
        txtDniBuscar.clear();

        // Limpia los campos editables del formulario.
        txtNombre.clear();
        txtApellido.clear();
        txtEmail.clear();
        txtPassword.clear();

        // Limpia cualquier mensaje de éxito o error mostrado anteriormente.
        lblMensaje.setText("");

        // Elimina estilos previos del label para evitar que conserve color verde o rojo.
        lblMensaje.getStyleClass().removeAll("mensaje-exito", "mensaje-error");

        // Devuelve el foco al campo DNI para facilitar una nueva búsqueda.
        txtDniBuscar.requestFocus();
    }
}