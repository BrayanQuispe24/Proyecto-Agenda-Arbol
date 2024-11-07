/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Excepciones.ExcepcionDatoNoExiste;

/**
 *
 * @author BRAYAN
 * @param <T>
 */
public class AVLP<T extends Comparable<T>> extends AVL<T> {

    public void eliminarP(T datoAEliminar) {
        this.raiz = this.eliminar(this.raiz, datoAEliminar);
    }

    private NodoBinario<T> eliminar(NodoBinario<T> nodoAux, T datoAEliminar) {
        //caso base cuando el nodo es vacio 
        if (NodoBinario.esNodoVacio(nodoAux)) {
            throw new ExcepcionDatoNoExiste();
        }
        //comenzamos el recorrido 
        T datoActual = nodoAux.getDato();

        int comparacion = datoActual.compareTo(datoAEliminar);
        if (comparacion > 0) {
            NodoBinario<T> supuestoHijoIzquierdo = this.eliminar(nodoAux.getHijoIzquierdo(), datoAEliminar);
            nodoAux.setHijoIzquierdo(supuestoHijoIzquierdo);
            return balancearP(nodoAux);
        }
        if (comparacion < 0) {
            NodoBinario<T> supuestoHijoDerecho = this.eliminar(nodoAux.getHijoDerecho(), datoAEliminar);
            nodoAux.setHijoDerecho(supuestoHijoDerecho);
            return balancearP(nodoAux);
        }
        //si llegar aqui es que lo encontro 
        //primer caso es hoja 
        if (nodoAux.esHoja()) {
            return NodoBinario.nodoVacio();
        }
        //caso 2 cuando tiene por lo menos un hijo no vacio 
        if (!nodoAux.esVacioHijoDerecho() && nodoAux.esVacioHijoDerecho()) {
            return balancearP(nodoAux.getHijoDerecho());
        }
        if (!nodoAux.esVacioHijoIzquierdo() && nodoAux.esVacioHijoDerecho()) {
            return balancearP(nodoAux.getHijoIzquierdo());
        }
        //caso 3 cuando tiene dos nodos no vacios 
        T datoReemplazo = this.sucesorInOrden(nodoAux.getHijoDerecho());
        NodoBinario<T> supuestoHijoDerecho = this.eliminar(nodoAux.getHijoDerecho(), datoReemplazo);
        nodoAux.setHijoDerecho(supuestoHijoDerecho);
        nodoAux.setDato(datoReemplazo);
        return balancearP(nodoAux);
    }

    private NodoBinario<T> balancearP(NodoBinario<T> nodoAux) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private T sucesorInOrden(NodoBinario<T> hijoDerecho) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
