package com.listacompra.interfaces.listacompra.service;

import com.listacompra.interfaces.listacompra.dao.ListaCompraDao;
import com.listacompra.interfaces.listacompra.dao.UsuarioDao;
import com.listacompra.interfaces.listacompra.model.ListaCompra;
import com.listacompra.interfaces.listacompra.model.Usuario;

import java.time.LocalDate;
import java.util.List;

// Clase que contiene la lógica de negocio relacionada con las listas de compra
public class ListaCompraService {

    // Mé_todo que crea una nueva lista de compra asociada a un usuario
    // Devuelve un String para que JavaFX pueda mostrar el resultado en pantalla
    public String crearListaCompra(String dni, String nombreLista) {

        // Validamos que el DNI no sea nulo ni esté vacío
        if (dni == null || dni.isEmpty()) {
            return "El DNI no puede estar vacío";
        }

        // Normalizamos el DNI para evitar errores por espacios o minúsculas
        dni = dni.trim().toUpperCase();

        // Buscamos al usuario en la base de datos a partir de su DNI
        Usuario usuario = UsuarioDao.buscarUsuarioPorDni(dni);

        // Si no existe el usuario, no se puede crear la lista
        if (usuario == null) {
            return "No se encontró ningún usuario con ese DNI";
        }

        // Validamos que el nombre de la lista no sea nulo ni esté vacío
        if (nombreLista == null || nombreLista.isEmpty()) {
            return "El nombre de la lista no puede estar vacío";
        }

        // Creamos el objeto ListaCompra con la fecha actual
        ListaCompra lista = new ListaCompra(usuario, nombreLista, LocalDate.now());

        // Insertamos la lista en la base de datos
        ListaCompraDao.insertarListaCompra(lista);

        // Devolvemos OK para que la interfaz muestre mensaje de éxito
        return "OK";
    }

    // Mé_todo que obtiene todas las listas de compra de un usuario a partir de su DNI
    public List<ListaCompra> obtenerListasPorDni(String dni) {

        // Validamos que el DNI no sea nulo ni esté vacío
        if (dni == null || dni.isEmpty()) {
            return List.of();
        }

        // Normalizamos el DNI
        dni = dni.trim().toUpperCase();

        // Buscamos al usuario por DNI
        Usuario usuario = UsuarioDao.buscarUsuarioPorDni(dni);

        // Si el usuario no existe, devolvemos una lista vacía
        if (usuario == null) {
            return List.of();
        }

        // Devolvemos todas las listas asociadas al usuario
        return ListaCompraDao.listarListasPorUsuario(usuario.getId());
    }

    // Mé_todo que elimina una lista de compra usando directamente su ID
    public String eliminarListaCompraPorId(String dni, int idLista) {

        // Validamos que el DNI no sea nulo ni esté vacío
        if (dni == null || dni.isEmpty()) {
            return "El DNI no puede estar vacío";
        }

        // Normalizamos el DNI para evitar errores por espacios o minúsculas
        dni = dni.trim().toUpperCase();

        // Buscamos al usuario por DNI
        Usuario usuario = UsuarioDao.buscarUsuarioPorDni(dni);

        // Si el usuario no existe, no se puede eliminar ninguna lista
        if (usuario == null) {
            return "No se encontró ningún usuario con ese DNI";
        }

        // Validamos que el ID de la lista sea correcto
        if (idLista <= 0) {
            return "Lista no válida";
        }

        // Buscamos la lista por su ID
        ListaCompra lista = ListaCompraDao.buscarListaCompraPorId(idLista);

        // Si no existe, devolvemos error
        if (lista == null) {
            return "No se encontró la lista seleccionada";
        }

        // Comprobamos que la lista pertenece al usuario autenticado
        if (lista.getUsuario().getId() != usuario.getId()) {
            return "No puedes eliminar una lista de otro usuario";
        }

        // Eliminamos la lista de la base de datos
        ListaCompraDao.eliminarListaCompra(idLista);

        // Devolvemos OK para que JavaFX pueda mostrar éxito
        return "OK";
    }
}