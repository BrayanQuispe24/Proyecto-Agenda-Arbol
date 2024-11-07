/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Excepciones.ExcepcionDatoNoExiste;
import Excepciones.ExcepcionDatoYaExiste;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author BRAYAN
 * @param <T>
 */
public class ArbolBinarioBusqueda<T extends Comparable<T>> implements IArbolBusqueda<T> {

    protected NodoBinario<T> raiz;

    //como no estamos añadiendo el constructor usaremos el por defecto
    //parametros: dos listas y una bandera 
    //hacer un constructor que resiva dos recorrido un inorden y el otro puede ser cualquiera
    @Override
    public void insertar(T datoAInsertar) {
        if (this.esArbolVacio()) {
            this.raiz = new NodoBinario<>(datoAInsertar);
        } else {
            NodoBinario<T> nodoAux = this.raiz;
            NodoBinario<T> nodoAnterior = NodoBinario.nodoVacio();
            do {
                T claveDelNodoAux = nodoAux.getDato();
                //nodoAnterior = nodoAux;
                int comparacion = datoAInsertar.compareTo(claveDelNodoAux);
                //esto es para buscar el lugar donde se creara el nodo
                if (comparacion < 0) {
                    nodoAnterior = nodoAux;
                    nodoAux = nodoAux.getHijoIzquierdo();
                } else if (comparacion > 0) {
                    nodoAnterior = nodoAux;
                    nodoAux = nodoAux.getHijoDerecho();
                } else {
                    throw new ExcepcionDatoYaExiste();
                }
            } while (!NodoBinario.esNodoVacio(nodoAux));
            //si no encontramos el nodo
            NodoBinario<T> nuevoNodo = new NodoBinario<>(datoAInsertar);
            if (datoAInsertar.compareTo(nodoAnterior.getDato()) < 0) {
                nodoAnterior.setHijoIzquierdo(nuevoNodo);
            } else {
                nodoAnterior.setHijoDerecho(nuevoNodo);
            }

        }
    }

    @Override
    public void eliminar(T dato) {
        //agregar excepcion 
        this.raiz = eliminar(this.raiz, dato);
    }

    private NodoBinario<T> eliminar(NodoBinario<T> nodoActual, T datoAEliminar) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            throw new ExcepcionDatoNoExiste();
        }
        T datoActual = nodoActual.getDato();
        if (datoAEliminar.compareTo(datoActual) < 0) {
            NodoBinario<T> supuestoNuevoHI = this.eliminar(nodoActual.getHijoIzquierdo(), datoAEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHI);
            return nodoActual;
        }

        if (datoAEliminar.compareTo(datoActual) > 0) {
            NodoBinario<T> supuestoNuevoHD = this.eliminar(nodoActual.getHijoDerecho(), datoAEliminar);
            nodoActual.setHijoDerecho(supuestoNuevoHD);
            return nodoActual;
        }
        //caso1
        if (nodoActual.esHoja()) {
            return NodoBinario.nodoVacio();
        }
        //caso 2 
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoIzquierdo();
        }
        if (!nodoActual.esVacioHijoDerecho() && nodoActual.esVacioHijoIzquierdo()) {
            return nodoActual.getHijoDerecho();
        }
        //caso 3
        T reemplazo = this.obtenerSucesorInOrden(nodoActual.getHijoDerecho());
        NodoBinario<T> supuestoNuevoHD = this.eliminar(nodoActual.getHijoDerecho(), reemplazo);
        nodoActual.setHijoDerecho(supuestoNuevoHD);
        nodoActual.setDato(reemplazo);
        return nodoActual;

    }

    private T obtenerSucesorInOrden(NodoBinario<T> nodoActual) {
        while (!nodoActual.esVacioHijoIzquierdo()) {
            nodoActual.getHijoIzquierdo();
        }
        return nodoActual.getDato();
    }

    @Override
    public T buscar(T dato) {
        //si el dato no esta o el arbol esta vacio se debe devolver null
        NodoBinario<T> nodoAux = this.raiz;
        while (!NodoBinario.esNodoVacio(nodoAux)) {
            T datoDelNodoAux = nodoAux.getDato();
            int comparacion = datoDelNodoAux.compareTo(dato);
            if (comparacion < 0) {
                nodoAux = nodoAux.getHijoDerecho();
            } else if (comparacion > 0) {
                nodoAux = nodoAux.getHijoIzquierdo();
            } else {
                return datoDelNodoAux;
            }
        }
        return null;
    }
    
    @Override
    public Persona buscarPersona(T dato) {
        //si el dato no esta o el arbol esta vacio se debe devolver null
        NodoBinario<T> nodoAux = this.raiz;
        while (!NodoBinario.esNodoVacio(nodoAux)) {
            T datoDelNodoAux = nodoAux.getDato();
            int comparacion = datoDelNodoAux.compareTo(dato);
            if (comparacion < 0) {
                nodoAux = nodoAux.getHijoDerecho();
            } else if (comparacion > 0) {
                nodoAux = nodoAux.getHijoIzquierdo();
            } else {
                return (Persona) nodoAux.getDato();
            }
        }
        return null;
    }

    @Override
    public boolean contiene(T dato) {
        return this.buscar(dato) != null;
    }

    @Override
    public int size() {
        int contador = 0;
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<T>> pilaDeNodos = new Stack<>();
            NodoBinario<T> nodoAux = this.raiz;
            pilaDeNodos.push(nodoAux);
            do {
                nodoAux = pilaDeNodos.pop();
                contador++;
                if (!nodoAux.esVacioHijoDerecho()) {
                    pilaDeNodos.push(nodoAux.getHijoDerecho());
                }
                if (!nodoAux.esVacioHijoIzquierdo()) {
                    pilaDeNodos.push(nodoAux.getHijoIzquierdo());
                }
            } while (!pilaDeNodos.isEmpty());
        }
        return contador;
    }

    @Override
    public int altura() {
        int altura = 0;
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<T>> colaDeNodos = new LinkedList<>();
            NodoBinario<T> nodoActual = this.raiz;
            colaDeNodos.add(nodoActual);
            while (!colaDeNodos.isEmpty()) {
                int cantidadNodos = colaDeNodos.size();
                altura++;
                for (int i = 0; i < cantidadNodos; i++) {
                    nodoActual = colaDeNodos.poll();
                    if (!nodoActual.esVacioHijoIzquierdo()) {
                        colaDeNodos.add(nodoActual.getHijoIzquierdo());
                    }
                    if (!nodoActual.esVacioHijoDerecho()) {
                        colaDeNodos.add(nodoActual.getHijoDerecho());
                    }
                }
            }

        }
        return altura;
    }

    protected int alturaNodo(NodoBinario<T> nodoActual) {
        // Si el nodo es vacío, la altura es -1
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return -1;
        }

        Queue<NodoBinario<T>> colaDeNodos = new LinkedList<>();
        colaDeNodos.add(nodoActual);
        int altura = 0;

        // Mientras haya nodos en la cola, sigue recorriendo el árbol
        while (!colaDeNodos.isEmpty()) {
            int cantidadNodos = colaDeNodos.size();
            altura++; // Incrementa la altura por cada nivel que recorres

            // Procesar todos los nodos del nivel actual
            for (int i = 0; i < cantidadNodos; i++) {
                NodoBinario<T> nodo = colaDeNodos.poll();

                // Añade el hijo izquierdo a la cola, si no es vacío
                if (!NodoBinario.esNodoVacio(nodo.getHijoIzquierdo())) {
                    colaDeNodos.add(nodo.getHijoIzquierdo());
                }

                // Añade el hijo derecho a la cola, si no es vacío
                if (!NodoBinario.esNodoVacio(nodo.getHijoDerecho())) {
                    colaDeNodos.add(nodo.getHijoDerecho());
                }
            }
        }

        return altura;
    }

    @Override
    public void vaciar() {
        this.raiz = NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public int nivel() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<T> recorridoEnInOrden() {
        List<T> listaDeDatos = new LinkedList<>();
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<T>> pilaDeNodos = new Stack<>();
            NodoBinario<T> nodoAux = this.raiz;
            this.meterPilaInOnder(nodoAux, pilaDeNodos);
            do {
                nodoAux = pilaDeNodos.pop();
                listaDeDatos.add(nodoAux.getDato());
                if (!nodoAux.esVacioHijoDerecho()) {
                    nodoAux = nodoAux.getHijoDerecho();
                    this.meterPilaInOnder(nodoAux, pilaDeNodos);
                }
            } while (!pilaDeNodos.isEmpty());
        }
        return listaDeDatos;
    }

    private void meterPilaInOnder(NodoBinario<T> nodoAux, Stack<NodoBinario<T>> pilaDeNodos) {
        while (!NodoBinario.esNodoVacio(nodoAux)) {
            pilaDeNodos.push(nodoAux);
            nodoAux = nodoAux.getHijoIzquierdo();
        }
    }

    public List<T> recorridoEnInOrdenR() {
        List<T> listaDeDatos = new LinkedList<>();
        if (!this.esArbolVacio()) {
            recorridoEIOR(this.raiz, listaDeDatos);
        }
        return listaDeDatos;
    }

    private void recorridoEIOR(NodoBinario<T> nodoAux, List<T> recorrido) {
        //caso base
        if (NodoBinario.esNodoVacio(nodoAux)) {
            return;
        }
        this.recorridoEIOR(nodoAux.getHijoIzquierdo(), recorrido);
        recorrido.add(nodoAux.getDato());
        this.recorridoEIOR(nodoAux.getHijoDerecho(), recorrido);
    }

    @Override
    public List<T> recorridoEnPreOrden() {
        List<T> listDeDatos = new LinkedList<>();
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<T>> pilaDeNodos = new Stack<>();
            pilaDeNodos.push(this.raiz);
            NodoBinario<T> nodoQueTocaSacar;
            do {
                nodoQueTocaSacar = pilaDeNodos.pop();
                listDeDatos.add(nodoQueTocaSacar.getDato());
                if (!nodoQueTocaSacar.esVacioHijoDerecho()) {
                    pilaDeNodos.push(nodoQueTocaSacar.getHijoDerecho());
                }
                if (!nodoQueTocaSacar.esVacioHijoIzquierdo()) {
                    pilaDeNodos.push(nodoQueTocaSacar.getHijoIzquierdo());
                }
            } while (!pilaDeNodos.isEmpty());
        }
        return listDeDatos;
    }

    public List<T> recorridoEnPreOrdenR() {
        List<T> recorrido = new LinkedList<>();
        if (!this.esArbolVacio()) {
            this.recorridoEPOR(this.raiz, recorrido);
        }
        return recorrido;
    }

    private void recorridoEPOR(NodoBinario<T> nodoAux, List<T> recorrido) {
        if (NodoBinario.esNodoVacio(nodoAux)) {
            return;
        }
        recorrido.add(nodoAux.getDato());
        this.recorridoEPOR(nodoAux.getHijoIzquierdo(), recorrido);
        this.recorridoEPOR(nodoAux.getHijoDerecho(), recorrido);
    }

    public List<T> recorridoEnPostOrdenR() {
        List<T> recorrido = new LinkedList<>();
        if (!this.esArbolVacio()) {
            this.recorridoEPostOR(this.raiz, recorrido);
        }
        return recorrido;
    }

    private void recorridoEPostOR(NodoBinario<T> nodoAux, List<T> recorrido) {
        if (NodoBinario.esNodoVacio(nodoAux)) {
            return;
        }
        this.recorridoEPOR(nodoAux.getHijoIzquierdo(), recorrido);
        this.recorridoEPOR(nodoAux.getHijoDerecho(), recorrido);
        recorrido.add(nodoAux.getDato());
    }

    @Override
    public List<T> recorridoEnPostOrden() {
        List<T> listaDeDatos = new LinkedList<>();
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<T>> pilaDeNodos = new Stack<>();
            NodoBinario<T> nodoAux = this.raiz;
            this.meterEnPilaParaPostOrden(nodoAux, pilaDeNodos);
            do {
                nodoAux = pilaDeNodos.pop();
                listaDeDatos.add(nodoAux.getDato());
                if (!pilaDeNodos.isEmpty()) {
                    NodoBinario<T> nodoTope = pilaDeNodos.peek();
                    if (!nodoTope.esVacioHijoDerecho()
                            && nodoTope.getHijoDerecho() != nodoAux) {
                        nodoAux = nodoTope.getHijoDerecho();
                        this.meterEnPilaParaPostOrden(nodoAux, pilaDeNodos);
                    }
                }
            } while (!pilaDeNodos.isEmpty());
        }
        return listaDeDatos;
    }

    private void meterEnPilaParaPostOrden(NodoBinario<T> nodoAux, Stack<NodoBinario<T>> pilaDeNodos) {
        while (!NodoBinario.esNodoVacio(nodoAux)) {
            pilaDeNodos.push(nodoAux);
            if (!nodoAux.esVacioHijoIzquierdo()) {
                nodoAux = nodoAux.getHijoIzquierdo();
            } else {
                nodoAux = nodoAux.getHijoDerecho();
            }
        }
    }

    @Override
    public List<T> recorridoPorNiveles() {
        List<T> listDeDatos = new LinkedList<>();
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<T>> colaNodos = new LinkedList<>();
            colaNodos.offer(this.raiz);
            NodoBinario<T> nodoQueTocaSacar;
            do {
                nodoQueTocaSacar = colaNodos.poll();
                listDeDatos.add(nodoQueTocaSacar.getDato());
                if (!nodoQueTocaSacar.esVacioHijoIzquierdo()) {
                    colaNodos.offer(nodoQueTocaSacar.getHijoIzquierdo());
                }
                if (!nodoQueTocaSacar.esVacioHijoDerecho()) {
                    colaNodos.offer(nodoQueTocaSacar.getHijoDerecho());
                }
            } while (!colaNodos.isEmpty());
        }
        return listDeDatos;
    }

    @Override
    public String toString() {
        return ToStrinNodo(this.raiz, "");
    }

    private String ToStrinNodo(NodoBinario<T> nodoActual, String ant) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return "";
        }
        String g = "{" + nodoActual.getDato().toString() + "}\n";
        g += ant + "║\n";
        g += ant + "╠═Der-> " + ToStrinNodo(nodoActual.getHijoDerecho(), ant + "║  ") + "\n";
        g += ant + "║\n";
        g += ant + "╚═Izq-> " + ToStrinNodo(nodoActual.getHijoIzquierdo(), ant + "    ");
        return g;
    }




}
