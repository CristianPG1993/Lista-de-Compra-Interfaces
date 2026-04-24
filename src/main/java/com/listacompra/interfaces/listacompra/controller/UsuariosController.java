package com.listacompra.interfaces.listacompra.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class UsuariosController {

    @FXML
    private VBox rootUsuarios;
    @FXML
    private Button btnActualizarUsuario;


    @FXML
    private void onActualizarUsuario() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/actualizar_usuario-view.fxml"));

            Parent vista = loader.load();

            StackPane contenedorCentral = (StackPane) rootUsuarios.getParent();
            contenedorCentral.getChildren().clear();
            StackPane.setAlignment(vista, Pos.TOP_CENTER);
            contenedorCentral.getChildren().add(vista);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void onEliminarUsuario() {

    }

}