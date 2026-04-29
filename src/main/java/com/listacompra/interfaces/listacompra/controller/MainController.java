package com.listacompra.interfaces.listacompra.controller;


import com.listacompra.interfaces.listacompra.model.Usuario;
import javafx.event.ActionEvent;
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

    public void onMostrarProductos() {

        //Actualizamos la barra de estado para informar al usuario
        lblEstado.setText("Módulo de productos cargado");

        // Intentamos cargar la vista de productos dentro del panel central
        try{
            // Creamos el cargador FXML apuntando a productos-view.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/productos-view.fxml"));

            // Cargamos la vista y la guardamos como nodo raíz
            Parent vista = loader.load();

            // Limpiamos el contenido actual del panel central
            contenidoCentral.getChildren().clear();

            //Centramos la vista dentro del StackPane central
            StackPane.setAlignment(vista, Pos.CENTER);

            // Añadimos la vista de productos al panel central
            contenidoCentral.getChildren().add(vista);
        } catch (IOException e) {

            // Mostramos la traza del error para depuración
            e.printStackTrace();

            // Informamos al usuario de que la vista no se ha podido cargar
            lblEstado.setText("Error al cargar la vista de productos");
        }
    }

    @FXML
    private void onMostrarListasCompra(){

        // Actualizamos la barra de estado para informar al usuario
        lblEstado.setText("Módulo de listas de compra cargado");

        // Intentamos cargar la vista de listas de compra dentro del panel central
        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/listas-compra-view.fxml"));

            Parent vista = loader.load();

            // Obtenemos el controlador de la vista
            ListasCompraController listasCompraController = loader.getController();

            // Pasamos al controlador el usuario que ha iniciado sesión
            listasCompraController.setUsuarioAutenticado(usuarioAutenticado);

            contenidoCentral.getChildren().clear();

            StackPane.setAlignment(vista, Pos.CENTER);

            contenidoCentral.getChildren().add(vista);
        } catch (IOException e) {
            e.printStackTrace();

            lblEstado.setText("No se ha podido cargar la vista de las tablas");
        }
    }

    @FXML
    private void onMostrarAnadirProductoLista() {

        // Actualizamos la barra de estado para informar al usuario
        lblEstado.setText("Módulo de Añadir producto a la lista cargado");

        // Intentamos cargar la vista de listas de compra dentro del panel central
        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/anadir-producto-lista-view.fxml"));

            Parent vista = loader.load();

            // Obtenemos el controlador de la vista
            AnadirProductosListaController controller = loader.getController();


            // Pasamos al controlador el usuario que ha iniciado sesión
            controller.setUsuarioAutenticado(usuarioAutenticado);

            contenidoCentral.getChildren().clear();

            StackPane.setAlignment(vista, Pos.CENTER);

            contenidoCentral.getChildren().add(vista);
        } catch (IOException e) {
            e.printStackTrace();

            lblEstado.setText("No se ha podido cargar la vista de añadir producto a lista");
        }
    }

    @FXML
    private void onMostrarHacerCompra(){
        // Actualizamos la barra de estado para informar al usuario
        lblEstado.setText("Módulo de Hacer la compra cargado");

        // Intentamos cargar la vista de hacer la compra dentro del panel central
        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/hacer-compra-view.fxml"));

            Parent vista = loader.load();

            // Obtenemos el controlador de la vista
            HacerCompraController controller = loader.getController();


            // Pasamos al controlador el usuario que ha iniciado sesión
            controller.setUsuarioAutenticado(usuarioAutenticado);

            contenidoCentral.getChildren().clear();

            StackPane.setAlignment(vista, Pos.CENTER);

            contenidoCentral.getChildren().add(vista);
        } catch (IOException e) {
            e.printStackTrace();

            lblEstado.setText("No se ha podido cargar la vista de hacer la compra");
        }
    }
}