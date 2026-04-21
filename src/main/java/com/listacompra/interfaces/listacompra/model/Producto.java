package com.listacompra.interfaces.listacompra.model;

import java.math.BigDecimal;

//Clase que representa un producto de la base de datos
public class Producto {

    //Identificador único del producto (clave primaria)
    private int idProducto;

    //Nombre del producto
    private String nombre;

    //Precio del producto (BigDecimal para evitar problemas de precisión)
    private BigDecimal precio;

    //Categoría del producto (alimentación, limpieza, etc.)
    private String categoria;

    //Constructor vacío (necesario para frameworks o creación manual)
    public Producto(){}

    //Constructor completo (usado cuando se obtiene el producto desde la base de datos)
    public Producto(int idProducto, String nombre, BigDecimal precio, String categoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    //Constructor sin id (usado para insertar un nuevo producto en la base de datos)
    public Producto(String nombre, BigDecimal precio, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    //====================
    // GETTERS
    //====================

    //Devuelve el id del producto
    public int getIdProducto() {
        return idProducto;
    }

    //Devuelve el nombre del producto
    public String getNombre() {
        return nombre;
    }

    //Devuelve el precio del producto
    public BigDecimal getPrecio() {
        return precio;
    }

    //Devuelve la categoría del producto
    public String getCategoria() {
        return categoria;
    }

    //====================
    // SETTERS
    //====================

    //Asigna el id del producto
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    //Modifica el nombre del producto
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //Modifica el precio del producto
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    //Modifica la categoría del producto
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    //Mé_todo que devuelve una representación en texto del objeto
    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}