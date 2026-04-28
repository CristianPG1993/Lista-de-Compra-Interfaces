package com.listacompra.interfaces.listacompra.service;

import com.listacompra.interfaces.listacompra.dao.ProductoDao;
import com.listacompra.interfaces.listacompra.model.Producto;

import java.math.BigDecimal;
import java.util.List;

public class ProductoService {

    // Mé_todo que crea un nuevo producto en la base de datos
    // Devuelve un String para que la interfaz gráfica pueda mostrar el resultado
    public String crearProducto(String nombre, BigDecimal precio, String categoria) {

        // Validar que el nombre no esté vacío
        if (nombre.isEmpty()) {
            return "El nombre no puede estar vacío.";
        }

        // Validar que el precio sea mayor que 0
        if (precio.compareTo(BigDecimal.ZERO) <= 0) {
            return "El precio debe ser mayor que 0.";
        }

        // Validar que la categoría no esté vacía ni sea nula
        if (categoria.isEmpty() || categoria == null) {
            return "La categoría no puede estar vacía.";
        }

        // Crear objeto Producto con los datos introducidos
        Producto producto = new Producto(nombre, precio, categoria);

        // Insertar el producto en la base de datos
        ProductoDao.insertarProducto(producto);

        return "OK";
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

    // Mé_todo que elimina un producto usando directamente su ID.
    public String eliminarProductoPorId(int idProducto) {

        // Validamos que el ID recibido sea válido.
        if (idProducto <= 0) {
            return "Producto no válido.";
        }

        // Buscamos el producto en la base de datos para comprobar que existe.
        Producto producto = ProductoDao.buscarProductoPorId(idProducto);

        // Si no existe ningún producto con ese ID, devolvemos mensaje de error.
        if (producto == null) {
            return "No se encontró el producto.";
        }

        // Eliminamos el producto usando el DAO.
        ProductoDao.eliminarProducto(idProducto);

        // Devolvemos OK para que el controlador pueda mostrar mensaje de éxito.
        return "OK";
    }
}