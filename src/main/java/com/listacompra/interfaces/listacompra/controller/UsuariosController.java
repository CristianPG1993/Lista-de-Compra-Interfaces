package com.listacompra.interfaces.listacompra.controller;

import com.listacompra.interfaces.listacompra.service.UsuarioService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class UsuariosController {

    @FXML
    private Button btnCrearUsuario;
    private MainController mainController;


    @FXML
    private void onCrearUsuario() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/registro-usuario-view.fxml"));
            Parent vista = loader.load();

            btnCrearUsuario.getScene().setRoot(vista);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onActualizarUsuario(){

    }

    @FXML
    public void onEliminarUsuario() {
    }


    @FXML
    private void onVolver(){}


    //Setter para preparar la comunicación entre Maincontroller y UsuariosController
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


}
