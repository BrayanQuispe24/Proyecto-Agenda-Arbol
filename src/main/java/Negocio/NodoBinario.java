/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

/**
 *
 * @author BRAYAN
 * @param <T>
 */
public class NodoBinario<T> {

    //Atributos
    private T dato;
    private NodoBinario<T> hijoIzquierdo;
    private NodoBinario<T> hijoDerecho;
    //////////////////////

    public NodoBinario() {
    }

    public NodoBinario(T dato) {
        this.dato = dato;
    }

    public NodoBinario(T dato, NodoBinario<T> hijoIzquierdo, NodoBinario<T> hijoDerecho) {
        this.dato = dato;
        this.hijoIzquierdo = hijoIzquierdo;
        this.hijoDerecho = hijoDerecho;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public void setHijoIzquierdo(NodoBinario<T> hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public void setHijoDerecho(NodoBinario<T> hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }

    /**
     *
     * @return
     */
    public T getDato() {
        return dato;
    }

    public NodoBinario<T> getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public NodoBinario<T> getHijoDerecho() {
        return hijoDerecho;
    }

    public static NodoBinario nodoVacio() {
        return null;
    }

    public static boolean esNodoVacio(NodoBinario nodo) {
        return nodo == nodoVacio();
    }

    public boolean esVacioHijoIzquierdo() {
        return NodoBinario.esNodoVacio(this.hijoIzquierdo);
    }

    public boolean esVacioHijoDerecho() {
        return NodoBinario.esNodoVacio(this.hijoDerecho);
    }

    public boolean esHoja() {
        return (NodoBinario.esNodoVacio(this.hijoIzquierdo)
                && NodoBinario.esNodoVacio(this.hijoDerecho));
    }


    

}
