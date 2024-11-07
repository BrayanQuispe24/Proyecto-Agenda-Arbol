/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

/**
 *
 * @author BRAYAN
 */
public class Persona implements Comparable<Persona> {

    //atributos
    private int numero;
    private String nombre;
    private String direccion;

    public Persona(int numero, String nombre, String direccion) {
        this.numero = numero;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    @Override
    public int compareTo(Persona otraPersona) {
        if (this.numero == otraPersona.numero) {
            return 0;
        }
        return this.numero < otraPersona.numero ? -1 : 1;
    }

    @Override
    public String toString() {
        String PersonaAMostrar = "Nombre: " + this.getNombre() + "\n"
                + "Numero de celular: " + this.getNumero() + "\n"
                + "Direccion: " + this.direccion;
        return PersonaAMostrar;
    }

}
