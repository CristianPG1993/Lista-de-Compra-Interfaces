package com.listacompra.interfaces.listacompra.service;

import com.listacompra.interfaces.listacompra.dao.ItemListaDao;
import com.listacompra.interfaces.listacompra.dao.ListaCompraDao;
import com.listacompra.interfaces.listacompra.dao.ProductoDao;
import com.listacompra.interfaces.listacompra.dao.UsuarioDao;
import com.listacompra.interfaces.listacompra.model.ItemLista;
import com.listacompra.interfaces.listacompra.model.ListaCompra;
import com.listacompra.interfaces.listacompra.model.Producto;
import com.listacompra.interfaces.listacompra.model.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ListaCompraService {

    // Mé_todo que crea una nueva lista de compra asociada a un usuario
    // Primero valida el DNI y el nombre de la lista, después inserta la lista en la base de datos
    public void crearListaCompra(String dni, String nombreLista) {

        // Validar que el DNI no esté vacío
        if (dni.isEmpty()) {
            System.out.println("El DNI no puede estar vacío.");
            return;
        }

        // Buscar al usuario en la base de datos a partir de su DNI
        Usuario usuario = UsuarioDao.buscarUsuarioPorDni(dni);

        // Si no existe el usuario, se informa y se cancela la operación
        if (usuario == null) {
            System.out.println("No se encontró ningún usuario con ese DNI.");
            return;
        }

        // Validar que el nombre de la lista no esté vacío
        if (nombreLista.isEmpty()) {
            System.out.println("El nombre de la lista no puede estar vacío.");
            return;
        }

        // Crear el objeto ListaCompra con la fecha actual
        ListaCompra lista = new ListaCompra(usuario, nombreLista, LocalDate.now());

        // Insertar la lista en la base de datos
        ListaCompraDao.insertarListaCompra(lista);

        // Mostrar mensaje de confirmación
        System.out.println("Lista creada correctamente.");
    }

    // Mé_todo que obtiene todas las listas de compra de un usuario a partir de su DNI
    // Devuelve una lista vacía si el DNI no es válido o si el usuario no existe
    public List<ListaCompra> obtenerListasPorDni(String dni) {

        // Validar que el DNI no esté vacío
        if (dni.isEmpty()) {
            System.out.println("El DNI no puede estar vacío.");
            return List.of();
        }

        // Buscar al usuario por DNI
        Usuario usuario = UsuarioDao.buscarUsuarioPorDni(dni);

        // Si el usuario no existe, devolver lista vacía
        if (usuario == null) {
            System.out.println("No se encontró ningún usuario con ese DNI.");
            return List.of();
        }

        // Devolver todas las listas asociadas al usuario
        return ListaCompraDao.listarListasPorUsuario(usuario.getId());
    }

    // Mé_todo que actualiza el nombre de una lista de compra seleccionada por posición
    // Valida el DNI, la existencia del usuario, la lista seleccionada y el nuevo nombre
    public void actualizarListaCompra(String dni, int indiceLista, String nuevoNombre) {

        // Validar que el DNI no esté vacío
        if (dni.isEmpty()) {
            System.out.println("El DNI no puede estar vacío.");
            return;
        }

        // Buscar al usuario en la base de datos
        Usuario usuario = UsuarioDao.buscarUsuarioPorDni(dni);

        // Si no existe, cancelar la operación
        if (usuario == null) {
            System.out.println("No se encontró ningún usuario con ese DNI.");
            return;
        }

        // Obtener todas las listas del usuario
        List<ListaCompra> listas = ListaCompraDao.listarListasPorUsuario(usuario.getId());

        // Comprobar si el usuario tiene listas
        if (listas.isEmpty()) {
            System.out.println("Este usuario no tiene listas de compra.");
            return;
        }

        // Validar que el índice seleccionado esté dentro del rango correcto
        if (indiceLista < 1 || indiceLista > listas.size()) {
            System.out.println("Opción no válida.");
            return;
        }

        // Validar que el nuevo nombre no esté vacío
        if (nuevoNombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }

        // Obtener la lista seleccionada
        ListaCompra listaSeleccionada = listas.get(indiceLista - 1);

        // Asignar el nuevo nombre a la lista
        listaSeleccionada.setNombreCompra(nuevoNombre);

        // Actualizar la lista en la base de datos
        ListaCompraDao.actualizarListaCompra(listaSeleccionada);
    }

    // Mé_todo que elimina una lista de compra seleccionada por posición
    // Valida primero el usuario y que la lista exista
    public void eliminarListaCompra(String dni, int indiceLista) {

        // Validar que el DNI no esté vacío
        if (dni.isEmpty()) {
            System.out.println("El DNI no puede estar vacío.");
            return;
        }

        // Buscar al usuario por DNI
        Usuario usuario = UsuarioDao.buscarUsuarioPorDni(dni);

        // Si no existe, cancelar la operación
        if (usuario == null) {
            System.out.println("No se encontró ningún usuario con ese DNI.");
            return;
        }

        // Obtener las listas del usuario
        List<ListaCompra> listas = ListaCompraDao.listarListasPorUsuario(usuario.getId());

        // Comprobar si el usuario tiene listas
        if (listas.isEmpty()) {
            System.out.println("Este usuario no tiene listas de compra.");
            return;
        }

        // Validar que la opción seleccionada sea correcta
        if (indiceLista < 1 || indiceLista > listas.size()) {
            System.out.println("Opción no válida.");
            return;
        }

        // Obtener la lista seleccionada
        ListaCompra listaSeleccionada = listas.get(indiceLista - 1);

        // Eliminar la lista de la base de datos
        ListaCompraDao.eliminarListaCompra(listaSeleccionada.getIdLista());
    }

    // Mé_todo que obtiene todos los productos disponibles de la base de datos
    // Se usa al añadir productos a una lista
    public List<Producto> obtenerProductosDisponibles() {
        return ProductoDao.listarProductos();
    }

    // Mé_todo que obtiene todos los productos (items) de una lista concreta
    // Se usa desde el menú para mostrar qué productos tiene la lista seleccionada
    public List<ItemLista> obtenerItemsDeLista(String dni, int indiceLista) {

        // Validar que el DNI no esté vacío
        if (dni.isEmpty()) {
            System.out.println("El DNI no puede estar vacío.");
            return List.of();
        }

        // Buscar al usuario por su DNI
        Usuario usuario = UsuarioDao.buscarUsuarioPorDni(dni);

        // Si el usuario no existe, devolver lista vacía
        if (usuario == null) {
            System.out.println("No se encontró ningún usuario con ese DNI.");
            return List.of();
        }

        // Obtener las listas de compra asociadas al usuario
        List<ListaCompra> listas = ListaCompraDao.listarListasPorUsuario(usuario.getId());

        // Comprobar si el usuario tiene listas
        if (listas.isEmpty()) {
            System.out.println("Este usuario no tiene listas de compra.");
            return List.of();
        }

        // Validar que la lista seleccionada exista
        if (indiceLista < 1 || indiceLista > listas.size()) {
            System.out.println("Opción no válida.");
            return List.of();
        }

        // Obtener la lista seleccionada
        ListaCompra listaSeleccionada = listas.get(indiceLista - 1);

        // Devolver todos los items de esa lista
        return ItemListaDao.listarItemsPorLista(listaSeleccionada.getIdLista());
    }

    // Mé_todo que marca como comprado un producto concreto dentro de una lista
    // El usuario selecciona primero la lista y después el producto de esa lista
    public void marcarComoComprado(String dni, int indiceLista, int indiceItem) {

        // Validar que el DNI no esté vacío
        if (dni.isEmpty()) {
            System.out.println("El DNI no puede estar vacío.");
            return;
        }

        // Buscar al usuario por DNI
        Usuario usuario = UsuarioDao.buscarUsuarioPorDni(dni);

        // Si no existe el usuario, cancelar operación
        if (usuario == null) {
            System.out.println("No se encontró ningún usuario con ese DNI.");
            return;
        }

        // Obtener las listas del usuario
        List<ListaCompra> listas = ListaCompraDao.listarListasPorUsuario(usuario.getId());

        // Comprobar si el usuario tiene listas
        if (listas.isEmpty()) {
            System.out.println("Este usuario no tiene listas de compra.");
            return;
        }

        // Validar que la lista seleccionada sea correcta
        if (indiceLista < 1 || indiceLista > listas.size()) {
            System.out.println("Opción no válida.");
            return;
        }

        // Obtener la lista seleccionada
        ListaCompra listaSeleccionada = listas.get(indiceLista - 1);

        // Obtener los items de esa lista
        List<ItemLista> items = ItemListaDao.listarItemsPorLista(listaSeleccionada.getIdLista());

        // Comprobar si la lista tiene productos
        if (items.isEmpty()) {
            System.out.println("La lista no tiene productos.");
            return;
        }

        // Validar que el producto seleccionado exista dentro de la lista
        if (indiceItem < 1 || indiceItem > items.size()) {
            System.out.println("Opción no válida.");
            return;
        }

        // Obtener el item seleccionado
        ItemLista itemSeleccionado = items.get(indiceItem - 1);

        // Marcar el item como comprado en la base de datos
        ItemListaDao.marcarComoComprado(itemSeleccionado.getIdItem());

        // Mensaje de confirmación
        System.out.println("Producto marcado como comprado.");
    }

    // Mé_todo que añade un producto a una lista de compra
    // Valida usuario, lista, producto y cantidad antes de insertar el item
    public void anadirProductoALista(String dni, int indiceLista, int indiceProducto, int cantidad) {

        // Validar que el DNI no esté vacío
        if (dni.isEmpty()) {
            System.out.println("El DNI no puede estar vacío.");
            return;
        }

        // Buscar al usuario por DNI
        Usuario usuario = UsuarioDao.buscarUsuarioPorDni(dni);

        // Si no existe el usuario, cancelar la operación
        if (usuario == null) {
            System.out.println("No se encontró ningún usuario con ese DNI.");
            return;
        }

        // Obtener las listas del usuario
        List<ListaCompra> listas = ListaCompraDao.listarListasPorUsuario(usuario.getId());

        // Comprobar si el usuario tiene listas
        if (listas.isEmpty()) {
            System.out.println("Este usuario no tiene listas de compra.");
            return;
        }

        // Validar que la lista seleccionada exista
        if (indiceLista < 1 || indiceLista > listas.size()) {
            System.out.println("Opción no válida.");
            return;
        }

        // Obtener todos los productos disponibles
        List<Producto> productos = ProductoDao.listarProductos();

        // Comprobar si hay productos en la base de datos
        if (productos.isEmpty()) {
            System.out.println("No hay productos disponibles.");
            return;
        }

        // Validar que el producto seleccionado exista
        if (indiceProducto < 1 || indiceProducto > productos.size()) {
            System.out.println("Opción no válida.");
            return;
        }

        // Validar que la cantidad sea mayor que 0
        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que 0.");
            return;
        }

        // Obtener la lista seleccionada
        ListaCompra listaSeleccionada = listas.get(indiceLista - 1);

        // Obtener el producto seleccionado
        Producto productoSeleccionado = productos.get(indiceProducto - 1);

        // Crear el item que se insertará en la lista
        ItemLista itemLista = new ItemLista(
                productoSeleccionado,
                listaSeleccionada,
                cantidad,
                productoSeleccionado.getPrecio(),
                false
        );

        // Insertar el nuevo item en la base de datos
        ItemListaDao.insertarItemLista(itemLista);

        // Mostrar confirmación
        System.out.println("Producto añadido a la lista.");
    }

    // Mé_todo que muestra por consola el contenido completo de una lista de compra
    // Incluye nombre de lista, usuario, productos y total acumulado
    public void mostrarListaCompleta(String dni, int indiceLista) {

        // Validar que el DNI no esté vacío
        if (dni.isEmpty()) {
            System.out.println("El DNI no puede estar vacío.");
            return;
        }

        // Buscar al usuario por DNI
        Usuario usuario = UsuarioDao.buscarUsuarioPorDni(dni);

        // Si el usuario no existe, cancelar la operación
        if (usuario == null) {
            System.out.println("No se encontró ningún usuario con ese DNI.");
            return;
        }

        // Obtener las listas del usuario
        List<ListaCompra> listas = ListaCompraDao.listarListasPorUsuario(usuario.getId());

        // Comprobar si tiene listas
        if (listas.isEmpty()) {
            System.out.println("Este usuario no tiene listas de compra.");
            return;
        }

        // Validar la posición elegida
        if (indiceLista < 1 || indiceLista > listas.size()) {
            System.out.println("Opción no válida.");
            return;
        }

        // Obtener la lista seleccionada
        ListaCompra listaSeleccionada = listas.get(indiceLista - 1);

        // Obtener los items de esa lista
        List<ItemLista> items = ItemListaDao.listarItemsPorLista(listaSeleccionada.getIdLista());

        // Calcular el total de la lista
        BigDecimal total = ItemListaDao.calcularPrecioTotalLista(listaSeleccionada.getIdLista());

        // Mostrar cabecera de la lista
        System.out.println("\n=== LISTA DE LA COMPRA ===");
        System.out.println("Lista: " + listaSeleccionada.getNombreCompra());
        System.out.println("Usuario: " + usuario.getNombre() + " " + usuario.getApellido());

        // Si no hay productos, informarlo
        if (items.isEmpty()) {
            System.out.println("La lista no tiene productos.");
        } else {
            // Recorrer y mostrar todos los items de la lista
            for (ItemLista item : items) {

                // Calcular subtotal del producto (precio unitario * cantidad)
                BigDecimal subtotal = item.getPrecioUnitario()
                        .multiply(BigDecimal.valueOf(item.getCantidad()));

                // Mostrar información de cada item
                System.out.println("- " + item.getProducto().getNombre()
                        + " x" + item.getCantidad()
                        + " -> " + subtotal + "€"
                        + " | Comprado: " + (item.isComprado() ? "✔" : "✘"));
            }
        }

        // Mostrar total final
        System.out.println("Total: " + total + "€");
    }

    // Mé_todo que muestra una lista de compra ordenada por precio unitario
    // Se utiliza como funcionalidad adicional del proyecto
    public void mostrarListaOrdenadaPorPrecio(String dni, int indiceLista) {

        // Validar que el DNI no esté vacío
        if (dni.isEmpty()) {
            System.out.println("El DNI no puede estar vacío.");
            return;
        }

        // Buscar al usuario por DNI
        Usuario usuario = UsuarioDao.buscarUsuarioPorDni(dni);

        // Si no existe el usuario, cancelar
        if (usuario == null) {
            System.out.println("No se encontró ningún usuario con ese DNI.");
            return;
        }

        // Obtener las listas del usuario
        List<ListaCompra> listas = ListaCompraDao.listarListasPorUsuario(usuario.getId());

        // Comprobar si tiene listas
        if (listas.isEmpty()) {
            System.out.println("Este usuario no tiene listas de compra.");
            return;
        }

        // Validar que la lista seleccionada exista
        if (indiceLista < 1 || indiceLista > listas.size()) {
            System.out.println("Opción no válida.");
            return;
        }

        // Obtener la lista seleccionada
        ListaCompra listaSeleccionada = listas.get(indiceLista - 1);

        // Obtener los productos de la lista
        List<ItemLista> items = ItemListaDao.listarItemsPorLista(listaSeleccionada.getIdLista());

        // Si está vacía, informar al usuario
        if (items.isEmpty()) {
            System.out.println("La lista no tiene productos.");
            return;
        }

        // Ordenar los items por precio unitario de menor a mayor
        items.sort((a, b) -> a.getPrecioUnitario().compareTo(b.getPrecioUnitario()));

        // Mostrar cabecera
        System.out.println("\n=== LISTA ORDENADA POR PRECIO ===");

        // Recorrer la lista ya ordenada y mostrar sus datos
        for (ItemLista item : items) {

            // Calcular subtotal del item
            BigDecimal subtotal = item.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(item.getCantidad()));

            // Mostrar información del producto
            System.out.println("- " + item.getProducto().getNombre()
                    + " | Precio: " + item.getPrecioUnitario() + "€"
                    + " | Cantidad: " + item.getCantidad()
                    + " | Subtotal: " + subtotal + "€");
        }
    }
}