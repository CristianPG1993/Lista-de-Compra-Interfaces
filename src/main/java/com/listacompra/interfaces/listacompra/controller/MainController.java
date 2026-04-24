package com.listacompra.interfaces.listacompra.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane contenidoCentral;

    @FXML
    private Label lblEstado;

    @FXML
    private void onMostrarUsuarios(){

        lblEstado.setText("Módulo de usuario cargado");

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/usuarios-view.fxml"));
            Parent vista = loader.load();


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



}