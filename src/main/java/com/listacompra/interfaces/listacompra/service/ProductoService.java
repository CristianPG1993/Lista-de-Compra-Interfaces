package com.listacompra.interfaces.listacompra.service;

import com.listacompra.interfaces.listacompra.dao.ProductoDao;
import com.listacompra.interfaces.listacompra.model.Producto;

import java.math.BigDecimal;
import java.util.List;

public class ProductoService {

    // Mé_todo que crea un nuevo producto en la base de datos
    // Valida que los datos introducidos sean correctos antes de insertarlo
    public void crearProducto(String nombre, BigDecimal precio, String categoria) {

        // Validar que el nombre no esté vacío
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }

        // Validar que el precio sea mayor que 0
        if (precio.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("El precio debe ser mayor que 0.");
            return;
        }

        // Validar que la categoría no esté vacía
        if (categoria.isEmpty()) {
            System.out.println("La categoría no puede estar vacía.");
            return;
        }

        // Crear objeto Producto con los datos introducidos
        Producto producto = new Producto(nombre, precio, categoria);

        // Insertar el producto en la base de datos
        ProductoDao.insertarProducto(producto);

        // Mensaje de confirmación
        System.out.println("Producto creado correctamente.");
    }

    // Mé_todo que devuelve todos los productos almacenados en la base de datos
    // Se utiliza principalmente para mostrarlos en el menú
    public List<Producto> listarProductos() {
        return ProductoDao.listarProductos();
    }

    // Mé_todo que actualiza un producto existente
    // El producto se selecciona por índice (posición en la lista mostrada al usuario)
    public void actualizarProducto(int indiceSeleccionado, String nombre, BigDecimal precio, String categoria) {

        // Obtener todos los productos
        List<Producto> productos = ProductoDao.listarProductos();

        // Comprobar si hay productos registrados
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }

        // Validar que el índice seleccionado sea válido
        if (indiceSeleccionado < 1 || indiceSeleccionado > productos.size()) {
            System.out.println("Opción no válida.");
            return;
        }

        // Validar nombre
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }

        // Validar precio
        if (precio.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("El precio debe ser mayor que 0.");
            return;
        }

        // Validar categoría
        if (categoria.isEmpty()) {
            System.out.println("La categoría no puede estar vacía.");
            return;
        }

        // Obtener el producto seleccionado (restamos 1 porque la lista empieza en 0)
        Producto producto = productos.get(indiceSeleccionado - 1);

        // Actualizar sus valores
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setCategoria(categoria);

        // Guardar cambios en la base de datos
        ProductoDao.actualizarProducto(producto);
    }

    // Mé_todo que elimina un producto existente
    // El producto se selecciona por índice
    public void eliminarProducto(int indiceSeleccionado) {

        // Obtener todos los productos
        List<Producto> productos = ProductoDao.listarProductos();

        // Comprobar si hay productos registrados
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }

        // Validar que el índice seleccionado sea correcto
        if (indiceSeleccionado < 1 || indiceSeleccionado > productos.size()) {
            System.out.println("Opción no válida.");
            return;
        }

        // Obtener el producto seleccionado
        Producto producto = productos.get(indiceSeleccionado - 1);

        // Eliminar el producto usando su ID en la base de datos
        ProductoDao.eliminarProducto(producto.getIdProducto());
    }
}