/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author BRAYAN
 * @param <T>
 */
public class PracticaMVias<T extends Comparable<T>> extends ArbolMViasBusqueda<T> {

    //vamos a implementar el recorrido por niveles 
    public List<T> recorridoPorNivelesP() {
        List<T> listaDeDatos = new LinkedList<>();
        if (!this.esArbolVacio()) {
            Queue<NodoMVias<T>> colaDeNodos = new LinkedList<>();
            NodoMVias<T> nodoAux;
            colaDeNodos.offer(this.raiz);
            do {
                nodoAux = colaDeNodos.poll();
                for (int i = 0; i < nodoAux.numeroDeDatosNoVacios(); i++) {
                    listaDeDatos.add(nodoAux.getDato(i));
                    if (!nodoAux.esHijoVacio(i)) {
                        colaDeNodos.offer(nodoAux.getHijo(i));
                    }
                }
                //falta sacar el ultimo hjo
                if (!nodoAux.esHijoVacio(nodoAux.numeroDeDatosNoVacios())) {
                    colaDeNodos.offer(nodoAux.getHijo(nodoAux.numeroDeDatosNoVacios()));
                }
            } while (!colaDeNodos.isEmpty());
        }
        return listaDeDatos;
    }

    public List<T> recorridoInOrdenP() {
        List<T> listaDeDatos = new LinkedList<>();
        this.recorridoInOrdenP(this.raiz, listaDeDatos);
        return listaDeDatos;
    }

    private void recorridoInOrdenP(NodoMVias<T> nodoAux, List<T> listaDeDatos) {
        //caso base cuando el nodos es vacio
        if (NodoMVias.esNodoVacio(nodoAux)) {
            return;
        }
        //comenzamos recorrido 
        for (int i = 0; i < nodoAux.numeroDeDatosNoVacios(); i++) {
            this.recorridoInOrdenP(nodoAux.getHijo(i), listaDeDatos);
            listaDeDatos.add(nodoAux.getDato(i));
        }
        //falta visitar el ultimo nodo
        if (!nodoAux.esHijoVacio(nodoAux.numeroDeDatosNoVacios())) {
            recorridoInOrdenP(nodoAux.getHijo(nodoAux.numeroDeDatosNoVacios()),
                     listaDeDatos);
        }
    }

    public int nivelP() {
        int nivel = -1;
        if (!this.esArbolVacio()) {
            Queue<NodoMVias<T>> colaDeNodos = new LinkedList<>();
            NodoMVias<T> nodoAux;
            colaDeNodos.offer(this.raiz);
            do {
                int cantidadDeNodos = colaDeNodos.size();
                nivel++;
                for (int i = 0; i < cantidadDeNodos; i++) {
                    nodoAux = colaDeNodos.poll();
                    for (int j = 0; j < nodoAux.numeroDeDatosNoVacios(); j++) {
                        if (!nodoAux.esHijoVacio(j)) {
                            colaDeNodos.offer(nodoAux.getHijo(j));
                        }
                    }
                    if (!nodoAux.esHijoVacio(nodoAux.numeroDeDatosNoVacios())) {
                        colaDeNodos.offer(nodoAux.getHijo(nodoAux.numeroDeDatosNoVacios()));
                    }
                }
            } while (!colaDeNodos.isEmpty());
        }
        return nivel;
    }
    //vamos a implementar el eliminar avl 
    

}
