package com.magdezis.model;

public class Usuario {

    private final int id;
    private final String nombre;
    private final String apellido;
    private final String correo;
    private final String contrasena;
    private final Rol rol;

    public Usuario(int id, String nombre, String apellido, String correo, String contrasena, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public Rol getRol() {
        return rol;
    }
}
