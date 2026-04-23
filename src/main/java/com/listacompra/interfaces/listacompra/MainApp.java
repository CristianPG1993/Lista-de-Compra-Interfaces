package com.listacompra.interfaces.listacompra;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.image.Image;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 550);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        stage.setTitle("Lista de la Compra");

        //Icono de la ventana
        stage.getIcons().add(
                new Image(getClass().getResource("/images/logoCompra.png").toExternalForm())
        );

        stage.setResizable(false);
        stage.centerOnScreen();

        stage.setScene(scene);
        stage.show();
    }
}