package com.listacompra.interfaces.listacompra.model;

import java.time.LocalDate;

// Clase que representa una lista de compra del sistema
public class ListaCompra {

    // Identificador único de la lista (PK en la base de datos)
    private int idLista;

    // Objeto Usuario asociado a la lista (relación con la tabla usuarios)
    // IMPORTANTE: en la base de datos se guarda solo el idUsuario
    private Usuario usuario;

    // Nombre de la lista (ej: "Compra semanal")
    private String nombreCompra;

    // Fecha en la que se creó la lista
    private LocalDate fechaCreacion;

    // Constructor vacío (necesario para frameworks o creación manual sin datos)
    public ListaCompra() {}

    // Constructor completo (usado cuando se obtiene la lista desde la base de datos)
    public ListaCompra(int idLista, Usuario usuario, String nombreCompra, LocalDate fechaCreacion) {
        this.idLista = idLista;
        this.usuario = usuario;
        this.nombreCompra = nombreCompra;
        this.fechaCreacion = fechaCreacion;
    }

    // Constructor sin id (usado para insertar nuevas listas en la base de datos)
    // El id se genera automáticamente en la BD
    public ListaCompra(Usuario usuario, String nombreCompra, LocalDate fechaCreacion) {
        this.usuario = usuario;
        this.nombreCompra = nombreCompra;
        this.fechaCreacion = fechaCreacion;
    }

    // ====================
    // GETTERS
    // ====================

    // Devuelve el id de la lista
    public int getIdLista() {
        return idLista;
    }

    // Devuelve el objeto Usuario asociado a la lista
    public Usuario getUsuario() {
        return usuario;
    }

    // Devuelve el nombre de la lista
    public String getNombreCompra() {
        return nombreCompra;
    }

    // Devuelve la fecha de creación
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    // ====================
    // SETTERS
    // ====================

    // Modifica el id de la lista
    public void setIdLista(int idLista) {
        this.idLista = idLista;
    }

    // Asigna un usuario a la lista
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // Modifica el nombre de la lista
    public void setNombreCompra(String nombreCompra) {
        this.nombreCompra = nombreCompra;
    }

    // Modifica la fecha de creación
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    // Mé_todo que devuelve una representación en texto del objeto
    // Muy útil para imprimir en consola y depurar
    @Override
    public String toString() {
        return "ListaCompra{" +
                "idLista=" + idLista +
                ", usuario=" + usuario +
                ", nombreCompra='" + nombreCompra + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}