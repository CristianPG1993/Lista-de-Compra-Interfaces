package com.listacompra.interfaces.listacompra.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    //Mé_todo para realizar la conexión con la DB
    public static Connection conectar(){

        String URL = "jdbc:postgresql://localhost:5432/listacompra";
        String USER = "postgres";
        String PASSWORD = "";

        //Comprueba que las variables no sean erróneas
        if(URL == null || USER == null){
            throw new RuntimeException("Error en la url o en el usuario de la BD.");

        }

        //Inicializamos la conexión
        Connection conexion = null;

        //Se inicia la conexión, si no se puede establecer la conexión genera un error
        try{
            conexion = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Conexión establecida con la base de datos.");
        }catch(SQLException e){
            e.printStackTrace();
        }

        return conexion;

    }
}
