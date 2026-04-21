package com.listacompra.interfaces.listacompra.model;

public class Usuario {

    private int id;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String password;

    //Permite crear objetos Usuario
    public Usuario(){}

    //Constructor sin id
    public Usuario(String dni, String nombre, String apellido, String email, String password) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
    }

    //Constructor con id
    public Usuario(int id, String dni, String nombre, String apellido, String email, String password) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
    }

    //====================
    // GETTERS
    //====================

    //Getter para devolver el id del usuario
    public int getId() {
        return id;
    }

    //Getter para devolver el nombre del usuario
    public String getNombre() {
        return nombre;
    }

    //Getter para devolver el apellido del usuario
    public String getApellido() {
        return apellido;
    }

    //Getter para devolver el dni del usuario
    public String getDni() {
        return dni;
    }

    //Getter para devolver el email del usuario
    public String getEmail() {
        return email;
    }

    //Getter para devolver la contraseña del usuario
    public String getPassword() {
        return password;
    }


    //====================
    // SETTERS
    //====================

    public void setId(int id) {
        this.id = id;
    }

    //Setter para cambiar el nombre del usuario
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //Setter para cambiar el apellido del usuario
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    //Setter para cambiar el dni del usuario
    public void setDni(String dni) {
        this.dni = dni;
    }

    //Setter para cambiar el email del usuario
    public void setEmail(String email) {
        this.email = email;
    }

    //Setter para cambiar el password del usuario
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
