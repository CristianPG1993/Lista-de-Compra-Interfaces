package com.listacompra.interfaces.listacompra.model;

import java.math.BigDecimal;

//Clase que representa un item dentro de una lista de compra
public class ItemLista {

    //Identificador único del item (clave primaria en la base de datos)
    private int idItem;

    //Producto asociado al item de la lista
    private Producto producto;

    //Lista de compra a la que pertenece este item
    private ListaCompra listaCompra;

    //Cantidad de unidades del producto dentro de la lista
    private int cantidad;

    //Precio unitario del producto en el momento de añadirlo a la lista
    private BigDecimal precioUnitario;

    //Indica si el producto ya ha sido comprado o no
    private boolean comprado;

    //Constructor vacío
    public ItemLista(){}

    //Constructor completo, usado cuando se obtienen datos desde la base de datos
    public ItemLista(int idItem, Producto producto, ListaCompra listaCompra, int cantidad, BigDecimal precioUnitario, boolean comprado) {
        this.idItem = idItem;
        this.producto = producto;
        this.listaCompra = listaCompra;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.comprado = comprado;
    }

    //Constructor sin id, usado cuando se crea un nuevo item para insertarlo en la base de datos
    public ItemLista(Producto producto, ListaCompra listaCompra, int cantidad, BigDecimal precioUnitario, boolean comprado) {
        this.producto = producto;
        this.listaCompra = listaCompra;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.comprado = comprado;
    }

    //====================
    // GETTERS
    //====================

    //Devuelve el id del item
    public int getIdItem() {
        return idItem;
    }

    //Devuelve el producto asociado
    public Producto getProducto() {
        return producto;
    }

    //Devuelve la lista de compra a la que pertenece el item
    public ListaCompra getListaCompra() {
        return listaCompra;
    }

    //Devuelve la cantidad del producto
    public int getCantidad() {
        return cantidad;
    }

    //Devuelve el precio unitario del producto
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    //Devuelve si el producto está comprado o no
    public boolean isComprado() {
        return comprado;
    }

    //====================
    // SETTERS
    //====================

    //Asigna el id del item
    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    //Asigna el producto del item
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    //Asigna la lista de compra a la que pertenece el item
    public void setListaCompra(ListaCompra listaCompra) {
        this.listaCompra = listaCompra;
    }

    //Modifica la cantidad del producto
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    //Modifica el precio unitario del producto
    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    //Marca el producto como comprado o no comprado
    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }

    //Mé_todo que devuelve una representación en texto del objeto
    //Útil para imprimir en consola y hacer pruebas
    @Override
    public String toString() {
        return "ItemLista{" +
                "idItem=" + idItem +
                ", producto=" + producto +
                ", listaCompra=" + listaCompra +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", comprado=" + comprado +
                '}';
    }
}