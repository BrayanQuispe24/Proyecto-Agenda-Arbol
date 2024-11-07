/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BRAYAN
 * @param <T>
 */
public class NodoMVias<T> {

    private List<T> listaDeDatos;
    private List<NodoMVias<T>> listaDeHijos;

    //constructor
    public NodoMVias(int orden, T dato) {
        this(orden);
        listaDeDatos.set(0, dato);
    }

    public NodoMVias(int orden) {
        listaDeDatos = new ArrayList<>();
        listaDeHijos = new ArrayList<>();
        //inicializamos con un dato y un nodo vacio cada casilla
        for (int i = 0; i < orden - 1; i++) {
            listaDeDatos.add((T) NodoMVias.datoVacio());
            listaDeHijos.add(NodoMVias.nodoVacio());
        }
        //aqui se añade un hijo mas porque la ultima casilla tiene dos hijos
        listaDeHijos.add(NodoMVias.nodoVacio());
    }

    public static NodoMVias nodoVacio() {
        return null;
    }

    public static Object datoVacio() {
        return null;
    }

    public static boolean esNodoVacio(NodoMVias nodo) {
        return NodoMVias.nodoVacio() == nodo;
    }

    public T getDato(int posicion) {
        return listaDeDatos.get(posicion);
    }

    public void setDato(int posicion, T dato) {
        listaDeDatos.set(posicion, dato);
    }

    public boolean esDatoVacio(int posicion) {
        return listaDeDatos.get(posicion) == NodoMVias.datoVacio();
    }

    public NodoMVias<T> getHijo(int posicion) {
        return listaDeHijos.get(posicion);
    }

    public void setHijo(int posicion, NodoMVias<T> hijo) {
        listaDeHijos.set(posicion, hijo);
    }

    public boolean esHijoVacio(int posicion) {
        return listaDeHijos.get(posicion) == NodoMVias.nodoVacio();

    }

    public boolean esHoja() {
        for (int i = 0; i < listaDeHijos.size(); i++) {
            if (!this.esHijoVacio(i)) {
                return false;
            }
        }
        return true;
    }

    public int numeroDeDatosNoVacios() {
        int cantidad = 0;
        for (int i = 0; i < this.listaDeDatos.size(); i++) {
            if (!this.esDatoVacio(i)) {
                cantidad++;
            }
        }
        return cantidad;
    }

    boolean estanDatosLlenos() {
        return this.numeroDeDatosNoVacios() == this.listaDeDatos.size();
    }

}
