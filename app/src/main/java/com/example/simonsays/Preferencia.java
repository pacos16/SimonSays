package com.example.simonsays;
public class Preferencia{
    private int posicion;
    private String nombre;
    private int puntuacion;

    public Preferencia(int posicion, String nombre, int puntuacion) {
        this.posicion = posicion;
        this.nombre = nombre;
        this.puntuacion = puntuacion;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }


}